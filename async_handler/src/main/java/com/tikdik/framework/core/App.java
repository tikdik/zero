package com.tikdik.framework.core;

import com.alibaba.fastjson.JSON;
import com.tikdik.framework.core.service.ZeroService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-root.xml");
        ZeroService zeroService = context.getBean(ZeroService.class);
        System.out.println(JSON.toJSON(zeroService.listAll()));
    }
}
