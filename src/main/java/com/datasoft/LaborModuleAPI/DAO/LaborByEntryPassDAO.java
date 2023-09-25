package com.datasoft.LaborModuleAPI.DAO;

import com.datasoft.LaborModuleAPI.Model.Labor;
import com.datasoft.LaborModuleAPI.Service.LaborServices.LaborDetailsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Repository
public class LaborByEntryPassDAO {

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    public List getLaborCount(String entry_pass_no) {
        String sqlLaborInfo = "SELECT agent_name AS NAME,nid_number AS nid,mobile_number AS phone,valid_till_dt AS expiration_date\n" +
                "FROM cchaportdb.vcms_vehicle_agent \n" +
                "WHERE cchaportdb.vcms_vehicle_agent.card_number='"+entry_pass_no+"' ORDER BY cchaportdb.vcms_vehicle_agent.id DESC LIMIT 1";
        List laborInfo = secondaryDBTemplate.query(sqlLaborInfo, new LaborDetailsMapper());
        List laborByEntryPassNo = (List) laborInfo.stream().collect(Collectors.toList());
        return laborByEntryPassNo;
    }

    class LaborDetailsMapper implements RowMapper {
        @Override
        public Labor mapRow(ResultSet rs, int rowNum) throws SQLException {
            String name = "";
            String nid = "";
            String phone = "";
            Date expiration_date;

            Labor labor_details = new Labor();
            while (rs.next()) {
                name = rs.getString("name");
                nid = rs.getString("nid");
                phone = rs.getString("phone");
                expiration_date = rs.getDate("expiration_date");

                labor_details.setName(name);
                labor_details.setNid(nid);
                labor_details.setPhone(phone);
                labor_details.setExpiration_date(expiration_date);
            }
            return labor_details;
        }
    }
}
