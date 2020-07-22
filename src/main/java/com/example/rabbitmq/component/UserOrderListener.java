package com.example.rabbitmq.component;

import com.example.rabbitmq.dao.UserOrderDao;
import com.example.rabbitmq.service.impl.ConcurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date 2020/7/22 15:58
 * @Author mawkun
 */
@Component("userOrderListener")
public class UserOrderListener implements ChannelAwareMessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserOrderDao userOrderDao;

    @Autowired
    private ConcurrencyService concurrencyService;

    Logger log = LoggerFactory.getLogger(UserOrderListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            String mobile = new String(body, "UTF-8");
            log.info("监听到抢单手机号：{}",mobile);
            concurrencyService.manageRobbing(String.valueOf(mobile));
            channel.basicAck(tag, true);
        }catch (Exception e) {
            log.error("用户商城抢单发生异常：",e.fillInStackTrace());
            channel.basicReject(tag, false);
        }
    }
}
