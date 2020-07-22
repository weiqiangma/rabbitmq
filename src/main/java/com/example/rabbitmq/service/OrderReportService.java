package com.example.rabbitmq.service;

import com.example.rabbitmq.entity.OrderReport;
import java.util.List;

/**
 * 订单报表表-分析级别(OrderReport)表服务接口
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public interface OrderReportService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderReport queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OrderReport> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param orderReport 实例对象
     * @return 实例对象
     */
    OrderReport insert(OrderReport orderReport);

    /**
     * 修改数据
     *
     * @param orderReport 实例对象
     * @return 实例对象
     */
    OrderReport update(OrderReport orderReport);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}