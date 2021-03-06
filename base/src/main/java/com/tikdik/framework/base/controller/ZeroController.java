package com.tikdik.framework.base.controller;

import com.tikdik.framework.base.exception.DataFormatException;
import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by rdadmin on 17-11-3.
 */
@Controller
@RequestMapping("/test")
public class ZeroController {

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
}
