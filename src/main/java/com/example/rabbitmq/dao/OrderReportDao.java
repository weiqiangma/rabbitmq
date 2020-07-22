package com.example.rabbitmq.dao;

import com.example.rabbitmq.entity.OrderReport;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 订单报表表-分析级别(OrderReport)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public interface OrderReportDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderReport queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<OrderReport> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param orderReport 实例对象
     * @return 对象列表
     */
    List<OrderReport> queryAll(OrderReport orderReport);

    /**
     * 新增数据
     *
     * @param orderReport 实例对象
     * @return 影响行数
     */
    int insert(OrderReport orderReport);

    /**
     * 修改数据
     *
     * @param orderReport 实例对象
     * @return 影响行数
     */
    int update(OrderReport orderReport);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}