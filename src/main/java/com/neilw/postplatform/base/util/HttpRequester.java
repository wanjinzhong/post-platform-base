package com.neilw.postplatform.base.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpRequester {
    private Method method;
    private String url;
    private Map<String, String> pathVariable = new HashMap<>();
    private Map<String, Object> requestParams = new HashMap<>();
    private Map<String, List<String>> headers = new HashMap<>();
    private String loginId;
    private Object body;

    public static HttpRequester get(String url) {
        HttpRequester requester = new HttpRequester();
        requester.method = Method.GET;
        requester.url = url;
        return requester;
    }
    public static HttpRequester post(String url) {
        HttpRequester requester = new HttpRequester();
        requester.method = Method.POST;
        requester.url = url;
        return requester;
    }
    public static HttpRequester put(String url) {
        HttpRequester requester = new HttpRequester();
        requester.method = Method.PUT;
        requester.url = url;
        return requester;
    }
    public static HttpRequester delete(String url) {
        HttpRequester requester = new HttpRequester();
        requester.method = Method.DELETE;
        requester.url = url;
        return requester;
    }

    public HttpRequester pathVariables(Map<String, String> pathVariable) {
        if (pathVariable != null) {
            for (Map.Entry<String, String> entry : pathVariable.entrySet()) {
                pathVariable(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public HttpRequester pathVariable(String key, String value) {
        if (StringUtils.isNotBlank(key)) {
            this.pathVariable.put(key, value);
        }
        return this;
    }

    public HttpRequester headers(Map<String, List<String>> headers) {
        if (headers != null) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                header(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public HttpRequester header(String key, List<String> value) {
        if (StringUtils.isNotBlank(key)) {
            this.headers.put(key, value);
        }
        return this;
    }

    public HttpRequester loginId(String loginId) {
        this.loginId = loginId;
        return this;
    }
    public HttpRequester body(Object body) {
        this.body = body;
        return this;
    }

    public HttpRequester requestParams(Map<String, String> requestParams) {
        if (requestParams != null) {
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                requestParam(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public HttpRequester requestParam(String key, String value) {
        if (StringUtils.isNotBlank(key)) {
            this.requestParams.put(key, value);
        }
        return this;
    }

    public Map<String, Object> execute() {
        String actUrl = buildUrl(url, pathVariable);
        if (StringUtils.isBlank(loginId)) {
            loginId = "synnex-web";
        }
        HttpRequest request = HttpRequest.of(actUrl).setMethod(method)
                .contentType("application/json")
                .header(headers)
                .bearerAuth(TokenGenerator.generate(loginId));
        if (body != null) {
            request.body(new Gson().toJson(body));
        }
        request.form(requestParams);
        HttpResponse response = request.execute();
        if (response.getStatus() == 404) {
            throw new RuntimeException(String.format("404: page not found %s", actUrl));
        }
        return new Gson().fromJson(response.body(), new TypeToken<Map<String, Object>>(){}.getType());
    }

    private static String buildUrl(String url, Map<String, String> pathVariable) {
        if (pathVariable == null) {
            return url;
        }
        for (Map.Entry<String, String> vari : pathVariable.entrySet()) {
            url = url.replace("{" + vari.getKey() + "}", vari.getValue());
        }
        return url;
    }
}
