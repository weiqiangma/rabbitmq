package com.example.rabbitmq.dao;

import com.example.rabbitmq.entity.UserLog;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户操作日志(UserLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public interface UserLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserLog queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userLog 实例对象
     * @return 对象列表
     */
    List<UserLog> queryAll(UserLog userLog);

    /**
     * 新增数据
     *
     * @param userLog 实例对象
     * @return 影响行数
     */
    int insert(UserLog userLog);

    /**
     * 修改数据
     *
     * @param userLog 实例对象
     * @return 影响行数
     */
    int update(UserLog userLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}