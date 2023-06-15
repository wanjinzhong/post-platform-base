package com.neilw.postplatform.base.interceptor;

import com.neilw.postplatform.base.constants.CommonConstants;
import com.neilw.postplatform.base.logger.Logger;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.time.StopWatch;

import java.lang.reflect.Method;
import java.util.concurrent.*;

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
        String timeoutStr = System.getProperty(CommonConstants.SQL_TIMEOUT);
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

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        FutureTask<Object> future = new FutureTask<>(() -> {
            try {
                return methodProxy.invokeSuper(o, objects);
            } catch (Throwable e) {
                throw new RuntimeException(e);
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
            if (objects != null && objects.length > 0) {
                throw new RuntimeException(String.format("SQL execution timeout for %dms: [%s] %s", sqlTimeout, methodProxy.getSignature().getName(), objects[0]));
            } else {
                throw new RuntimeException(String.format("SQL execution timeout for %dms: [%s]", sqlTimeout, methodProxy.getSignature().getName()));
            }
        } finally {
            if (!future.isCancelled() && !future.isDone()) {
                future.cancel(true);
            }
        }
    }
}