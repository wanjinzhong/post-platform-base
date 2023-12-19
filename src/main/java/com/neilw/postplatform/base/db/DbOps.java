package com.neilw.postplatform.base.db;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.dialect.Dialect;
import com.neilw.postplatform.base.annotation.DbResultProperty;
import com.neilw.postplatform.base.annotation.DbResultPropertyIgnore;
import com.neilw.postplatform.base.logger.Logger;
import com.neilw.postplatform.base.util.MapUtil;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbOps extends Db {
    private Logger logger;
    public DbOps(DataSource ds, Logger logger) {
        super(ds);
        this.logger = logger;
    }

    public DbOps(DataSource ds, String driverClassName, Logger logger) {
        super(ds, driverClassName);
        this.logger = logger;
    }

    public DbOps(DataSource ds, Dialect dialect, Logger logger) {
        super(ds, dialect);
        this.logger = logger;
    }
    @Override
    public List<Entity> query(String sql, Object... params) throws SQLException {
        logger.debug("[SQL] " + sql);
        return super.query(sql, params);
    }
    public <T> List<T> query(String sql, Map<String, Object> params, Class<T> resultType) throws SQLException {
        logger.debug("[SQL] " + sql);
        List<Entity> entities = super.query(sql, params);
        return buildData(resultType, entities);
    }


    protected List<Entity> querySystem(String sql, Object... params) throws SQLException {
        logger.debug("[SQL] " + sql);
        return super.query(sql, params);
    }

    protected int executeSystem(String sql, Object... params) throws SQLException {
        logger.debug("[SQL] " + sql);
        return super.execute(sql, params);
    }

    @Override
    public <T> List<T> query(String sql, Class<T> resultType, Object... params) throws SQLException {
        return buildData(resultType, super.query(sql, params));
    }

    public <T> T queryOne(String sql, Class<T> resultType) throws SQLException {
        return buildData(resultType, readFields(resultType), super.queryOne(sql));
    }
    public <T> T queryOne(String sql, Class<T> resultType, Object... params) throws SQLException {
        return buildData(resultType, readFields(resultType), super.queryOne(sql, params));
    }

    private <T> List<T> buildData(Class<T> resultType, List<Entity> entities) {
        Map<String, Field> fieldMap = readFields(resultType);
        List<T> data = new ArrayList<>();
        entities.forEach(e -> data.add(buildData(resultType, fieldMap, e)));
        return data;
    }

    private <T> T buildData(Class<T> resultType, Map<String, Field> fieldMap, Entity entity) {
        T d;
        try {
            d = resultType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        entity.forEach((name, value) -> MapUtil.get(fieldMap, StringUtils.lowerCase(name)).ifPresent(f -> {
            try {
                f.set(d, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }));
        return d;
    }

    private static <T> Map<String, Field> readFields(Class<T> resultType) {
        Field[] fields = resultType.getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            DbResultPropertyIgnore ignore = field.getAnnotation(DbResultPropertyIgnore.class);
            if (ignore != null) {
                continue;
            }
            DbResultProperty property = field.getAnnotation(DbResultProperty.class);
            String name = field.getName();
            if (property != null) {
                name = property.value();
            }
            fieldMap.put(StringUtils.lowerCase(name), field);
        }
        return fieldMap;
    }

}
