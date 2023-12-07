package com.neilw.postplatform.base.db;

import cn.hutool.core.io.FileUtil;
import cn.hutool.db.dialect.Dialect;
import com.neilw.postplatform.base.enums.DBEnv;
import com.neilw.postplatform.base.logger.Logger;
import net.sf.cglib.proxy.Enhancer;
import sun.security.provider.MD5;

import javax.sql.DataSource;
import java.util.zip.Checksum;

public class DBHelper {
    private Logger logger;
    private JdbcInterceptor interceptor;
    public DBHelper(Logger logger) {
        this.logger = logger;
        interceptor = new JdbcInterceptor(logger);
    }

    public DBHelper(Logger logger, JdbcInterceptor interceptor) {
        this.logger = logger;
        this.interceptor = interceptor;
    }

    public DbOps getDb(String dbName, DBEnv env, Dialect dialect) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DbOps.class);
        enhancer.setCallback(interceptor);
        return (DbOps) enhancer.create(new Class[]{DataSource.class, Dialect.class, Logger.class},
                new Object[]{DataSourceProviderFactory.getProvider().getDataSource(dbName, env), dialect, logger});
    }
}
