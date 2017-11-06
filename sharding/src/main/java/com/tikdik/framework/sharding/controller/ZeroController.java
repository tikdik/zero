package com.tikdik.framework.sharding.controller;

import com.tikdik.framework.sharding.exception.DataFormatException;
import com.tikdik.framework.sharding.service.ShardingService;
import org.apache.ibatis.annotations.Param;
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
    private ShardingService shardingService;

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

    @RequestMapping(value = "/sharding", method = RequestMethod.POST)
    @ResponseBody
    public String sharding(@RequestParam("name") String name) {
        shardingService.add(name);
        return "hello";
    }
}
