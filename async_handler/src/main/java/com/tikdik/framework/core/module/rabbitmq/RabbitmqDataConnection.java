package com.tikdik.framework.core.module.rabbitmq;

import com.rabbitmq.client.*;
import com.tikdik.framework.core.config.RabbitmqConfig;
import com.tikdik.framework.core.utils.ExceptionUtil;
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
    /**
     * rabbitmq一次性最多发送数据的条数，数量不能太多，容易导致
     */
    private static final int PREFETCH_COUNT = 100;

    /**
     * 最大次数是10
     */
    private int tryNumber = 0;

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
            channel.basicQos(PREFETCH_COUNT);
            channel.basicRecover(true);

            channel.queueDeclare(RONG_CLOUD_QUEUE_NAME, true, false, false, null);
            channel.basicConsume(RONG_CLOUD_QUEUE_NAME, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(message);
                }
            });
        } catch (IOException | TimeoutException e) {
            ExceptionUtil.errInfo(e);
            // 认为这个异常是在中途出现，尝试重连
            recovery();
        }
        tryNumber = 0;
        logger.info("rabbitmq data connection is running.");
    }

    /**
     * 恢复连接
     */
    private void recovery() {
        // 首先关闭相应资源
        try {
            close();
            Thread.sleep(5000);
        } catch (IOException | InterruptedException ignore) {
        }
        logger.info("try again.");
        tryNumber++;
        if (tryNumber < 10) {
            init();
        } else {
            logger.info("try up to max.");
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
