package com.tikdik.framework.sharding.exception;

/**
 * 数据格式不对
 * Created by yufei.liu on 2017/1/16.
 */
public class IllegalOperationException extends Exception {
    public IllegalOperationException() {
        this("illegal operation, you have no permission!");
    }

    public IllegalOperationException(String message) {
        super(message);
    }
}
