package com.example.rabbitmq.dao;

import com.example.rabbitmq.entity.ProductBak;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 产品信息表(ProductBak)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public interface ProductBakDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductBak queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProductBak> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param productBak 实例对象
     * @return 对象列表
     */
    List<ProductBak> queryAll(ProductBak productBak);

    /**
     * 新增数据
     *
     * @param productBak 实例对象
     * @return 影响行数
     */
    int insert(ProductBak productBak);

    /**
     * 修改数据
     *
     * @param productBak 实例对象
     * @return 影响行数
     */
    int update(ProductBak productBak);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}