package com.jiw.dudu.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Description Order
 * @Author pangh
 * @Date 2022年08月31日
 * @Version v1.0.0
 */
@Data
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String orderSerial;

    private Date createTime;

    public Order(String orderSerial,Date createTime){
        this.orderSerial = orderSerial;
        this.createTime = createTime;
    }

}