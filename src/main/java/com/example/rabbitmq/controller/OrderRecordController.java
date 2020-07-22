package com.example.rabbitmq.controller;

import com.example.rabbitmq.entity.OrderRecord;
import com.example.rabbitmq.service.OrderRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单记录表-业务级别(OrderRecord)表控制层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@RestController
@RequestMapping("orderRecord")
public class OrderRecordController {
    /**
     * 服务对象
     */
    @Resource
    private OrderRecordService orderRecordService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public OrderRecord selectOne(Integer id) {
        return this.orderRecordService.queryById(id);
    }

}