package com.neilw.postplatform.base.util;

import cn.hutool.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenGenerator {
    public static String generate(String loginId) {
        String url = System.getProperty("token-url", "http://hyvex.synnex.org/p1-token/");
        loginId = loginId == null ? "synnex-web" : loginId;
        return ((Map<String, String>) (new Gson().fromJson(HttpRequest.get(url + loginId).execute().body(), new TypeToken<Map<String, String>>() {}.getType()))).get("token");
    }
}
