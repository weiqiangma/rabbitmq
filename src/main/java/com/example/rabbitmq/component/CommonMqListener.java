package com.example.rabbitmq.component;

import com.example.rabbitmq.dao.UserLogDao;
import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.entity.UserLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

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
    @RabbitListener(queues = "${log.user.queue.name}", containerFactory = "singleListenerContainer")
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

    @RabbitListener(queues = "testDirectQueue", containerFactory = "ConsumeAckListenerContainer")
    public void consumeTestDirectQueue(Message message, Channel channel) {
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("Direct消费者监听1：{}", new String(message.getBody(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@RabbitListener(queues = "testDirectQueue", containerFactory = "multiListenerContainer")
    public void consumeTestDirectQueue2(Message message) {
        try {
            log.info("Direct消费者监听2：{}", new String(message.getBody(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "testFanOutQueue")
    public void consumeTestFanOutQueue(Message message) {
        try {
            log.info("FanOut消费者监听1：{}", new String(message.getBody(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "testFanOutQueue2")
    public void consumeTestFanOutQueue2(Message message) {
        try {
            log.info("FanOut消费者监听2：{}", new String(message.getBody(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
