package com.example.rabbitmq.controller;

import com.example.rabbitmq.common.CommonResult;
import com.example.rabbitmq.service.impl.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/8/25.
 */
@RestController
@RequestMapping(value = "init")
public class ConcurrencyController {

    private static final Logger log= LoggerFactory.getLogger(ConcurrencyController.class);

    @Autowired
    private InitService initService;

    @RequestMapping(value = "/robbing/thread")
    public CommonResult robbingThread(){
        initService.generateMultiThread();
        return CommonResult.success("ok");
    }
}







































