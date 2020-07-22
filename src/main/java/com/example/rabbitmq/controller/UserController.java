package com.example.rabbitmq.controller;

import com.example.rabbitmq.common.CommonResult;
import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户信息表(User)表控制层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(Integer id) {
        return this.userService.queryById(id);
    }

    @RequestMapping(value = "login")
    public CommonResult login(User user) {
        return userService.login(user);
    }

}