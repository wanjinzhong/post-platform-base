package com.neilw.postplatform.base.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.dialect.DialectFactory;
import cn.hutool.db.dialect.DriverNamePool;
import com.neilw.postplatform.base.collector.SQLNumberInCollector;
import com.neilw.postplatform.base.collector.SQLStringInCollector;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlUtil extends cn.hutool.db.sql.SqlUtil {

    public static Collector<Number, List<String>, String> numberInCollector() {
        return new SQLNumberInCollector();
    }
    public static Collector<String, List<String>, String> stringInCollector() {
        return new SQLStringInCollector();
    }
    public static String buildNumberIn(Collection<Number> data) {
        if (CollectionUtils.isEmpty(data)) {
            return "";
        }
        return data.stream().map(d -> d == null ? null : d.toString()).collect(Collectors.joining(","));
    }

    public static String buildStringIn(Collection<?> data) {
        if (CollectionUtils.isEmpty(data)) {
            return "";
        }
        return data.stream().map(d -> d == null ? null : "'" + d + "'").collect(Collectors.joining(","));
    }

    public static String getJDBCDriver(String jdbcUrl) {
        String name = ReUtil.getGroup1("jdbc:(.*?):", jdbcUrl);
        String driverName = DialectFactory.identifyDriver(jdbcUrl, null);
        if (StringUtils.isBlank(driverName) && StringUtils.isNotBlank(name)) {
            // other logic
//            if (name.contains("jtds")) {
//                driverName = DriverNamePool.DRIVER_SYBASE;
//            }
        }
        return driverName;
    }
}
