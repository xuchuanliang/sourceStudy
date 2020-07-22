package com.hkbt.test2;

import org.springframework.stereotype.Component;

@Component
public class UserService {
    private OrderService orderService;
    public void query(){
        System.err.println(orderService);
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
