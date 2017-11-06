package com.tikdik.framework.async.controller;

import com.tikdik.framework.async.exception.DataFormatException;
import com.tikdik.framework.async.module.rabbitmq.RabbitmqDataConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by rdadmin on 17-11-3.
 */
@Controller
@RequestMapping("/test")
public class ZeroController {
    @Autowired
    private RabbitmqDataConnection rabbitmqDataConnection;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    @ResponseBody
    public String exception()
            throws DataFormatException {
        throw new DataFormatException("exception");
    }

    @RequestMapping(value = "/rabbitmq_send", method = RequestMethod.GET)
    @ResponseBody
    public void rabbitmqSend(@RequestParam("msg") String msg) {
        rabbitmqDataConnection.sendMessage(msg);
    }
}
