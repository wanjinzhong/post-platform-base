package com.neilw.postplatform.base.db;

import com.neilw.postplatform.base.constants.AvailableDbMethods;
import com.neilw.postplatform.base.constants.CommonConstants;
import com.neilw.postplatform.base.exception.InvalidMethodException;
import com.neilw.postplatform.base.logger.Logger;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
public class JdbcInterceptor implements MethodInterceptor {

    private long sqlTimeout = 1000 * 60;
    private Logger logger;
    private ExecutorService executorService;

    public JdbcInterceptor(Logger logger, ExecutorService executorService) {
        this(logger);
        this.executorService = executorService;
    }
    public JdbcInterceptor(Logger logger) {
        this.logger = logger;
        String timeoutStr = System.getenv(CommonConstants.SQL_TIMEOUT);
        if (StringUtils.isBlank(timeoutStr)) {
            logger.warn(String.format("Missing config for sql timeout, use default [%s] instead.", sqlTimeout));
        } else {
            try {
                long timeout = Long.parseLong(timeoutStr);
                if (timeout <= 0) {
                    throw new RuntimeException();
                }
                sqlTimeout = timeout;
            } catch (Exception e) {
                logger.warn(String.format("Error config for sql timeout [%s], use default [%s] instead.", timeoutStr, sqlTimeout));
            }
        }
    }

    @Override
    public Object intercept(Object db, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        AtomicReference<String> time = new AtomicReference<>();
        AtomicReference<String> originSql = new AtomicReference<>();;
        if (!method.getDeclaringClass().equals(Db.class) || method.getName().endsWith("System")) {
            return methodProxy.invokeSuper(db, objects);
        }
        FutureTask<Object> future = new FutureTask<>(() -> {
            try {
                Integer sqlParamIndex = validateMethod(method);
                time.set(String.valueOf(System.currentTimeMillis()));
                if (sqlParamIndex != null) {
                    originSql.set((String) objects[sqlParamIndex - 1]);
                    objects[sqlParamIndex - 1] = "/*" + time.get() + "*/" + objects[sqlParamIndex - 1];
                }
                return methodProxy.invokeSuper(db, objects);
            } catch (InvalidMethodException e) {
                throw e;
            } catch (Throwable e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
        try {
            if (executorService != null) {
                executorService.submit(future);
            } else {
                new Thread(future).start();
            }
            return future.get(sqlTimeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            if (!future.isCancelled()) {
                future.cancel(true);
            }
            if (StringUtils.isNotBlank(originSql.get())) {
                try {
                    ((Db) db).querySystem("select ID from information_schema.PROCESSLIST where COMMAND <> 'Sleep' and INFO like '%/*" + time.get() + "*/%' " +
                                    "and INFO not like '%from information_schema.PROCESSLIST%'")
                            .forEach(entity -> {
                                try {
                                    ((Db) db).executeSystem("kill " + entity.getLong("ID"));
                                } catch (SQLException ex) {
                                    logger.error("Failed to cancel to Sql execution.");
                                }
                            });
                } catch (Exception notSupport) {
                    log.error(notSupport.getMessage(), notSupport);
                }
                throw new SQLTimeoutException(String.format("SQL execution timeout for %dms: [%s] %s", sqlTimeout, methodProxy.getSignature().getName(), originSql.get()));
            } else {
                throw new SQLTimeoutException(String.format("SQL execution timeout for %dms: [%s]", sqlTimeout, methodProxy.getSignature().getName()));
            }
        }
    }

    private static Integer validateMethod(Method method) {
        Map<Method, Integer> availableMethods = AvailableDbMethods.getMethods();
        if (!availableMethods.containsKey(method)) {
            List<String> suggest = availableMethods.keySet().stream().filter(m -> Objects.equals(m.getName(), method.getName()))
                    .map(JdbcInterceptor::getMethodFullName).collect(Collectors.toList());
            StringBuilder message = new StringBuilder().append("Db method for ").append(getMethodFullName(method)).append(" is invalid");
            if (!CollectionUtils.isEmpty(suggest)) {
                message.append("\nSuggest to use:\n");
                message.append(String.join("\n", suggest));
            }
            throw new InvalidMethodException(message.toString());
        }
        return availableMethods.get(method);
    }

    private static String getMethodFullName(Method method) {
        String returnType = method.getReturnType().getSimpleName();
        if (ArrayUtils.isEmpty(method.getParameterTypes())) {
            return returnType + " " + method.getName() + "()";
        } else {
            return returnType + " " + method.getName() + "(" + Arrays.stream(method.getParameterTypes()).map(Class::getSimpleName).collect(Collectors.joining(", ")) + ")";
        }
    }
}