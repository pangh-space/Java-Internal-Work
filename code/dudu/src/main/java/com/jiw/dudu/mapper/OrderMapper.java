package com.jiw.dudu.mapper;

import com.jiw.dudu.entities.Order;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderMapper extends Mapper<Order> {
}