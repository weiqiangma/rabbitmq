package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.UserLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/8/25.
 */
@Service
public class CommonMqService {

    private static final Logger log= LoggerFactory.getLogger(ConcurrencyService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment env;


    /**
     * 发送抢单信息入队列
     * @param mobile
     */
    public void sendRobbingMsg(String mobile){
        try {
            Message message = MessageBuilder.withBody(mobile.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)    //设置内容类型
                    .setMessageCount(100)                                   //设置消息总数（某个队列下消息最大容量）
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)        //设置投递模式（消息持久化）
                    .build();
            rabbitTemplate.send("local.user.order.exchange", "local.user.order.routing.key", message);
        }catch (Exception e){
            log.error("发送抢单信息入队列 发生异常： mobile={} ",mobile);
        }
    }


    /**
     * 发送用户操作日志入队列
     * @param userLog
     */
    public void sendUserLog(UserLog userLog) {
        try {
            rabbitTemplate.setExchange(env.getProperty("log.user.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("log.user.routing.key.name"));
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(userLog)).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            rabbitTemplate.send(message);
        } catch (Exception e) {
            log.error("发送用户操作日志入队列 发生异常：");
        }
    }
}


































