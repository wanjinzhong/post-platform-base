package com.neilw.postplatform.base.enviromnent;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Environment {
    private final Map<String, String> env = new HashMap<>();

    public Map<String, String> listEnv() {
        return env;
    }

    public Set<String> listKey() {
        return env.keySet();
    }

    public void put(String key, Object value) {
        env.put(key, String.valueOf(value));
    }

    public String getString(String key) {
        return env.get(key);
    }
    public Integer getInteger(String key) {
        return Integer.valueOf(env.get(key));
    }

    public Long getLong(String key) {
        return Long.valueOf(env.get(key));
    }

    public Double getDouble(String key) {
        return Double.valueOf(env.get(key));
    }

    public Boolean getBoolean(String key) {
        return Boolean.valueOf(env.get(key));
    }

    public LocalDate getDate(String key, String format) {
        String str = getString(key);
        return StringUtils.isBlank(str) ? null : LocalDate.parse(str, DateTimeFormatter.ofPattern(format));
    }

    public LocalDateTime getTime(String key, String format) {
        String str = getString(key);
        return StringUtils.isBlank(str) ? null : LocalDateTime.parse(str, DateTimeFormatter.ofPattern(format));
    }
    public String getOrDefaultString(String key, String defaultValue) {
        return env.getOrDefault(key, defaultValue);
    }
    public Integer getOrDefaultInteger(String key, Integer defaultValue) {
        String value = env.get(key);
        return StringUtils.isBlank(value) ? defaultValue : Integer.valueOf(value);
    }
    public Long getOrDefaultLong(String key, Long defaultValue) {
        String value = env.get(key);
        return StringUtils.isBlank(value) ? defaultValue : Long.valueOf(value);
    }
    public Double getOrDefaultDouble(String key, Double defaultValue) {
        String value = env.get(key);
        return StringUtils.isBlank(value) ? defaultValue : Double.valueOf(value);
    }
    public Boolean getOrDefaultBoolean(String key, Boolean defaultValue) {
        String value = env.get(key);
        return StringUtils.isBlank(value) ? defaultValue : Boolean.valueOf(value);
    }
    public LocalDate getOrDefaultDate(String key, String format, LocalDate defaultValue) {
        String value = env.get(key);
        return StringUtils.isBlank(value) ? defaultValue : LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
    }

    public LocalDateTime getOrDefaultTime(String key, String format, LocalDateTime defaultValue) {
        String value = env.get(key);
        return StringUtils.isBlank(value) ? defaultValue : LocalDateTime.parse(value, DateTimeFormatter.ofPattern(format));
    }
}
