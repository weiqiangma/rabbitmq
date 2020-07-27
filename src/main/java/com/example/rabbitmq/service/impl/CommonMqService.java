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
            rabbitTemplate.setExchange(env.getProperty("product.robbing.mq.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("product.robbing.mq.routing.key.name"));

            Message message= MessageBuilder.withBody(mobile.getBytes("UTF-8")).setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.send(message);
        }catch (Exception e){
            log.error("发送抢单信息入队列 发生异常： mobile={} ",mobile);
        }
    }

    /**
     * 发送抢单信息入队列
     * @param mobile
     */
    public void sendRobbingMsgV2(String mobile){
        try {
            rabbitTemplate.setExchange(env.getProperty("user.order.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("user.order.routing.key.name"));
            Message message= MessageBuilder.withBody(mobile.getBytes("UTF-8")).setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.send(message);
        }catch (Exception e){
            log.error("发送抢单信息入队列V2 发生异常： mobile={} ",mobile);
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


































