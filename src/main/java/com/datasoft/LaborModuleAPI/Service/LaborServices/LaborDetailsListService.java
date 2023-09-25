package com.datasoft.LaborModuleAPI.Service.LaborServices;

import com.datasoft.LaborModuleAPI.DAO.LaborByEntryPassDAO;
import com.datasoft.LaborModuleAPI.Model.Labor;
import com.datasoft.LaborModuleAPI.Repository.LaborRepositories.LaborDetailsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaborDetailsListService implements RowMapper {
    @Autowired
    private LaborDetailsListRepository laborListRepository;

    @Autowired
    private LaborByEntryPassDAO laborByEntryPassDAO;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    public List<Labor> laborList(){ return laborListRepository.laborList(); }

    public List<Labor> getLaborInfoByTypeAndDesignation(Long labor_type_id, Long designation_category_id){
        return laborListRepository.getLaborInfoByTypeAndDesignation(labor_type_id,designation_category_id);
    }

    public List<Labor> availableLaborList(Long labor_type_id, Long designation_category_id, Long berth_operator_id, Integer gang_id){
        return laborListRepository.availableLaborList(labor_type_id,designation_category_id,berth_operator_id,gang_id);
    }

    public Labor laborInfoById(Long id){ return laborListRepository.laborInfoById(id);}

    public Integer laborChkById(String laborId){ return laborListRepository.laborChkById(laborId); }

    public Integer laborAssignmentChkById(String laborId) {return laborListRepository.laborAssignmentChkById(laborId);}

    public Labor laborDetailsinfoListByLaborId(String laborId){ return laborListRepository.laborDetailsinfoListByLaborId(laborId);}

    public List laborCount(String entry_pass_no){
        List laborCountRslt = laborByEntryPassDAO.getLaborCount(entry_pass_no);
        return laborCountRslt;
    }

    public Labor laborInfo(String entry_pass_no) throws SQLException {

        String sqlLaborInfo = "SELECT agent_name AS NAME,nid_number AS nid,mobile_number AS phone,valid_till_dt AS expiration_date\n" +
                "FROM cchaportdb.vcms_vehicle_agent \n" +
                "WHERE cchaportdb.vcms_vehicle_agent.card_number='"+entry_pass_no+"' ORDER BY cchaportdb.vcms_vehicle_agent.id DESC LIMIT 1";
        List laborInfo = secondaryDBTemplate.query(sqlLaborInfo, new LaborDetailsListService());
        List laborByEntryPassNo = (List) laborInfo.stream().collect(Collectors.toList());

        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.16.42:3306/cchaportdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "user1", "user1test");
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(sqlLaborInfo);

        Labor laborInfoObj = (Labor) mapRow(rs,laborInfo.size());
        return laborInfoObj;

    }
    @Override
    public Labor mapRow(ResultSet rs, int rowNum) throws SQLException{
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

    public List<Labor> laborListByOrg(Integer org_id){
        return laborListRepository.getLaborListByOrg(org_id);

    }
}
