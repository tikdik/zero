package com.tikdik.framework.base.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 响应
 * Created by yufei.liu on 2017/2/24.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private static final Response ok = new Response().setCode(0).setDescription("ok");

    private static final Response error = new Response().setCode(-1).setDescription("error");

    private int code = 0;

    private String description = "ok";

    private T result;

    public static Response ok() {
        return ok;
    }

    public static Response error() {
        return error;
    }

    public int getCode() {
        return code;
    }

    public Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Response<T> setDescription(String description) {
        this.description = description;
        return this;
    }

    public T getResult() {
        return result;
    }

    public Response<T> setResult(T result) {
        this.result = result;
        return this;
    }

}
