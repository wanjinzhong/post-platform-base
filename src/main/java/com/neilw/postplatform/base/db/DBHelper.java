package com.neilw.postplatform.base.db;

import cn.hutool.db.Db;
import cn.hutool.db.dialect.Dialect;
import com.neilw.postplatform.base.enums.DBEnv;
import com.neilw.postplatform.base.interceptor.JdbcInterceptor;
import com.neilw.postplatform.base.logger.Logger;
import net.sf.cglib.proxy.Enhancer;

import javax.sql.DataSource;

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

    public Db getDb(String dbName, DBEnv env, Dialect dialect) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Db.class);
        enhancer.setCallback(interceptor);
        return (Db) enhancer.create(new Class[]{DataSource.class, Dialect.class},
                new Object[]{DataSourceProviderFactory.getProvider().getDataSource(dbName, env), dialect});
    }
}
