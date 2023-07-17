package com.neilw.postplatform.base.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neilw.postplatform.base.exception.http.HttpException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpRequester {
    private static final String AUTHORIZATION = "Authorization";
    private static final String COOKIE = "Cookie";
    private Method method;
    private String url;
    private Map<String, String> pathVariable = new HashMap<>();
    private Map<String, Object> requestParams = new HashMap<>();
    private Map<String, List<String>> headers = new HashMap<>();
    private String loginId;
    private String token;
    private String cookie;
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

    public HttpRequester token(String token) {
        this.token = token;
        return this;
    }

    public HttpRequester cookie(String cookie) {
        this.cookie = cookie;
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
                .contentType("application/json");

        if (body != null) {
            request.body(new Gson().toJson(body));
        }
        request.form(requestParams);
        if (StringUtils.isNotBlank(cookie)) {
            header(COOKIE, Collections.singletonList(cookie));
        } else if (StringUtils.isNotBlank(token)) {
            header(AUTHORIZATION, Collections.singletonList(token));
        } else {
            headers.remove(AUTHORIZATION);
            request.bearerAuth(TokenGenerator.generate(loginId));
        }
        request.header(headers);
        HttpResponse response = request.execute();
        if (!Objects.equals(response.getStatus(), 200)) {
            throw HttpException.of(response.getStatus(), response.body(), null);
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
