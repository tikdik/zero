package com.tikdik.framework.base.exception;


import com.tikdik.framework.base.response.Response;
import com.tikdik.framework.base.utils.ExceptionUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * <p>
 * Created by yufei.liu on 2017/6/19.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = Logger.getLogger(ExceptionHandlerController.class);

    /**
     * 获取异常信息
     *
     * @param e 异常
     */
    public static void errInfo(Exception e) {
        if (e == null) {
            return;
        }
        logger.info(ExceptionUtil.getErrorMessage(e));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response processException(Exception e) {
        logger.info(ExceptionUtil.getErrorMessage(e));
        return new Response().setCode(1000)
                .setDescription("service exception.");
    }

    /**
     * 数据格式不正确
     */
    @ExceptionHandler(DataFormatException.class)
    @ResponseBody
    public Response processDataFormatException(DataFormatException e) {
        return new Response().setCode(1001)
                .setDescription(e.getMessage());
    }

    /**
     * 操作对象不存在
     */
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    public Response processDataNotFoundException(DataNotFoundException e) {
        return new Response().setCode(1051)
                .setDescription(e.getMessage());
    }

    @ExceptionHandler(IllegalOperationException.class)
    @ResponseBody
    public Response processIllegalOperationException(IllegalOperationException e) {
        return new Response().setCode(1052)
                .setDescription(e.getMessage());
    }
}
