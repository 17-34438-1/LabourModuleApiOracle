package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.LCLAssignment;
import com.datasoft.LaborModuleAPI.Model.LCLRotation;
import com.datasoft.LaborModuleAPI.Repository.LCLAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LCLAssignmentService {

    @Autowired
    @Qualifier("jdbcTemplatePrimary")
    private JdbcTemplate primaryDBTemplate;




    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    @Autowired
    @Qualifier("jdbcTemplateOracle")
    private JdbcTemplate OracleDbTemplate;

    @Autowired
    private LCLAssignmentRepository lclAssignmentRepository;

    public List rotAndBlList(String assignment_date, String cont_loc_shed) throws SQLException {

        String sqlRotAndBlList = "SELECT Import_Rotation_No,BL_No\n" +
                "FROM lcl_assignment_detail\n" +
                "INNER JOIN igm_details ON igm_details.id=lcl_assignment_detail.igm_detail_id\n" +
                "WHERE assignment_date='"+assignment_date+"' AND cont_loc_shed='"+cont_loc_shed+"'";
        List rotAndBlList = secondaryDBTemplate.query(sqlRotAndBlList, new rotAndBl());
        List listAll = (List) rotAndBlList.stream().collect(Collectors.toList());

        return listAll;
    }

    public List containerList(String assignment_date, String cont_loc_shed) throws SQLException {

        String sqlContainerList = "SELECT cont_number\n" +
                "FROM lcl_assignment_detail\n" +
                "INNER JOIN igm_detail_container ON igm_detail_container.id=lcl_assignment_detail.igm_cont_detail_id\n" +
                "WHERE assignment_date='"+assignment_date+"' AND cont_loc_shed='"+cont_loc_shed+"'";
        List contList = secondaryDBTemplate.query(sqlContainerList, new containers());
        List listAll = (List) contList.stream().collect(Collectors.toList());

        return listAll;
    }

    public List rotList(String assignment_date, String cont_loc_shed) throws SQLException {

        String sqlRotList = "SELECT DISTINCT Import_Rotation_No FROM(\n" +
                "SELECT Import_Rotation_No,BL_No\n" +
                "FROM lcl_assignment_detail\n" +
                "INNER JOIN igm_details ON igm_details.id=lcl_assignment_detail.igm_detail_id\n" +
                "WHERE assignment_date='"+assignment_date+"' AND cont_loc_shed='"+cont_loc_shed+"') AS tbl";
        List rotList = secondaryDBTemplate.query(sqlRotList, new rotation());
        List listAll = (List) rotList.stream().collect(Collectors.toList());

        return listAll;
    }

    public List blList(String assignment_date, String cont_loc_shed) throws SQLException {

        String sqlRotList = "SELECT DISTINCT BL_No FROM(\n" +
                "SELECT Import_Rotation_No,BL_No\n" +
                "FROM lcl_assignment_detail\n" +
                "INNER JOIN igm_details ON igm_details.id=lcl_assignment_detail.igm_detail_id\n" +
                "WHERE assignment_date='"+assignment_date+"' AND cont_loc_shed='"+cont_loc_shed+"') AS tbl";
        List blList = secondaryDBTemplate.query(sqlRotList, new bl());
        List listAll = (List) blList.stream().collect(Collectors.toList());

        return listAll;
    }



    public List notAssignedRotationList() throws SQLException {

        String sqlAssignRotInfo = "SELECT argo_carrier_visit.gkey AS id,ib_vyg AS Import_Rotation_No,'' AS bl_no, '' AS cont_no,'' AS assignment_date,'' AS cont_loc_shed\n" +
                "FROM argo_carrier_visit\n" +
                "INNER JOIN argo_visit_details ON argo_visit_details.gkey=argo_carrier_visit.cvcvd_gkey\n" +
                "INNER JOIN vsl_vessel_visit_details ON vsl_vessel_visit_details.vvd_gkey=argo_visit_details.gkey\n" +
                "INNER JOIN vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
                "WHERE argo_carrier_visit.phase IN('20INBOUND','40WORKING') ";

        List OracleList = OracleDbTemplate.query(sqlAssignRotInfo, new AsaignRotInfo());



      List  listAll = (List) OracleList.stream().collect(Collectors.toList());


      return listAll;
    }

    class AsaignRotInfo implements RowMapper {

        @Override
        public LCLAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
            LCLAssignment lclAssignment = new LCLAssignment();
            String rotation= rs.getString("Import_Rotation_No");

            lclAssignment.setBl_no(rs.getString("BL_No"));
            lclAssignment.setCont_no("");

            String Query="SELECT rotation FROM ctmsmis.lm_assign_gang_to_workplace";
            List<LCLAssignment> resultList=primaryDBTemplate.query(Query,new LcLRotation());
            String roNo="";
            for(int i=0;i<resultList.size();i++){
                LCLAssignment lclAssignment1 = new LCLAssignment();
                lclAssignment1= resultList.get(i);
                roNo=lclAssignment1.getImport_rotation_no();

                if(roNo!=rotation){
                    lclAssignment.setImport_rotation_no(rotation);
                }
            }


            return lclAssignment;
        }
    }



    class rotAndBl implements RowMapper {

        @Override
        public LCLAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
            LCLAssignment lclAssignment = new LCLAssignment();
            lclAssignment.setImport_rotation_no(rs.getString("Import_Rotation_No"));
            lclAssignment.setBl_no(rs.getString("BL_No"));
            lclAssignment.setCont_no("");
            return lclAssignment;
        }
    }



    class LcLRotation implements RowMapper {

        @Override
        public LCLAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
            LCLAssignment lclRotation = new LCLAssignment();
            lclRotation.setImport_rotation_no(rs.getString("rotation"));
            return lclRotation;
        }
    }

    class containers implements RowMapper {

        @Override
        public LCLAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
            LCLAssignment lclAssignment = new LCLAssignment();
            lclAssignment.setImport_rotation_no("");
            lclAssignment.setBl_no("");
            lclAssignment.setCont_no(rs.getString("cont_number"));

            return lclAssignment;
        }
    }

    class rotation implements RowMapper {

        @Override
        public LCLAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
            LCLAssignment lclAssignment = new LCLAssignment();
            lclAssignment.setImport_rotation_no(rs.getString("Import_Rotation_No"));

            return lclAssignment;
        }
    }

    class bl implements RowMapper {

        @Override
        public LCLAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
            LCLAssignment lclAssignment = new LCLAssignment();
            lclAssignment.setBl_no(rs.getString("BL_No"));

            return lclAssignment;
        }
    }
}
