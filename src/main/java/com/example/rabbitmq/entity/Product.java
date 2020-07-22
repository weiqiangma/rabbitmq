package com.example.rabbitmq.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 商品信息表(Product)实体类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 134211775078165698L;
    
    private Integer id;
    /**
    * 商品编号
    */
    private String productNo;
    /**
    * 库存量
    */
    private Integer total;
    
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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