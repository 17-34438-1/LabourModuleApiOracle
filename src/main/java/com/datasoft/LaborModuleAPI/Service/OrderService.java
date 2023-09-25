package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Model.Orders;
import com.datasoft.LaborModuleAPI.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository order_repo;

    @Autowired
    @Qualifier("jdbcTemplatePrimary")
    private JdbcTemplate primaryDBTemplate;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    ResponseMessage jsonMsg;

    public List<Orders> listOrders(){
        return order_repo.getAllOrders();
    }
}
