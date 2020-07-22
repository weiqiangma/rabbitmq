package com.example.rabbitmq.controller;

import com.example.rabbitmq.entity.OrderReport;
import com.example.rabbitmq.service.OrderReportService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单报表表-分析级别(OrderReport)表控制层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@RestController
@RequestMapping("orderReport")
public class OrderReportController {
    /**
     * 服务对象
     */
    @Resource
    private OrderReportService orderReportService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public OrderReport selectOne(Integer id) {
        return this.orderReportService.queryById(id);
    }

}