package com.datasoft.LaborModuleAPI.Repository;

import com.datasoft.LaborModuleAPI.Model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Transactional
@Repository
public class OrderRepository {
    @Autowired
    @Qualifier("jdbcTemplatePrimary")
    private JdbcTemplate primaryDBTemplate;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    public List getAllOrders() {
        String sql1 = "SELECT id,name FROM cchaportdb.orders";
        //get list from 42
        List list1 = secondaryDBTemplate.query(sql1, new UserRowMapper());

        String sql2 = "SELECT id,test as name FROM ctmsmis.testtbl";
        //get list from 21
        List list2 = primaryDBTemplate.query(sql2, new UserRowMapper());


        //List listAll = (List) list1.stream().collect(Collectors.toList());

        List listAll = (List) Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
        return listAll;
    }

    class UserRowMapper implements RowMapper {

        @Override
        public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
            Orders order = new Orders();
            order.setId(rs.getLong("id"));
            order.setName(rs.getString("name"));
            return order;
        }

    }
}
