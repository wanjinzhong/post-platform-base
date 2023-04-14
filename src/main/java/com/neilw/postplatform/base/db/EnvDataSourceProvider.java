package com.neilw.postplatform.base.db;

import cn.hutool.db.dialect.impl.MysqlDialect;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.neilw.postplatform.base.enums.DBEnv;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;

public class EnvDataSourceProvider implements DataSourceProvider {
    @Override
    public DataSource getDataSource(String name) {
        return getDataSource(name, null);
    }

    @Override
    public DataSource getDataSource(String name, DBEnv env) {
        String upperName = StringUtils.upperCase(name);
        String url;
        String userName;
        String password;
        if (env != null) {
            url = System.getProperty(String.format("DB_%s_%s_URL", upperName, env.upper()));
            userName = System.getProperty(String.format("DB_%s_%s_USERNAME", upperName, env.upper()));
            password = System.getProperty(String.format("DB_%s_%s_PASSWORD", upperName, env.upper()));
            if (StringUtils.isAnyBlank(url, userName, password) ) {
                throw new RuntimeException(String.format("DataSouce %s for %s is not configured.", upperName, env.upper()));
            }
        } else {
            url = System.getProperty(String.format("DB_%s_URL", upperName));
            userName = System.getProperty(String.format("DB_%s_USERNAME", upperName));
            password = System.getProperty(String.format("DB_%s_PASSWORD", upperName));
            if (StringUtils.isAnyBlank(url, userName, password) ) {
                throw new RuntimeException(String.format("DataSouce %s is not configured.", upperName));
            }
        }
        return new SimpleDataSource(url, userName, password, MysqlDialect.class.getName());
    }
}
