package com.tikdik.framework.async.module.rabbitmq;

import com.google.common.base.Strings;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tikdik.framework.async.config.RabbitmqConfig;
import com.tikdik.framework.async.utils.ExceptionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 用户处理上称数据的rabbitmq的连接
 * 重点是close和recovery
 * close交给rabbitmqConfig管理
 * recovery自己负责重新连接
 */
@Component
public class RabbitmqDataConnection {

    private static final Logger logger = Logger.getLogger(RabbitmqDataConnection.class);

    /**
     * 路由的名字
     */
    private static final String EXCHANGE = "test-route";

    /**
     * 队列名字
     */
    private static final String RONG_CLOUD_QUEUE_NAME = "test_queue";


    private Connection connection;

    private Channel channel;

    private String rabbitmqUsername;

    private String rabbitmqPassword;

    private String rabbitmqIp;

    private int rabbitmqPort;

    @Autowired
    public RabbitmqDataConnection(RabbitmqConfig rabbitmqConfig) {
        this.rabbitmqUsername = rabbitmqConfig.getRabbitmqUsername();
        this.rabbitmqPassword = rabbitmqConfig.getRabbitmqPassword();
        this.rabbitmqIp = rabbitmqConfig.getRabbitmqIp();
        this.rabbitmqPort = rabbitmqConfig.getRabbitmqPort();
    }

    /**
     * 初始化rabbitmq连接
     */
    @PostConstruct
    public void init() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setAutomaticRecoveryEnabled(true);
        factory.setHost(rabbitmqIp);
        factory.setPort(rabbitmqPort);
        factory.setUsername(rabbitmqUsername);
        factory.setPassword(rabbitmqPassword);
        logger.info(String.format("rabbitmq ip = %s, port = %s, username = %s, password = %s",
                rabbitmqIp, rabbitmqPort, rabbitmqUsername, rabbitmqPassword));
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT, true, false, null);
            channel.queueDeclare(RONG_CLOUD_QUEUE_NAME, true, false, false, null);
            channel.queueBind(RONG_CLOUD_QUEUE_NAME, EXCHANGE, RONG_CLOUD_QUEUE_NAME);
        } catch (IOException | TimeoutException e) {
            ExceptionUtil.errInfo(e);
        }
        logger.info("rabbitmq data connection is running.");
    }


    public void sendMessage(String pub) {
        if(Strings.isNullOrEmpty(pub)) {
            return;
        }
        try {
            logger.info(pub);
            channel.basicPublish(EXCHANGE, RONG_CLOUD_QUEUE_NAME, null, pub.getBytes());
        } catch (IOException e) {
            ExceptionUtil.errInfo(e);
        }
    }

    /**
     * 关闭该rabbitmq连接
     *
     * @throws IOException 可能会出现异常
     */
    @PreDestroy
    public void close() throws IOException {
        logger.info("rabbitmq data connection close.");
        if (channel != null) {
            try {
                channel.close();
            } catch (TimeoutException e) {
                ExceptionUtil.errInfo(e);
            }
        }
        if (connection != null) {
            connection.close();
        }
    }
}
