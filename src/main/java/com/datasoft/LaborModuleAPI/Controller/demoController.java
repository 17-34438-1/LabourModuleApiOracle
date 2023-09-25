package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import com.datasoft.LaborModuleAPI.Model.Orders;
import com.datasoft.LaborModuleAPI.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class demoController {
    @Autowired
    private OrderService order_service;

    @Autowired
    @Qualifier("jdbcTemplatePrimary")
    private JdbcTemplate primaryDBTemplate;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public @ResponseBody List<Orders> index(){
        List<Orders> orders_list = new ArrayList<Orders>();
        String exception = null;
        try
        {
            orders_list = order_service.listOrders();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return orders_list ;
    }

    @RequestMapping(value = "/getorders")
    public String getMYUser() {
        Map<String, Object> map = new HashMap<String, Object>();
        String query = " select * from orders ORDER BY id DESC LIMIT 1";
        try {
            map = secondaryDBTemplate.queryForMap(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("We got---" + map.toString());
        return "MySQL Data: " + map.toString();
    }
}
