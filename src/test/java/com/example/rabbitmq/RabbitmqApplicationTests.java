package com.example.rabbitmq;

import com.example.rabbitmq.entity.ProductBak;
import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.service.ProductBakService;
import com.example.rabbitmq.service.ProductService;
import com.example.rabbitmq.service.impl.CommonMqService;
import com.example.rabbitmq.service.impl.InitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Random;


@SpringBootTest
class RabbitmqApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProductService productService;
    @Autowired
    ProductBakService productBakService;
    @Autowired
    InitService initService;
    @Autowired
    CommonMqService commonMqService;

    @Test
    void contextLoads() throws JsonProcessingException {
        User user = new User();
        user.setUserName("张三");
        user.setPassword("123456");

        Message message = MessageBuilder.withBody("hello world".getBytes()).build();
       // message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("local.log.user.exchange", "local.log.user.routing.key", message);
        System.out.println("正在发送消息");
    }

    @Test
    public void testAddProductBak(){
        for(int i = 0; i < 100000; i++) {
            Random random = new Random();
            String name = RandomStringUtils.random(random.nextInt(8), 0x4e00, 0x9fa5, false, false);
            ProductBak productBak = new ProductBak();
            productBak.setName(name);
            productBak.setIsActive(1);
            productBak.setStock(random.nextInt(1000));
            productBak.setPurchaseDate(new Date());
            productBak.setUpdateTime(new Date());
            productBak.setCreateTime(new Date());
            productBakService.insert(productBak);
        }
        System.out.println("ok");
    }

    @Test
    public void testDirectQueue() {
//            String body = "hello world:";
//            Message message = MessageBuilder.withBody(body.getBytes()).build();
//            rabbitTemplate.convertAndSend("testDirectExchange", "test.direct.routing.key", message);
            //rabbitTemplate.send("testFanOutExchange","test.fanout.routing.key1", message);
            commonMqService.sendRobbingMsg("17857026617");
    }

    @Test
    public void testFindProduct() {
        Random random = new Random();
        for(int i = 0; i < 200; i++) {
            productService.seckillProduct(String.valueOf(random.nextInt(100000)));
        }
    }
}
