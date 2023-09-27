package com.neilw.postplatform.base.enviromnent;

import cn.hutool.setting.dialect.Props;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocalEnvironmentHelper {
    public static Environment getLocalEnv() {
        Environment environment = new Environment();
        try {
            Props props = new Props("env.properties");
            props.toProperties().forEach((k, v) -> environment.put(StringUtils.upperCase((String) k), v));
        } catch (Exception ignore) {}
        return environment;
    }
}
