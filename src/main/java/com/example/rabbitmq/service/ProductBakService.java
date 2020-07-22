package com.example.rabbitmq.service;

import com.example.rabbitmq.entity.ProductBak;
import java.util.List;

/**
 * 产品信息表(ProductBak)表服务接口
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public interface ProductBakService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProductBak queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ProductBak> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param productBak 实例对象
     * @return 实例对象
     */
    ProductBak insert(ProductBak productBak);

    /**
     * 修改数据
     *
     * @param productBak 实例对象
     * @return 实例对象
     */
    ProductBak update(ProductBak productBak);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}