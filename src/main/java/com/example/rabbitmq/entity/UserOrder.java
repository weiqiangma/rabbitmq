package com.example.rabbitmq.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户订单表(UserOrder)实体类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public class UserOrder implements Serializable {
    private static final long serialVersionUID = -36309791528852933L;
    
    private Integer id;
    /**
    * 订单编号
    */
    private String orderNo;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 状态(1=已保存；2=已付款；3=已取消)
    */
    private Integer status;
    /**
    * 下单时间
    */
    private Date createTime;
    
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}