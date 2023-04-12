package com.neilw.postplatform.base.db;

import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.setting.dialect.Props;
import com.neilw.postplatform.base.enums.DBEnv;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;

public class PropertiesDataSourceProvider implements DataSourceProvider {

    @Override
    public DataSource getDataSource(String name) {
        return getDataSource(name, null);
    }

    @Override
    public DataSource getDataSource(String name, DBEnv env) {
        Props props = new Props("db.properties");
        String url;
        String userName;
        String password;
        if (env != null) {
            url = props.getProperty(String.format("db.%s.%s.url", name, env.lower()));
            userName = props.getProperty(String.format("db.%s.%s.username", name, env.lower()));
            password = props.getProperty(String.format("db.%s.%s.password", name, env.lower()));
        } else {
            url = props.getProperty(String.format("db.%s.url", name));
            userName = props.getProperty(String.format("db.%s.username", name));
            password = props.getProperty(String.format("db.%s.password", name));
        }
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException(String.format("url for datasource %s is not present.", name));
        }
        if (StringUtils.isBlank(userName)) {
            throw new RuntimeException(String.format("username for datasource %s is not present.", name));
        }
        if (StringUtils.isBlank(password)) {
            throw new RuntimeException(String.format("password for datasource %s is not present.", name));
        }
        return new SimpleDataSource(url, userName, password);
    }
}
