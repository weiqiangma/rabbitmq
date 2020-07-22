package com.example.rabbitmq.controller;

import com.example.rabbitmq.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2020/7/22 15:17
 * @Author mawkun
 */
@RestController
@RequestMapping(value = "mail")
public class MailController {
    Logger log = LoggerFactory.getLogger(MailController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "sendMail")
    public CommonResult sendMail() {
        try {
            rabbitTemplate.setExchange("local.mail.exchange");
            rabbitTemplate.setRoutingKey("local.mail.routing.key");
            rabbitTemplate.convertAndSend("邮件发送中！！！");
        }catch (Exception e) {
            e.printStackTrace();
        }
        log.info("邮件发送完毕,请注意查收");
        return CommonResult.success("ok");
    }
}
