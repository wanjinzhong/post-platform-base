package com.neilw.postplatform.base.constants;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.handler.RsHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AvailableDbMethods {
    private static final Map<Method, Integer> methods = new HashMap<>();
    static {
        try {
            methods.put(Db.class.getSuperclass().getDeclaredMethod("query", String.class, Map.class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("query", String.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("query", String.class, Class.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("queryOne", String.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("queryNumber", String.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("queryString", String.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("query", String.class, RsHandler.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("execute", String.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("executeForGeneratedKey", String.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("executeBatch", String.class, Object[][].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("executeBatch", String.class, Iterable.class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("executeBatch", String.class, Iterable.class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("insert", Entity.class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("insertOrUpdate", Entity.class, String[].class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("upsert", Entity.class, String[].class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("insert", Collection.class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("insertForGeneratedKeys", Entity.class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("insertForGeneratedKey", Entity.class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("del", String.class, String.class, Object.class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("del", Entity.class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("update", Entity.class, Entity.class), null);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("count", CharSequence.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("page", CharSequence.class, Page.class, RsHandler.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("page", CharSequence.class, Page.class, Object[].class), 1);
            methods.put(Db.class.getSuperclass().getDeclaredMethod("page", CharSequence.class, Page.class, Object[].class), 1);
            methods.put(Db.class.getDeclaredMethod("getConnection"), null);
            methods.put(Db.class.getDeclaredMethod("closeConnection", Connection.class), null);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Method, Integer> getMethods() {
        return methods;
    }
}
