package com.jiw.dudu.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description Customer
 * @Author pangh
 * @Date 2022年08月10日
 * @Version v1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String cname;

    private Integer age;

    private String phone;

    private Byte sex;

    private Date birth;


    public Customer(Integer id, String cname) {
        this.id = id;
        this.cname = cname;
    }
}