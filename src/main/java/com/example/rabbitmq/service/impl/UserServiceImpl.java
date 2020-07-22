package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.common.CommonResult;
import com.example.rabbitmq.dao.UserLogDao;
import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.dao.UserDao;
import com.example.rabbitmq.entity.UserLog;
import com.example.rabbitmq.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * 用户信息表(User)表服务实现类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserDao userDao;
    @Resource
    private UserLogDao userLogDao;
    @Resource
    ObjectMapper objectMapper;
    @Resource
    RabbitTemplate rabbitTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    public CommonResult login(User user) {
        List<User> list = userDao.queryAll(user);
        try {
            if (list.size() == 1) {
                UserLog userLog = new UserLog(user.getUserName(), "Login", "login", objectMapper.writeValueAsString(user), new Date());
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange("local.log.user.exchange");
                rabbitTemplate.setRoutingKey("local.log.user.routing.key");
                Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(userLog)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
                rabbitTemplate.convertAndSend(message);
            } else {
                return CommonResult.failed("用户名或密码错误");
            }
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return CommonResult.success("登录成功");
    }
}