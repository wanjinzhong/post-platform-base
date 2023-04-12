package com.neilw.postplatform.base.db;

import com.neilw.postplatform.base.enums.DBEnv;

import javax.sql.DataSource;

public interface DataSourceProvider {
    DataSource getDataSource(String name);

    DataSource getDataSource(String name, DBEnv env);
}
