package com.example.rabbitmq.controller;

import com.example.rabbitmq.entity.ProductRobbingRecord;
import com.example.rabbitmq.service.ProductRobbingRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 抢单记录表(ProductRobbingRecord)表控制层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@RestController
@RequestMapping("productRobbingRecord")
public class ProductRobbingRecordController {
    /**
     * 服务对象
     */
    @Resource
    private ProductRobbingRecordService productRobbingRecordService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ProductRobbingRecord selectOne(Integer id) {
        return this.productRobbingRecordService.queryById(id);
    }

}