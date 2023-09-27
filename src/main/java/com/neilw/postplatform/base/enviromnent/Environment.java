package com.neilw.postplatform.base.enviromnent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
