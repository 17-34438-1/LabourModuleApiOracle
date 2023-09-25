package com.datasoft.LaborModuleAPI.Service.GangAssignServices;

import com.datasoft.LaborModuleAPI.Model.GangAssign;
import com.datasoft.LaborModuleAPI.Repository.GangAssignRepositories.GangAssignInsertRepository;
import com.datasoft.LaborModuleAPI.Utils.CommonDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GangAssignInsertService {
    @Autowired
    private GangAssignInsertRepository gangAssignInsertRepo;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    @Autowired
    @Qualifier("jdbcTemplateOracle")
    private JdbcTemplate OracleDbTemplate;

    CommonDataUtils cu = new CommonDataUtils();

    public Boolean gangExistenceCnt(Long gang_id){
        if(gangAssignInsertRepo.gangExistenceCnt(gang_id) == 1) return true;
        else return false;
    }

    public Boolean laborExistenceCnt(Long labor_info_id){
        if(gangAssignInsertRepo.laborExistenceCnt(labor_info_id) == 1) return true;
        else return false;
    }

    public Boolean workLocationExistenceCnt(Long work_location_id){
        if(gangAssignInsertRepo.workLocationExistenceCnt(work_location_id) == 1) return true;
        else return false;
    }



    public Boolean workCategoryExistenceCnt(Long work_category_id){
        if(gangAssignInsertRepo.workCategoryExistenceCnt(work_category_id) == 1) return true;
        else return false;
    }

    public Boolean isRotationExists(String rotation){

        String Rotation=rotation.replace("_","/");
        System.out.println(Rotation);
        String strChkBayQuery = "SELECT COUNT(*) as rtnValue  FROM vsl_vessel_visit_details WHERE ib_vyg='"+Rotation+"'";
        //String strChkBayQuery = "SELECT COUNT(*) as rtnValue FROM igm_details WHERE Import_Rotation_No='"+Rotation+"'";
        Integer rtnValue = cu.getCountValue("SPARCSN4",strChkBayQuery);

        System.out.println("returnValue:"+rtnValue);


        if(rtnValue==0){
            return false;
        }
        else{
            return true;
        }

    }




//    public Boolean isRotationExists(String rotation){
//        if(gangAssignInsertRepo.isRotationExists(rotation) == 1) return true;
//        else return false;
//    }
//



    class CheckBay implements RowMapper {

        @Override
        public GangAssign mapRow(ResultSet rs, int rowNum) throws SQLException {
            GangAssign gangAssign = new GangAssign();
            gangAssign.setVvd_gkey(rs.getString("vvd_gkey"));
            return gangAssign;
        }
    }

    public Boolean isContainerExists(String container){

        String strChkBayQuery = "SELECT COUNT(*) as rtnValue FROM inv_unit WHERE id='"+container+"'";
        //String strChkBayQuery = "SELECT COUNT(*) as rtnValue FROM igm_details WHERE Import_Rotation_No='"+Rotation+"'";
        Integer rtnValue = cu.getCountValue("SPARCSN4",strChkBayQuery);

        System.out.println("returnValue:"+rtnValue);

        if(rtnValue==0){
            return false;
        }
        else{
            return true;
        }

//        if(gangAssignInsertRepo.isContainerExists(container) == 1) return true;
//        else return false;
    }



//    public Boolean isContainerExists(String container){
//        if(gangAssignInsertRepo.isContainerExists(container) == 1) return true;
//        else return false;
//    }


    public List getVslNameAndVvdGkeyBy_Rotation(String rotation){

        String Rotation=rotation.replace("_","/");
        System.out.println(Rotation);
        String strChkBayQuery = "SELECT vsl_vessel_visit_details.ib_vyg,argo_carrier_visit.gkey AS vvd_gkey,\n" +
                "vsl_vessels.name AS vsl_name\n" +
                "FROM argo_carrier_visit\n" +
                "INNER JOIN vsl_vessel_visit_details ON argo_carrier_visit.cvcvd_gkey=vsl_vessel_visit_details.vvd_gkey\n" +
                "INNER JOIN vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
                "WHERE vsl_vessel_visit_details.ib_vyg='"+Rotation+"' fetch first 1 rows only ";
        List bayCountList=OracleDbTemplate.query(strChkBayQuery,new checkRotation());

        List  listAll = (List) bayCountList.stream().collect(Collectors.toList());

//        return listAll;
//       return gangAssignInsertRepo.getVslNameAndVvdGkeyByRotation(rotation);

        return listAll;
    }

    public List getVslNameAndVvdGkeyByRotation(String rotation){

        String Rotation=rotation.replace("_","/");
        System.out.println(Rotation);
        String strChkBayQuery = "SELECT vsl_vessel_visit_details.ib_vyg,argo_carrier_visit.gkey AS vvd_gkey,\n" +
                "vsl_vessels.name AS vsl_name\n" +
                "FROM argo_carrier_visit\n" +
                "INNER JOIN vsl_vessel_visit_details ON argo_carrier_visit.cvcvd_gkey=vsl_vessel_visit_details.vvd_gkey\n" +
                "INNER JOIN vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
                "WHERE vsl_vessel_visit_details.ib_vyg='"+Rotation+"' fetch first 1 rows only ";
        List bayCountList=OracleDbTemplate.query(strChkBayQuery,new checkRotation());

        List  listAll = (List) bayCountList.stream().collect(Collectors.toList());

//        return listAll;
//       return gangAssignInsertRepo.getVslNameAndVvdGkeyByRotation(rotation);

        return listAll;
    }


    //    public List[] getVslNameAndVvdGkeyByRotation(String rotation){
    //       return gangAssignInsertRepo.getVslNameAndVvdGkeyByRotation(rotation);
    //
    //    }


    class checkRotation implements RowMapper {

        @Override
        public GangAssign mapRow(ResultSet rs, int rowNum) throws SQLException {
            GangAssign gangAssign = new GangAssign();
            gangAssign.setVsl_name(rs.getString("vsl_name"));
            return gangAssign;
        }
    }

    public List getUnitGkeyAndVesselByContainer(String container){

        String strChkBayQuery = "SELECT ib_vyg AS rotation_no,inv_unit.id AS cont_no,inv_unit.gkey AS unit_gkey,\n" +
                "argo_carrier_visit.gkey AS vvd_gkey,vsl_vessels.name AS vsl_name\n" +
                "FROM inv_unit\n" +
                "INNER JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
                "INNER JOIN argo_carrier_visit ON argo_carrier_visit.gkey= inv_unit_fcy_visit.actual_ib_cv\n" +
                "INNER JOIN vsl_vessel_visit_details ON argo_carrier_visit.cvcvd_gkey=vsl_vessel_visit_details.vvd_gkey\n" +
                "INNER JOIN vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
                "WHERE inv_unit.id='"+container+"' ORDER BY vsl_vessel_visit_details.vvd_gkey DESC fetch first 1 rows only";
        List bayCountList=OracleDbTemplate.query(strChkBayQuery,new checkRotation());

        List  listAll = (List) bayCountList.stream().collect(Collectors.toList());

//        return listAll;
//       return gangAssignInsertRepo.getVslNameAndVvdGkeyByRotation(rotation);

        return listAll;

//        return gangAssignInsertRepo.getUnitGkeyAndVesselByContainer(container);
    }


//   public List[] getUnitGkeyAndVesselByContainer(String container){
//        return gangAssignInsertRepo.getUnitGkeyAndVesselByContainer(container);
//    }

    public Integer gangAssignInsertForVesselOperation(Long gang_id, Long work_location_id,Integer shift,String rotation,String bl, String crane, String shed, String yard, String vsl_name,String vvd_gkey,String unit_gkey, String start_time, Date end_time){
        return gangAssignInsertRepo.gangAssignInsertForVesselOperation(gang_id,work_location_id,shift,rotation,bl,crane,shed,yard,vsl_name,vvd_gkey,unit_gkey,start_time,end_time);
    }

    public Integer gangAssignInsertForUnstuffing(Long gang_id, Long work_location_id,Integer shift, String shed, String start_time, Date end_time){
        return gangAssignInsertRepo.gangAssignInsertForUnstuffing(gang_id,work_location_id,shift,shed,start_time,end_time);
    }

    public Integer gangAssignInsertForShed(Long gang_id, Long work_location_id,Integer shift,String rotation,String bl, String shed, String vsl_name,String vvd_gkey,String unit_gkey, String start_time, Date end_time){
        return gangAssignInsertRepo.gangAssignInsertForShed(gang_id,work_location_id,shift,rotation,bl,shed,vsl_name,vvd_gkey,unit_gkey,start_time,end_time);
    }

    public Integer gangAssignInsertForYard(Long gang_id, Long work_location_id,Integer shift, String yard, String start_time, Date end_time){
        return gangAssignInsertRepo.gangAssignInsertForYard(gang_id,work_location_id,shift,yard,start_time,end_time);
    }

    public Integer isGangAssignmentForUnstuffingExists(Long gang_id, Long work_location_id,Integer shift, String shed, String start_time){
        System.out.println("****************************************************************");
        System.out.println("gang_id : " +gang_id+" work_location_id : " +work_location_id+" shift : "+shift+" shed : "+shed+" start_time : " +start_time);
        System.out.println("****************************************************************");
        return gangAssignInsertRepo.isGangAssignmentForUnstuffingExists(gang_id,work_location_id,shift,shed,start_time);
    }

    public Integer lastGangAssignmentForUnstuffingId(Long gang_id, Long work_location_id,Integer shift, String shed, String start_time){
        return gangAssignInsertRepo.lastGangAssignmentForUnstuffingId(gang_id,work_location_id,shift,shed,start_time);
    }

    public Integer gangAssignWiseLaborInsert(Integer gang_assignment_id, Long gang_id, Long labor_id,String start_time){
        return gangAssignInsertRepo.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
    }

    public Integer lastGangAssignmentId(){
        return gangAssignInsertRepo.lastGangAssignmentId();
    }

    public Integer gangAssignmentWiseContainerInsert(Integer gang_assignment_to_workplace_id, String container,String vsl_name,String vvd_gkey,String unit_gkey){
        return gangAssignInsertRepo.gangAssignmentWiseContainerInsert(gang_assignment_to_workplace_id,container,vsl_name,vvd_gkey,unit_gkey);
    }

    public List containerBlockList(String cont_number) throws SQLException {

        String sqlCntBlock = "SELECT nbr_block_unblock_cont_no.*\n" +
                "FROM nbr_block_unblock_cont_no\n" +
                "INNER JOIN nbr_block_unblock_data ON nbr_block_unblock_cont_no.block_unblock_id=nbr_block_unblock_data.id\n" +
                "WHERE cont_no='"+cont_number+"' AND release_flag='DO_NOT_RELEASE' \n" +
                "ORDER BY nbr_block_unblock_cont_no.id DESC LIMIT 1";
        List containerBlock = secondaryDBTemplate.query(sqlCntBlock, new containerBlock());

        return containerBlock;
    }

    class containerBlock implements RowMapper {

        @Override
        public GangAssign mapRow(ResultSet rs, int rowNum) throws SQLException {
            GangAssign gangAssign = new GangAssign();

            return gangAssign;
        }
    }



}
