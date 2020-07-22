package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.OrderReport;
import com.example.rabbitmq.dao.OrderReportDao;
import com.example.rabbitmq.service.OrderReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单报表表-分析级别(OrderReport)表服务实现类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
@Service("orderReportService")
public class OrderReportServiceImpl implements OrderReportService {
    @Resource
    private OrderReportDao orderReportDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderReport queryById(Integer id) {
        return this.orderReportDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<OrderReport> queryAllByLimit(int offset, int limit) {
        return this.orderReportDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param orderReport 实例对象
     * @return 实例对象
     */
    @Override
    public OrderReport insert(OrderReport orderReport) {
        this.orderReportDao.insert(orderReport);
        return orderReport;
    }

    /**
     * 修改数据
     *
     * @param orderReport 实例对象
     * @return 实例对象
     */
    @Override
    public OrderReport update(OrderReport orderReport) {
        this.orderReportDao.update(orderReport);
        return this.queryById(orderReport.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.orderReportDao.deleteById(id) > 0;
    }
}