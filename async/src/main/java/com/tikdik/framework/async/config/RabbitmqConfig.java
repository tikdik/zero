package com.tikdik.framework.async.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置
 * Created by yufei.liu on 2017/3/22.
 */
@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq_username}")
    private String rabbitmqUsername;

    @Value("${rabbitmq_password}")
    private String rabbitmqPassword;

    @Value("${rabbitmq_ip}")
    private String rabbitmqIp;

    @Value("${rabbitmq_port}")
    private int rabbitmqPort;

    public String getRabbitmqUsername() {
        return rabbitmqUsername;
    }

    public void setRabbitmqUsername(String rabbitmqUsername) {
        this.rabbitmqUsername = rabbitmqUsername;
    }

    public String getRabbitmqPassword() {
        return rabbitmqPassword;
    }

    public void setRabbitmqPassword(String rabbitmqPassword) {
        this.rabbitmqPassword = rabbitmqPassword;
    }

    public String getRabbitmqIp() {
        return rabbitmqIp;
    }

    public void setRabbitmqIp(String rabbitmqIp) {
        this.rabbitmqIp = rabbitmqIp;
    }

    public int getRabbitmqPort() {
        return rabbitmqPort;
    }

    public void setRabbitmqPort(int rabbitmqPort) {
        this.rabbitmqPort = rabbitmqPort;
    }
}
