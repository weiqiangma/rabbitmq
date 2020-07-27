package com.example.rabbitmq.component;

import com.example.rabbitmq.dao.UserLogDao;
import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.entity.UserLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/30.
 */
@Component
public class CommonMqListener {

    private static final Logger log= LoggerFactory.getLogger(CommonMqListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserLogDao userLogMapper;

    /**
     * 监听消费用户日志
     * @param message
     */
    //@RabbitListener(queues = "${log.user.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeUserLogQueue(Message message){
        System.out.println("进入监听消息方法");
        try {
            UserLog userLog = objectMapper.readValue(message.getBody(), UserLog.class);
            userLogMapper.insert(userLog);
            log.info("监听消费用户日志 监听到消息： {} " + new String(message.getBody()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 监听消费邮件发送
     * @param message
     */
    @RabbitListener(queues = "${mail.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMailQueue(Message message){
        try {
            log.info("监听消费邮件发送 监听到消息： {} ",new String(message.getBody(),"UTF-8"));
            //mailService.sendEmail();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 监听消费死信队列中的消息
     * @param message
     */
    @RabbitListener(queues = "${simple.dead.real.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeDeadQueue(Message message){
        try {
            log.info("监听消费死信队列中的消息： {} ",new String(message.getBody(),"UTF-8"));

            //mailService.sendEmail();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "${log.user.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeManyQueue1(Message message) {
        try {
            log.info("消费者1监听：{}", new String(message.getBody(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "${log.user.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeManyQueue2(Message message) {
        try {
            log.info("消费者2监听：{}", new String(message.getBody(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
