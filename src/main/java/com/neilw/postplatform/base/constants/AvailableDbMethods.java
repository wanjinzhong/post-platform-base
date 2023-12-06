package com.neilw.postplatform.base.constants;

import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.handler.RsHandler;
import com.neilw.postplatform.base.db.Db;
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
            methods.put(Db.class.getMethod("query", String.class, Map.class), 1);
            methods.put(Db.class.getMethod("query", String.class, Object[].class), 1);
            methods.put(Db.class.getDeclaredMethod("querySystem", String.class, Object[].class), 1);
            methods.put(Db.class.getMethod("query", String.class, Class.class, Object[].class), 1);
            methods.put(Db.class.getMethod("queryOne", String.class, Object[].class), 1);
            methods.put(Db.class.getMethod("queryNumber", String.class, Object[].class), 1);
            methods.put(Db.class.getMethod("queryString", String.class, Object[].class), 1);
            methods.put(Db.class.getMethod("query", String.class, RsHandler.class, Object[].class), 1);
            methods.put(Db.class.getMethod("execute", String.class, Object[].class), 1);
            methods.put(Db.class.getDeclaredMethod("executeSystem", String.class, Object[].class), 1);
            methods.put(Db.class.getMethod("executeForGeneratedKey", String.class, Object[].class), 1);
            methods.put(Db.class.getMethod("executeBatch", String.class, Object[][].class), 1);
            methods.put(Db.class.getMethod("executeBatch", String.class, Iterable.class), 1);
            methods.put(Db.class.getMethod("executeBatch", String.class, Iterable.class), 1);
            methods.put(Db.class.getMethod("insert", Entity.class), null);
            methods.put(Db.class.getMethod("insertOrUpdate", Entity.class, String[].class), null);
            methods.put(Db.class.getMethod("upsert", Entity.class, String[].class), null);
            methods.put(Db.class.getMethod("insert", Collection.class), null);
            methods.put(Db.class.getMethod("insertForGeneratedKeys", Entity.class), null);
            methods.put(Db.class.getMethod("insertForGeneratedKey", Entity.class), null);
            methods.put(Db.class.getMethod("del", String.class, String.class, Object.class), 1);
            methods.put(Db.class.getMethod("del", Entity.class), null);
            methods.put(Db.class.getMethod("update", Entity.class, Entity.class), null);
            methods.put(Db.class.getMethod("count", CharSequence.class, Object[].class), 1);
            methods.put(Db.class.getMethod("page", CharSequence.class, Page.class, RsHandler.class, Object[].class), 1);
            methods.put(Db.class.getMethod("page", CharSequence.class, Page.class, Object[].class), 1);
            methods.put(Db.class.getMethod("page", CharSequence.class, Page.class, Object[].class), 1);
            methods.put(Db.class.getMethod("getConnection"), null);
            methods.put(Db.class.getMethod("closeConnection", Connection.class), null);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Method, Integer> getMethods() {
        return methods;
    }
}
