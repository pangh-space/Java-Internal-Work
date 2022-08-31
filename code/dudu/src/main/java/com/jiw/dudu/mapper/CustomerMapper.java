package com.jiw.dudu.mapper;


import com.jiw.dudu.entities.Customer;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerMapper extends Mapper<Customer> {
}