package com.tikdik.framework.base.controller;

import com.google.common.base.Strings;
import com.tikdik.framework.base.config.SwaggerPropertiesConfig;
import com.tikdik.framework.base.module.swagger.SwaggerUIFilter;
import com.tikdik.framework.base.response.Response;
import com.tikdik.framework.base.utils.RandomValidateCodeUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * swagger-ui登陆页面
 * Created by yufei.liu on 2016/12/19.
 */
@Controller
public class SwaggerLoginController {

    private static final Logger logger = Logger.getLogger(SwaggerLoginController.class);

    private String username;

    private String password;

    /**
     * 是否是本地测试状态
     */
    private boolean local;

    @Autowired
    public SwaggerLoginController(SwaggerPropertiesConfig swaggerPropertiesConfig) {
        Assert.notNull(swaggerPropertiesConfig);
        this.username = swaggerPropertiesConfig.getSwaggerLoginName();
        this.password = swaggerPropertiesConfig.getSwaggerLoginPassword();
        this.local = "admin".equals(this.password);
        logger.info(String.format("project run in environment is local: [%s].", this.local));
    }

    /**
     * 展示登陆页面
     *
     * @return 页面
     */
    @RequestMapping(value = "swagger/login", method = RequestMethod.GET)
    @ApiIgnore
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    /**
     * 请求验证码，并将验证码写到session中
     */
    @RequestMapping(value = "swagger/verify/code", method = RequestMethod.GET)
    @ApiOperation(value = "获取验证码", notes = "获取验证码", response = Void.class)
    @ResponseBody
    @ApiIgnore
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
        randomValidateCode.getRandCode(request, response);
    }

    /**
     * 登陆验证
     */
    @RequestMapping(value = "swagger/login/check", method = RequestMethod.POST)
    @ApiOperation(value = "登陆验证", notes = "登陆验证", response = Response.class)
    @ResponseBody
    @ApiIgnore
    public Response loginCheck(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("verifyCode") String verifyCode,
                                     HttpSession session) {
        // 本次测试环境，直接成功
        if (local) {
            session.setAttribute(SwaggerUIFilter.SWAGGER_LOGIN_FLAG, username);
            return Response.ok();
        }
        String currentVerifyCode = (String) session.getAttribute(RandomValidateCodeUtil.RANDOM_CODE_KEY);
        if (Strings.isNullOrEmpty(currentVerifyCode)) {
            return Response.error();
        }
        if (this.username.equals(username)
                && this.password.equals(password)
                && currentVerifyCode.equalsIgnoreCase(verifyCode)) {
            session.setAttribute(SwaggerUIFilter.SWAGGER_LOGIN_FLAG, username);
            return Response.ok();
        }
        return Response.error();
    }
}
