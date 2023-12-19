package com.neilw.postplatform.base.enviromnent;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.upperCase;


public class Environment {
    private final Map<String, String> env = new HashMap<>();


    public Map<String, String> listEnv() {
        return env;
    }

    public Set<String> listKey() {
        return env.keySet();
    }

    public void put(String key, Object value) {
        env.put(upperCase(key), String.valueOf(value));
    }

    public String getString(String key) {
        return env.get(upperCase(key));
    }
    public Integer getInteger(String key) {
        return Integer.valueOf(env.get(upperCase(key)));
    }

    public Long getLong(String key) {
        return Long.valueOf(env.get(upperCase(key)));
    }

    public Double getDouble(String key) {
        return Double.valueOf(env.get(upperCase(key)));
    }

    public Boolean getBoolean(String key) {
        return Boolean.valueOf(env.get(upperCase(key)));
    }

    public LocalDate getDate(String key, String format) {
        String str = getString(upperCase(key));
        return isBlank(str) ? null : LocalDate.parse(str, DateTimeFormatter.ofPattern(format));
    }

    public LocalDateTime getTime(String key, String format) {
        String str = getString(upperCase(key));
        return isBlank(str) ? null : LocalDateTime.parse(str, DateTimeFormatter.ofPattern(format));
    }
    public List<String> getStringAsList(String key) {
        return getStringAsList(key, ",");
    }
    public List<Integer> getIntegerAsList(String key) {
        return getIntegerAsList(key, ",");
    }

    public List<Long> getLongAsList(String key) {
        return getLongAsList(key, ",");
    }

    public List<Double> getDoubleAsList(String key) {
        return getDoubleAsList(key, ",");
    }

    public List<Boolean> getBooleanAsList(String key) {
        return getBooleanAsList(key, ",");
    }

    public List<LocalDate> getDateAsList(String key, String format) {
        return getDateAsList(key, format, ",");
    }

    public List<LocalDateTime> getTimeAsList(String key, String format) {
        return getTimeAsList(key, format, ",");
    }

    public List<String> getStringAsList(String key, String splitor) {
        String stringValue = env.get(upperCase(key));
        if (StringUtils.isBlank(stringValue)) {
            return new ArrayList<>();
        }
        return Arrays.asList(stringValue.split(splitor));
    }
    public List<Integer> getIntegerAsList(String key, String splitor) {
        return getStringAsList(key, splitor).stream().map(StringUtils::trim).filter(StringUtils::isNotBlank)
                .map(Integer::valueOf).collect(Collectors.toList());
    }

    public List<Long> getLongAsList(String key, String splitor) {
        return getStringAsList(key, splitor).stream().map(StringUtils::trim).filter(StringUtils::isNotBlank)
                .map(Long::valueOf).collect(Collectors.toList());
    }

    public List<Double> getDoubleAsList(String key, String splitor) {
        return getStringAsList(key, splitor).stream().map(StringUtils::trim).filter(StringUtils::isNotBlank)
                .map(Double::valueOf).collect(Collectors.toList());
    }

    public List<Boolean> getBooleanAsList(String key, String splitor) {
        return getStringAsList(key, splitor).stream().map(StringUtils::trim).filter(StringUtils::isNotBlank)
                .map(Boolean::valueOf).collect(Collectors.toList());
    }

    public List<LocalDate> getDateAsList(String key, String format, String splitor) {
        return getStringAsList(key, splitor).stream().map(StringUtils::trim).filter(StringUtils::isNotBlank)
                .map(s -> LocalDate.parse(s, DateTimeFormatter.ofPattern(format))).collect(Collectors.toList());
    }

    public List<LocalDateTime> getTimeAsList(String key, String format, String splitor) {
        return getStringAsList(key, splitor).stream().map(StringUtils::trim).filter(StringUtils::isNotBlank)
                .map(s -> LocalDateTime.parse(s, DateTimeFormatter.ofPattern(format))).collect(Collectors.toList());
    }

    public String getOrDefaultString(String key, String defaultValue) {
        return env.getOrDefault(upperCase(key), defaultValue);
    }
    public Integer getOrDefaultInteger(String key, Integer defaultValue) {
        String value = env.get(upperCase(key));
        return isBlank(value) ? defaultValue : Integer.valueOf(value);
    }
    public Long getOrDefaultLong(String key, Long defaultValue) {
        String value = env.get(upperCase(key));
        return isBlank(value) ? defaultValue : Long.valueOf(value);
    }
    public Double getOrDefaultDouble(String key, Double defaultValue) {
        String value = env.get(upperCase(key));
        return isBlank(value) ? defaultValue : Double.valueOf(value);
    }
    public Boolean getOrDefaultBoolean(String key, Boolean defaultValue) {
        String value = env.get(upperCase(key));
        return isBlank(value) ? defaultValue : Boolean.valueOf(value);
    }
    public LocalDate getOrDefaultDate(String key, String format, LocalDate defaultValue) {
        String value = env.get(upperCase(key));
        return isBlank(value) ? defaultValue : LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
    }

    public LocalDateTime getOrDefaultTime(String key, String format, LocalDateTime defaultValue) {
        String value = env.get(upperCase(key));
        return isBlank(value) ? defaultValue : LocalDateTime.parse(value, DateTimeFormatter.ofPattern(format));
    }
}
