package com.example.rabbitmq.controller;

import com.example.rabbitmq.entity.UserOrder;
import com.example.rabbitmq.service.UserOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户订单表(UserOrder)表控制层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@RestController
@RequestMapping("userOrder")
public class UserOrderController {
    /**
     * 服务对象
     */
    @Resource
    private UserOrderService userOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserOrder selectOne(Integer id) {
        return this.userOrderService.queryById(id);
    }

}