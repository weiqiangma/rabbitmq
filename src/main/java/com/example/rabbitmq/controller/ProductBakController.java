package com.example.rabbitmq.controller;

import com.example.rabbitmq.common.CommonResult;
import com.example.rabbitmq.entity.ProductBak;
import com.example.rabbitmq.service.ProductBakService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 产品信息表(ProductBak)表控制层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@RestController
@RequestMapping("productBak")
public class ProductBakController {
    /**
     * 服务对象
     */
    @Resource
    private ProductBakService productBakService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ProductBak selectOne(Integer id) {
        return this.productBakService.queryById(id);
    }

}