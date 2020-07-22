package com.example.rabbitmq.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 抢单记录表(ProductRobbingRecord)实体类
 *
 * @author makejava
 * @since 2020-07-16 13:39:43
 */
public class ProductRobbingRecord implements Serializable {
    private static final long serialVersionUID = 187882385357724532L;
    
    private Integer id;
    /**
    * 手机号
    */
    private String mobile;
    /**
    * 产品Id
    */
    private Integer productId;
    /**
    * 抢单时间
    */
    private Date robbingTime;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getRobbingTime() {
        return robbingTime;
    }

    public void setRobbingTime(Date robbingTime) {
        this.robbingTime = robbingTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}