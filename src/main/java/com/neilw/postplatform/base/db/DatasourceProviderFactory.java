package com.neilw.postplatform.base.db;

import org.apache.commons.lang3.StringUtils;

public class DatasourceProviderFactory {
    public static DataSourceProvider getProvider() {
        if (StringUtils.equals("PROD", System.getProperty("PLUGIN_ENV"))) {
            return new EnvDataSourceProvider();
        } else {
            return new PropertiesDataSourceProvider();
        }
    }
}
