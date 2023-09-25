package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.BerthOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BerthOperatorListService {

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;


    public List berthOperatorList() throws SQLException {

       String sqlContInfo = "SELECT id,Organization_Name AS berth_operator_name FROM organization_profiles " +
                "WHERE Org_Type_id='30' AND ((id!='3346') AND (id!='3347') AND (id!='3348'))";

        List berthOperatorList = secondaryDBTemplate.query(sqlContInfo, new berthOpList());
        List listAll = (List) berthOperatorList.stream().collect(Collectors.toList());

        return listAll;
    }

    public List berthOperatorListByOrgType(Integer user_role_id, Integer org_id) throws SQLException {
        String sqlContInfo = "";
        if(user_role_id==1 || user_role_id.equals(1) || user_role_id.equals("1")){
            sqlContInfo = "SELECT id,Organization_Name AS berth_operator_name FROM organization_profiles " +
                    "WHERE Org_Type_id='30' AND ((id!='3346') AND (id!='3347') AND (id!='3348'))";
        } else {
            sqlContInfo = "SELECT id,Organization_Name AS berth_operator_name FROM organization_profiles " +
                    "WHERE Org_Type_id='30' AND id='"+org_id+"'";
        }

        List berthOperatorList = secondaryDBTemplate.query(sqlContInfo, new berthOpList());
        List listAll = (List) berthOperatorList.stream().collect(Collectors.toList());

        return listAll;
    }


    class berthOpList implements RowMapper {

        @Override
        public BerthOperator mapRow(ResultSet rs, int rowNum) throws SQLException {
            BerthOperator berthOperator = new BerthOperator();
            berthOperator.setId(rs.getLong("id"));
            berthOperator.setName(rs.getString("berth_operator_name"));
            return berthOperator;
        }
    }
}
