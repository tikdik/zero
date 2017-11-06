package com.tikdik.framework.base.exception;

/**
 * 数据格式不对
 * Created by yufei.liu on 2017/1/16.
 */
public class DataFormatException extends Exception {
    public DataFormatException() {
        this("param format error!");
    }

    public DataFormatException(String message) {
        super(message);
    }
}
