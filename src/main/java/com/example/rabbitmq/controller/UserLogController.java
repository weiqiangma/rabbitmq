package com.example.rabbitmq.controller;

import com.example.rabbitmq.entity.UserLog;
import com.example.rabbitmq.service.UserLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户操作日志(UserLog)表控制层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@RestController
@RequestMapping("userLog")
public class UserLogController {
    /**
     * 服务对象
     */
    @Resource
    private UserLogService userLogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserLog selectOne(Integer id) {
        return this.userLogService.queryById(id);
    }

}