package com.jiw.dudu.controller;

import com.jiw.dudu.entities.Customer;
import com.jiw.dudu.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description CustomerController
 * @Author pangh
 * @Date 2022年08月10日
 * @Version v1.0.0
 */
@Api(tags = "客户Customer接口")
@RestController
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("CompletableFuture多接口并行调用案例")
    @RequestMapping(value = "/customercf/{typeId}", method = RequestMethod.GET)
    public Customer findCustomerByCompletableFuture(@PathVariable("typeId") String typeId) {
        return customerService.findCustomerByCompletableFuture(typeId);
    }

}