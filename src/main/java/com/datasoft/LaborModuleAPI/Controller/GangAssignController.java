package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.*;
import com.datasoft.LaborModuleAPI.Service.GangAssignServices.GangAssignDeleteService;
import com.datasoft.LaborModuleAPI.Service.GangAssignServices.GangAssignEditService;
import com.datasoft.LaborModuleAPI.Service.GangAssignServices.GangAssignInsertService;
import com.datasoft.LaborModuleAPI.Service.GangAssignServices.GangAssignListService;
import com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices.LaborAssignmentListService;
import com.datasoft.LaborModuleAPI.Utils.CommonDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/GangAssign")
public class GangAssignController {
    @Autowired
    private GangAssignInsertService gangAssignInsertService;

    @Autowired
    private GangAssignListService gangAssignListService;

    @Autowired
    private GangAssignEditService gangAssignEditService;

    @Autowired
    private GangAssignDeleteService gangAssignDeleteService;

    @Autowired
    private LaborAssignmentListService laborAssignmentListService;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/getLaborTypeById/{rotation}", method = RequestMethod.GET)
    public Boolean getLaborTypeById(@PathVariable("rotation") String rotation){

        //String Rotation=rotation.replace('/','_');

        Boolean laborType =gangAssignInsertService.isRotationExists(rotation);
        return laborType;
    }

//    @RequestMapping(value = "/getLaborTypeById/{rotation}", method = RequestMethod.GET)
//    public List getLaborTypeById(@PathVariable("rotation") String rotation){
//
//        //List[] laborType =gangAssignInsertService.getVslNameAndVvdGkeyByRotation(rotation);
//        List<GangAssign> vslNameAndVvvGkey = gangAssignInsertService.getVslNameAndVvdGkeyBy_Rotation(rotation);
//        String vsl_name="";
//        String vvd_gkey="";
//        for(int cnt=0;cnt<vslNameAndVvvGkey.size();cnt++){
//            GangAssign gangAssign=new GangAssign();
//            gangAssign= vslNameAndVvvGkey.get(cnt);
//            vsl_name = gangAssign.getVsl_name();
//            vvd_gkey = gangAssign.getVvd_gkey();
//            System.out.println("vsl_name:"+vsl_name);
//            System.out.println("vvd_gkey:"+vvd_gkey);
//
//        }
//
//        return vslNameAndVvvGkey;
//    }

//    @RequestMapping(value = "/UpdateEdi",method = RequestMethod.PUT, consumes="application/json")
//    public Boolean UpdateEdiDeclarationByIdAndLogin(@RequestBody EdiDeclarationModel todaysEdiDeclaration) throws IOException {
//        String file_download_by = todaysEdiDeclaration.getFile_download_by();
//        String Rotation=rotation.replace('/','_');
//        Boolean isLaborTypeExists =todaysEdiDeclarationService.UpdateEdiDeclarationByIdAndLogin(file_download_by,id);
//        return  isLaborTypeExists;
//    }


    @RequestMapping(value = "/AddGangAssignment_List", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> index(@RequestBody GangAssign gangAssign) throws IOException, SQLException {
        Long gang_id = gangAssign.getGang_id();
        Long work_location_id = gangAssign.getWork_location_id();
        Long work_category_id = gangAssign.getWork_category_id();
        Integer shift = gangAssign.getShift();
        String crane = gangAssign.getCrane();
        String shed = gangAssign.getShed();
        String start_time = gangAssign.getStart_time();
        Date end_time = gangAssign.getEnd_time();

        String rotation = "";

        String yard = "";
        String bl = "";
        String vsl_name = "";
        String vvd_gkey = null;
        String unit_gkey = null;

        CommonDataUtils cu = new CommonDataUtils();

        if(gang_id == null || gang_id.equals("") || gang_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Gang ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(work_location_id == null || work_location_id.equals("") || work_location_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Location can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(work_category_id == null || work_category_id.equals("") || work_category_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Category can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(shift == null || shift.equals("") || shift.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Shift can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(start_time == null || start_time.equals("") || start_time.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Starting time can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(end_time == null || end_time.equals("") || end_time.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Ending time can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else
        {
            List<LaborAssignToGang> laborAssignList = new ArrayList<LaborAssignToGang>();
            laborAssignList = laborAssignmentListService.laborAssignmentListByGangId(gang_id);

            Boolean workCategoryExistenceCnt = gangAssignInsertService.workCategoryExistenceCnt(work_category_id);
            if(workCategoryExistenceCnt){
                Boolean gangExistenceCnt = gangAssignInsertService.gangExistenceCnt(gang_id);
                if(gangExistenceCnt){
                    Boolean workLocationExistenceCnt = gangAssignInsertService.workLocationExistenceCnt(work_location_id);
                    if(workLocationExistenceCnt){
                        if(work_category_id == 2 || work_category_id==4 || work_category_id==12){
                            // 'Discharging from Vessel Operation' OR 'Loading to Vessel Operation' OR 'Loading and Discharging'
                            System.out.println("Vessel Discharging Operation OR Vessel Loading Operation");
                            rotation = gangAssign.getRotation();
                            Boolean isRotaionExists = gangAssignInsertService.isRotationExists(rotation);
                            if(isRotaionExists){
                                List<GangAssign> vslNameAndVvvGkey = gangAssignInsertService.getVslNameAndVvdGkeyByRotation(rotation);
                                for(int cnt=0;cnt<vslNameAndVvvGkey.size();cnt++){
                                    gangAssign=vslNameAndVvvGkey.get(cnt);
                                    vsl_name = gangAssign.getVsl_name();
                                    vvd_gkey = gangAssign.getVvd_gkey();
                                    System.out.println("vsl_name:"+vsl_name);
                                    System.out.println("vvd_gkey:"+vvd_gkey);


                                }
                                if(laborAssignList.size() == 0){
                                    responseMessage = new ResponseMessage( "No labor in selected gang.");
                                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                } else {
                                    Integer gangAssignEntry = gangAssignInsertService.gangAssignInsertForVesselOperation(gang_id,work_location_id,shift,rotation,bl,crane,shed,yard,vsl_name,vvd_gkey,unit_gkey,start_time,end_time);
                                    if(gangAssignEntry.equals(1) || gangAssignEntry.equals("1")){
                                        Integer gang_assignment_id = gangAssignInsertService.lastGangAssignmentId();
                                        for(int i = 0; i<laborAssignList.size();i++){
                                            Long labor_id = laborAssignList.get(i).getLabor_id();
                                            gangAssignInsertService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
                                        }
                                        responseMessage = new ResponseMessage( "Gang Assignment Successful.");
                                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                                    } else {
                                        responseMessage = new ResponseMessage( "Sorry, Could not assign gang.");
                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                    }
                                }
                            } else {
                                responseMessage = new ResponseMessage( "Sorry! Invalid Rotation.");
                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                            }
                        }



                        else if (work_category_id == 6) {
                            // Unstuffing
                            Integer gang_assignment_id;
                            if(laborAssignList.size() == 0){
                                responseMessage = new ResponseMessage( "No labor in selected gang.");
                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                            } else {
                                gangAssignInsertService.gangAssignInsertForUnstuffing(gang_id,work_location_id,shift,shed,start_time,end_time);
                                gang_assignment_id = gangAssignInsertService.lastGangAssignmentId();
                                for(int i = 0; i<laborAssignList.size();i++){
                                    Long labor_id = laborAssignList.get(i).getLabor_id();
                                    gangAssignInsertService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
                                }
                                String container_array[] = gangAssign.getContainer();
                                for(int k=0;k<container_array.length;k++){
                                    String container = container_array[k];
                                    List<Object> blockedContainer = new ArrayList<Object>();
                                    blockedContainer = gangAssignInsertService.containerBlockList(container);
                                    if(blockedContainer.size()==0){
                                        String query = "SELECT COUNT(*) AS rtnValue FROM (\n" +
                                                "SELECT cont_number FROM lcl_assignment_detail\n" +
                                                "INNER JOIN igm_detail_container ON igm_detail_container.id=lcl_assignment_detail.igm_cont_detail_id\n" +
                                                "WHERE lcl_assignment_detail.assignment_date='"+ start_time +"' AND igm_detail_container.cont_number='"+ container +"'\n" +
                                                "UNION\n" +
                                                "SELECT cont_number FROM lcl_assignment_detail\n" +
                                                "INNER JOIN igm_sup_detail_container ON igm_sup_detail_container.id=lcl_assignment_detail.igm_cont_detail_id\n" +
                                                "WHERE lcl_assignment_detail.assignment_date='"+ start_time +"' AND igm_sup_detail_container.cont_number='"+ container +"'\n" +
                                                ") AS tbl";
                                        Integer rtnValue = cu.getCountValue("IGM",query);
                                        if(rtnValue==0)
                                        {
                                            responseMessage = new ResponseMessage("Sorry! No assignment found for the container " + container);
                                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                        } else {
                                            Boolean isContainerExists = gangAssignInsertService.isContainerExists(container);
                                            List<GangAssign> getUnitGkeyAndVesselByContainer = gangAssignInsertService.getUnitGkeyAndVesselByContainer(container);
                                            for(int cnt=0;cnt<getUnitGkeyAndVesselByContainer.size();cnt++){
                                                gangAssign=getUnitGkeyAndVesselByContainer.get(cnt);
                                                unit_gkey=gangAssign.getUnit_gkey();
                                                vvd_gkey =gangAssign.getVvd_gkey();
                                                System.out.println("vsl_name:"+vsl_name);
                                                vsl_name =gangAssign.getVsl_name();
                                                System.out.println("vvd_gkey:"+vvd_gkey);
                                                System.out.println("unit_gkey:"+unit_gkey);

                                            }
                                            Integer containerInsert = gangAssignInsertService.gangAssignmentWiseContainerInsert(gang_assignment_id,container,vsl_name,vvd_gkey,unit_gkey);

                                        }
                                    }
                                }
                                responseMessage = new ResponseMessage("Gang Assignment Successful.");
                                return new ResponseEntity(responseMessage, HttpStatus.OK);
                            }
                        }



                        else if(work_category_id == 8){
                            // Delivery from Shed
                            rotation = gangAssign.getRotation();
                            bl = gangAssign.getBl();

                            String queryChkNBRBlockUnblodk = "SELECT COUNT(*) AS rtnValue FROM nbr_block_unblock_data \n" +
                                    "WHERE rotation_no='" + rotation + "' AND bl_ref='" + bl + "' AND release_flag='DO_NOT_RELEASE'";
                            Integer cntBlocked = cu.getCountValue("IGM", queryChkNBRBlockUnblodk);
                            if (cntBlocked == 0 || cntBlocked.equals(0) || cntBlocked.equals("0")) {
                                String query = "SELECT COUNT(*) AS rtnValue FROM oracle_nts_data\n" +
                                        "WHERE imp_rot_no='" + rotation + "' AND bl_no='" + bl + "'";
                                Integer rtnValue = cu.getCountValue("IGM", query);
                                System.out.println("rtn" + rtnValue);
                                if (rtnValue == 0) {
                                    responseMessage = new ResponseMessage("Payment is not completed");
                                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                } else {
                                    rotation = gangAssign.getRotation();
                                    bl = gangAssign.getBl();
                                    Boolean isRotaionExists = gangAssignInsertService.isRotationExists(rotation);
                                    if (isRotaionExists) {
                                        List<GangAssign> vslNameAndVvvGkey = gangAssignInsertService.getVslNameAndVvdGkeyByRotation(rotation);
                                        for (int cnt = 0; cnt < vslNameAndVvvGkey.size(); cnt++) {
                                            gangAssign=vslNameAndVvvGkey.get(cnt);
                                            vsl_name = gangAssign.getVsl_name();
                                            vvd_gkey = gangAssign.getVvd_gkey();
                                            System.out.println("vsl_name:"+vsl_name);
                                            System.out.println("vvd_gkey:"+vvd_gkey);

                                        }
                                        if (laborAssignList.size() == 0) {
                                            responseMessage = new ResponseMessage("No labor in selected gang.");
                                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                        } else {
                                            Integer gangAssignEntry = gangAssignInsertService.gangAssignInsertForShed(gang_id,work_location_id,shift,rotation,bl,shed,vsl_name,vvd_gkey,unit_gkey,start_time,end_time);
                                            if(gangAssignEntry.equals(1) || gangAssignEntry.equals("1")){
                                                Integer gang_assignment_id = gangAssignInsertService.lastGangAssignmentId();
                                                for(int i = 0; i<laborAssignList.size();i++){
                                                    Long labor_id = laborAssignList.get(i).getLabor_id();
                                                    gangAssignInsertService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
                                                }
                                                responseMessage = new ResponseMessage( "Gang Assignment Successful.");
                                                return new ResponseEntity(responseMessage, HttpStatus.OK);
                                            } else {
                                                responseMessage = new ResponseMessage( "Sorry, Could not assign gang.");
                                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                            }
                                        }

                                    } else {
                                        responseMessage = new ResponseMessage("Sorry! Invalid Rotation.");
                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                    }
                                }

                            } else {
                                responseMessage = new ResponseMessage("Sorry, Rotation & BL are blocked by NBR.");
                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                            }
                        }



                        else if (work_category_id == 10){
                            // Delivery from Yard
                            yard = gangAssign.getYard();
                            Integer gang_assignment_id;
                            if(laborAssignList.size() == 0){
                                responseMessage = new ResponseMessage( "No labor in selected gang.");
                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                            } else {
                                gangAssignInsertService.gangAssignInsertForYard(gang_id,work_location_id,shift,yard,start_time,end_time);
                                gang_assignment_id = gangAssignInsertService.lastGangAssignmentId();
                                for(int i = 0; i<laborAssignList.size();i++){
                                    Long labor_id = laborAssignList.get(i).getLabor_id();
                                    gangAssignInsertService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
                                }
                                String container_array[] = gangAssign.getContainer();
                                for(int k=0;k<container_array.length;k++){
                                    String container = container_array[k];
                                    List<Object> blockedContainer = new ArrayList<Object>();
                                    blockedContainer = gangAssignInsertService.containerBlockList(container);
                                    if(blockedContainer.size()==0){


                                                String query = " SELECT COUNT(*) AS rtnValue FROM inv_unit\n" +
                                                        "INNER JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
                                                        "WHERE to_char(inv_unit_fcy_visit.flex_date01,'YYYY-MM-DD')='"+ start_time +"' AND id='"+ container +"''";
                                        Integer rtnValue = cu.getCountValue("SPARCSN4",query);
                                        if(rtnValue==0)
                                        {
                                            responseMessage = new ResponseMessage("Sorry! No assignment found for the container " + container);
                                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                        } else {
                                            Boolean isContainerExists = gangAssignInsertService.isContainerExists(container);
                                            List<GangAssign> getUnitGkeyAndVesselByContainer = gangAssignInsertService.getUnitGkeyAndVesselByContainer(container);
                                            for(int cnt=0;cnt<getUnitGkeyAndVesselByContainer.size();cnt++){
                                                gangAssign=getUnitGkeyAndVesselByContainer.get(cnt);
                                                unit_gkey = gangAssign.getUnit_gkey();
                                                vvd_gkey = gangAssign.getVvd_gkey();
                                                vsl_name = gangAssign.getVsl_name();
                                                System.out.println("vsl_name:"+vsl_name);
                                                System.out.println("vvd_gkey:"+vvd_gkey);
                                                System.out.println("unit_gkey:"+unit_gkey);

                                            }
                                            Integer containerInsert = gangAssignInsertService.gangAssignmentWiseContainerInsert(gang_assignment_id,container,vsl_name,vvd_gkey,unit_gkey);
                                        }
                                    }
                                }
                                responseMessage = new ResponseMessage("Gang Assignment Successful.");
                                return new ResponseEntity(responseMessage, HttpStatus.OK);
                            }
                        }



                        else {
                            responseMessage = new ResponseMessage( "Sorry! Invalid Work Category.");
                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        responseMessage = new ResponseMessage( "Sorry! Invalid Work Location.");
                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Invalid Gang Information.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Invalid Work Category.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
    }




//    @RequestMapping(value = "/AddGangAssignment", method = RequestMethod.POST, consumes="application/json")
//    public ResponseEntity<ResponseMessage> index(@RequestBody GangAssign gangAssign) throws IOException, SQLException {
//        Long gang_id = gangAssign.getGang_id();
//        Long work_location_id = gangAssign.getWork_location_id();
//        Long work_category_id = gangAssign.getWork_category_id();
//        Integer shift = gangAssign.getShift();
//        String crane = gangAssign.getCrane();
//        String shed = gangAssign.getShed();
//        String start_time = gangAssign.getStart_time();
//        Date end_time = gangAssign.getEnd_time();
//
//        String rotation = "";
//
//        String yard = "";
//        String bl = "";
//        String vsl_name = "";
//        String vvd_gkey = null;
//        String unit_gkey = null;
//
//        CommonDataUtils cu = new CommonDataUtils();
//
//        if(gang_id == null || gang_id.equals("") || gang_id.equals(" ")){
//            responseMessage = new ResponseMessage( "Sorry! Gang ID can not be null or empty.");
//            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//        }
//        else if(work_location_id == null || work_location_id.equals("") || work_location_id.equals(" ")){
//            responseMessage = new ResponseMessage( "Sorry! Work Location can not be null or empty.");
//            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//        }
//        else if(work_category_id == null || work_category_id.equals("") || work_category_id.equals(" ")){
//            responseMessage = new ResponseMessage( "Sorry! Work Category can not be null or empty.");
//            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//        }
//        else if(shift == null || shift.equals("") || shift.equals(" ")){
//            responseMessage = new ResponseMessage( "Sorry! Shift can not be null or empty.");
//            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//        }
//        else if(start_time == null || start_time.equals("") || start_time.equals(" ")){
//            responseMessage = new ResponseMessage( "Sorry! Starting time can not be null or empty.");
//            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//        }
//        else if(end_time == null || end_time.equals("") || end_time.equals(" ")){
//            responseMessage = new ResponseMessage( "Sorry! Ending time can not be null or empty.");
//            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//        }
//        else
//        {
//            List<LaborAssignToGang> laborAssignList = new ArrayList<LaborAssignToGang>();
//            laborAssignList = laborAssignmentListService.laborAssignmentListByGangId(gang_id);
//
//            Boolean workCategoryExistenceCnt = gangAssignInsertService.workCategoryExistenceCnt(work_category_id);
//            if(workCategoryExistenceCnt){
//                Boolean gangExistenceCnt = gangAssignInsertService.gangExistenceCnt(gang_id);
//                if(gangExistenceCnt){
//                    Boolean workLocationExistenceCnt = gangAssignInsertService.workLocationExistenceCnt(work_location_id);
//                    if(workLocationExistenceCnt){
//                        if(work_category_id == 2 || work_category_id==4 || work_category_id==12){
//                            // 'Discharging from Vessel Operation' OR 'Loading to Vessel Operation' OR 'Loading and Discharging'
//                            System.out.println("Vessel Discharging Operation OR Vessel Loading Operation");
//                            rotation = gangAssign.getRotation();
//                            Boolean isRotaionExists = gangAssignInsertService.isRotationExists(rotation);
//                            if(isRotaionExists){
//                                List vslNameAndVvvGkey[] = gangAssignInsertService.getVslNameAndVvdGkeyByRotation(rotation);
//                                for(int cnt=0;cnt<vslNameAndVvvGkey.length;cnt++){
//                                    vsl_name = vslNameAndVvvGkey[cnt].get(2).toString();
//                                    vvd_gkey = vslNameAndVvvGkey[cnt].get(1).toString();
//                                }
//                                if(laborAssignList.size() == 0){
//                                    responseMessage = new ResponseMessage( "No labor in selected gang.");
//                                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                                } else {
//                                    Integer gangAssignEntry = gangAssignInsertService.gangAssignInsertForVesselOperation(gang_id,work_location_id,shift,rotation,bl,crane,shed,yard,vsl_name,vvd_gkey,unit_gkey,start_time,end_time);
//                                    if(gangAssignEntry.equals(1) || gangAssignEntry.equals("1")){
//                                        Integer gang_assignment_id = gangAssignInsertService.lastGangAssignmentId();
//                                        for(int i = 0; i<laborAssignList.size();i++){
//                                            Long labor_id = laborAssignList.get(i).getLabor_id();
//                                            gangAssignInsertService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
//                                        }
//                                        responseMessage = new ResponseMessage( "Gang Assignment Successful.");
//                                        return new ResponseEntity(responseMessage, HttpStatus.OK);
//                                    } else {
//                                        responseMessage = new ResponseMessage( "Sorry, Could not assign gang.");
//                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                                    }
//                                }
//                            } else {
//                                responseMessage = new ResponseMessage( "Sorry! Invalid Rotation.");
//                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                            }
//                        }
//                        else if (work_category_id == 6) {
//                            // Unstuffing
//                            Integer gang_assignment_id;
//                            if(laborAssignList.size() == 0){
//                                responseMessage = new ResponseMessage( "No labor in selected gang.");
//                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                            } else {
//                                gangAssignInsertService.gangAssignInsertForUnstuffing(gang_id,work_location_id,shift,shed,start_time,end_time);
//                                gang_assignment_id = gangAssignInsertService.lastGangAssignmentId();
//                                for(int i = 0; i<laborAssignList.size();i++){
//                                    Long labor_id = laborAssignList.get(i).getLabor_id();
//                                    gangAssignInsertService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
//                                }
//                                String container_array[] = gangAssign.getContainer();
//                                for(int k=0;k<container_array.length;k++){
//                                    String container = container_array[k];
//                                    List<Object> blockedContainer = new ArrayList<Object>();
//                                    blockedContainer = gangAssignInsertService.containerBlockList(container);
//                                    if(blockedContainer.size()==0){
//                                        String query = "SELECT COUNT(*) AS rtnValue FROM (\n" +
//                                                "SELECT cont_number FROM lcl_assignment_detail\n" +
//                                                "INNER JOIN igm_detail_container ON igm_detail_container.id=lcl_assignment_detail.igm_cont_detail_id\n" +
//                                                "WHERE lcl_assignment_detail.assignment_date='"+ start_time +"' AND igm_detail_container.cont_number='"+ container +"'\n" +
//                                                "UNION\n" +
//                                                "SELECT cont_number FROM lcl_assignment_detail\n" +
//                                                "INNER JOIN igm_sup_detail_container ON igm_sup_detail_container.id=lcl_assignment_detail.igm_cont_detail_id\n" +
//                                                "WHERE lcl_assignment_detail.assignment_date='"+ start_time +"' AND igm_sup_detail_container.cont_number='"+ container +"'\n" +
//                                                ") AS tbl";
//                                        Integer rtnValue = cu.getCountValue("IGM",query);
//                                        if(rtnValue==0)
//                                        {
//                                            responseMessage = new ResponseMessage("Sorry! No assignment found for the container " + container);
//                                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                                        } else {
//                                            Boolean isContainerExists = gangAssignInsertService.isContainerExists(container);
//                                            List getUnitGkeyAndVesselByContainer[] = gangAssignInsertService.getUnitGkeyAndVesselByContainer(container);
//                                            for(int cnt=0;cnt<getUnitGkeyAndVesselByContainer.length;cnt++){
//                                                unit_gkey = getUnitGkeyAndVesselByContainer[cnt].get(2).toString();
//                                                vvd_gkey = getUnitGkeyAndVesselByContainer[cnt].get(3).toString();
//                                                vsl_name = getUnitGkeyAndVesselByContainer[cnt].get(4).toString();
//                                            }
//                                            Integer containerInsert = gangAssignInsertService.gangAssignmentWiseContainerInsert(gang_assignment_id,container,vsl_name,vvd_gkey,unit_gkey);
//
//                                        }
//                                    }
//                                }
//                                responseMessage = new ResponseMessage("Gang Assignment Successful.");
//                                return new ResponseEntity(responseMessage, HttpStatus.OK);
//                            }
//                        }
//                        else if(work_category_id == 8){
//                            // Delivery from Shed
//                            rotation = gangAssign.getRotation();
//                            bl = gangAssign.getBl();
//
//                            String queryChkNBRBlockUnblodk = "SELECT COUNT(*) AS rtnValue FROM nbr_block_unblock_data \n" +
//                                    "WHERE rotation_no='" + rotation + "' AND bl_ref='" + bl + "' AND release_flag='DO_NOT_RELEASE'";
//                            Integer cntBlocked = cu.getCountValue("IGM", queryChkNBRBlockUnblodk);
//                            if (cntBlocked == 0 || cntBlocked.equals(0) || cntBlocked.equals("0")) {
//                                String query = "SELECT COUNT(*) AS rtnValue FROM oracle_nts_data\n" +
//                                        "WHERE imp_rot_no='" + rotation + "' AND bl_no='" + bl + "'";
//                                Integer rtnValue = cu.getCountValue("IGM", query);
//                                System.out.println("rtn" + rtnValue);
//                                if (rtnValue == 0) {
//                                    responseMessage = new ResponseMessage("Payment is not completed");
//                                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                                } else {
//                                    rotation = gangAssign.getRotation();
//                                    bl = gangAssign.getBl();
//                                    Boolean isRotaionExists = gangAssignInsertService.isRotationExists(rotation);
//                                    if (isRotaionExists) {
//                                        List vslNameAndVvvGkey[] = gangAssignInsertService.getVslNameAndVvdGkeyByRotation(rotation);
//                                        for (int cnt = 0; cnt < vslNameAndVvvGkey.length; cnt++) {
//                                            vsl_name = vslNameAndVvvGkey[cnt].get(2).toString();
//                                            vvd_gkey = vslNameAndVvvGkey[cnt].get(1).toString();
//                                        }
//                                        if (laborAssignList.size() == 0) {
//                                            responseMessage = new ResponseMessage("No labor in selected gang.");
//                                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                                        } else {
//                                            Integer gangAssignEntry = gangAssignInsertService.gangAssignInsertForShed(gang_id,work_location_id,shift,rotation,bl,shed,vsl_name,vvd_gkey,unit_gkey,start_time,end_time);
//                                            if(gangAssignEntry.equals(1) || gangAssignEntry.equals("1")){
//                                                Integer gang_assignment_id = gangAssignInsertService.lastGangAssignmentId();
//                                                for(int i = 0; i<laborAssignList.size();i++){
//                                                    Long labor_id = laborAssignList.get(i).getLabor_id();
//                                                    gangAssignInsertService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
//                                                }
//                                                responseMessage = new ResponseMessage( "Gang Assignment Successful.");
//                                                return new ResponseEntity(responseMessage, HttpStatus.OK);
//                                            } else {
//                                                responseMessage = new ResponseMessage( "Sorry, Could not assign gang.");
//                                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                                            }
//                                        }
//
//                                    } else {
//                                        responseMessage = new ResponseMessage("Sorry! Invalid Rotation.");
//                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                                    }
//                                }
//
//                            } else {
//                                responseMessage = new ResponseMessage("Sorry, Rotation & BL are blocked by NBR.");
//                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                            }
//                        }
//                        else if (work_category_id == 10){
//                            // Delivery from Yard
//                            yard = gangAssign.getYard();
//                            Integer gang_assignment_id;
//                            if(laborAssignList.size() == 0){
//                                responseMessage = new ResponseMessage( "No labor in selected gang.");
//                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                            } else {
//                                gangAssignInsertService.gangAssignInsertForYard(gang_id,work_location_id,shift,yard,start_time,end_time);
//                                gang_assignment_id = gangAssignInsertService.lastGangAssignmentId();
//                                for(int i = 0; i<laborAssignList.size();i++){
//                                    Long labor_id = laborAssignList.get(i).getLabor_id();
//                                    gangAssignInsertService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id,start_time);
//                                }
//                                String container_array[] = gangAssign.getContainer();
//                                for(int k=0;k<container_array.length;k++){
//                                    String container = container_array[k];
//                                    List<Object> blockedContainer = new ArrayList<Object>();
//                                    blockedContainer = gangAssignInsertService.containerBlockList(container);
//                                    if(blockedContainer.size()==0){
//                                        String query = "SELECT COUNT(*) AS rtnValue FROM sparcsn4.inv_unit\n" +
//                                                "INNER JOIN sparcsn4.inv_unit_fcy_visit ON sparcsn4.inv_unit_fcy_visit.unit_gkey=sparcsn4.inv_unit.gkey\n" +
//                                                "WHERE DATE(sparcsn4.inv_unit_fcy_visit.flex_date01)='"+ start_time +"' AND id='"+ container +"'";
//                                        Integer rtnValue = cu.getCountValue("CTMSMIS",query);
//                                        if(rtnValue==0)
//                                        {
//                                            responseMessage = new ResponseMessage("Sorry! No assignment found for the container " + container);
//                                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                                        } else {
//                                            Boolean isContainerExists = gangAssignInsertService.isContainerExists(container);
//                                            List getUnitGkeyAndVesselByContainer[] = gangAssignInsertService.getUnitGkeyAndVesselByContainer(container);
//                                            for(int cnt=0;cnt<getUnitGkeyAndVesselByContainer.length;cnt++){
//                                                unit_gkey = getUnitGkeyAndVesselByContainer[cnt].get(2).toString();
//                                                vvd_gkey = getUnitGkeyAndVesselByContainer[cnt].get(3).toString();
//                                                vsl_name = getUnitGkeyAndVesselByContainer[cnt].get(4).toString();
//                                            }
//                                            Integer containerInsert = gangAssignInsertService.gangAssignmentWiseContainerInsert(gang_assignment_id,container,vsl_name,vvd_gkey,unit_gkey);
//                                        }
//                                    }
//                                }
//                                responseMessage = new ResponseMessage("Gang Assignment Successful.");
//                                return new ResponseEntity(responseMessage, HttpStatus.OK);
//                            }
//                        }
//                        else {
//                            responseMessage = new ResponseMessage( "Sorry! Invalid Work Category.");
//                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                        }
//                    } else {
//                        responseMessage = new ResponseMessage( "Sorry! Invalid Work Location.");
//                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                    }
//                } else {
//                    responseMessage = new ResponseMessage( "Sorry! Invalid Gang Information.");
//                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//                }
//            } else {
//                responseMessage = new ResponseMessage( "Sorry! Invalid Work Category.");
//                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//            }
//        }
//    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody List<GangAssign> list(){ return gangAssignListService.gangAssignmentList(); }

    @RequestMapping(value = "/listByOrg/{berth_operator_id}", method = RequestMethod.GET)
    public @ResponseBody List<GangAssign> gangAssignmentListByOrg(@PathVariable("berth_operator_id") Long berth_operator_id){
        System.out.println("Berth Op : " + berth_operator_id);
        return gangAssignListService.gangAssignmentByOrg(berth_operator_id);
    }

    @RequestMapping(value = "/getGangAssignmentById/{id}", method = RequestMethod.GET)
    public ResponseEntity<GangAssign> getGangAssignmentById(@PathVariable("id") Long id){
        return gangAssignListService.gangAssignmentById(id);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> editGangAssign(@RequestBody GangAssign gangAssign) throws IOException, SQLException {
        Long id = gangAssign.getId();
        Long gang_id = gangAssign.getGang_id();
        Long work_location_id = gangAssign.getWork_location_id();
        Long work_category_id = gangAssign.getWork_category_id();
        Integer shift = gangAssign.getShift();
        String crane = gangAssign.getCrane();
        String shed = gangAssign.getShed();
        String start_time = gangAssign.getStart_time();
        Date end_time = gangAssign.getEnd_time();

        String rotation = "";
        String yard = "";
        String bl = "";
        String vsl_name = "";
        String vvd_gkey = null;
        String unit_gkey = null;

        CommonDataUtils cu = new CommonDataUtils();

        if(gang_id == null || gang_id.equals("") || gang_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Gang ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(work_location_id == null || work_location_id.equals("") || work_location_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Location can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(work_category_id == null || work_category_id.equals("") || work_category_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Category can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(shift == null || shift.equals("") || shift.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Shift can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(start_time == null || start_time.equals("") || start_time.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Starting time can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(end_time == null || end_time.equals("") || end_time.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Ending time can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else {
            {
                List<LaborAssignToGang> laborAssignList = new ArrayList<LaborAssignToGang>();
                laborAssignList = laborAssignmentListService.laborAssignmentListByGangId(gang_id);
                Boolean isGangAssignmentExists = gangAssignEditService.isGangAssignmentExists(id);
                if(isGangAssignmentExists) {
                    Boolean workCategoryExistenceCnt = gangAssignInsertService.workCategoryExistenceCnt(work_category_id);
                    if(workCategoryExistenceCnt){
                        Boolean gangExistenceCnt = gangAssignInsertService.gangExistenceCnt(gang_id);
                        if(gangExistenceCnt){
                            Boolean workLocationExistenceCnt = gangAssignInsertService.workLocationExistenceCnt(work_location_id);
                            if(workLocationExistenceCnt){
                                if(work_category_id == 2 || work_category_id==4 || work_category_id==12){
                                    if(laborAssignList.size() == 0){
                                        responseMessage = new ResponseMessage( "No labor in selected gang.");
                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                    } else {
                                        rotation = gangAssign.getRotation();
                                        Boolean isRotaionExists = gangAssignInsertService.isRotationExists(rotation);
                                        if(isRotaionExists){
                                            List<GangAssign> vslNameAndVvvGkey = gangAssignInsertService.getVslNameAndVvdGkeyByRotation(rotation);
                                            for(int cnt=0;cnt<vslNameAndVvvGkey.size();cnt++){
                                                gangAssign=vslNameAndVvvGkey.get(cnt);
                                                vsl_name = gangAssign.getVsl_name();
                                                vvd_gkey = gangAssign.getVvd_gkey();



                                            }
                                            Integer gangAssignUpdate = gangAssignEditService.gangAssignmentUpdateforVesselOperation(id,gang_id,work_location_id,shift,rotation,crane,vsl_name,vvd_gkey,start_time,end_time);

                                            if(gangAssignUpdate.equals(1) || gangAssignUpdate.equals("1")){
                                                System.out.println("Gang Assignment ID......... " + id);
                                                gangAssignEditService.deleteLaborByGangAssignment(id);
                                                for(int i = 0; i<laborAssignList.size();i++){
                                                    Long labor_id = laborAssignList.get(i).getLabor_id();
                                                    gangAssignEditService.gangAssignWiseLaborInsert(id,gang_id,labor_id,start_time,end_time);
                                                }
                                                responseMessage = new ResponseMessage( "Gang Assignment Update Successful.");
                                                return new ResponseEntity(responseMessage, HttpStatus.OK);
                                            } else {
                                                responseMessage = new ResponseMessage( "Sorry, Could not update gang assignment.");
                                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                            }
                                        } else {
                                            responseMessage = new ResponseMessage( "Sorry! Invalid Rotation.");
                                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                        }
                                    }
                                } else if(work_category_id == 6){
                                    // 'Unstuffing'
                                    if(laborAssignList.size() == 0){
                                        responseMessage = new ResponseMessage( "No labor in selected gang.");
                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                    } else {
                                        gangAssignEditService.gangAssignmentEditforUnstuffing(id,gang_id,work_location_id,shift,shed,start_time,end_time);
                                        gangAssignEditService.deleteLaborByGangAssignment(id);
                                        for(int i = 0; i<laborAssignList.size();i++){
                                            Long labor_id = laborAssignList.get(i).getLabor_id();
                                            gangAssignEditService.gangAssignWiseLaborInsert(id,gang_id,labor_id,start_time,end_time);
                                        }
                                        gangAssignEditService.deleteContainerByGangAssignment(id);

                                        String container_array[] = gangAssign.getContainer();
                                        for(int k=0;k<container_array.length;k++){
                                            String container = container_array[k];
                                            List<Object> blockedContainer = new ArrayList<Object>();
                                            blockedContainer = gangAssignInsertService.containerBlockList(container);
                                            if(blockedContainer.size()==0){
                                                String query = "SELECT COUNT(*) AS rtnValue FROM (\n" +
                                                        "SELECT cont_number FROM lcl_assignment_detail\n" +
                                                        "INNER JOIN igm_detail_container ON igm_detail_container.id=lcl_assignment_detail.igm_cont_detail_id\n" +
                                                        "WHERE lcl_assignment_detail.assignment_date='"+ start_time +"' AND igm_detail_container.cont_number='"+ container +"'\n" +
                                                        "UNION\n" +
                                                        "SELECT cont_number FROM lcl_assignment_detail\n" +
                                                        "INNER JOIN igm_sup_detail_container ON igm_sup_detail_container.id=lcl_assignment_detail.igm_cont_detail_id\n" +
                                                        "WHERE lcl_assignment_detail.assignment_date='"+ start_time +"' AND igm_sup_detail_container.cont_number='"+ container +"'\n" +
                                                        ") AS tbl";
                                                Integer rtnValue = cu.getCountValue("IGM",query);
                                                if(rtnValue==0)
                                                {
                                                    responseMessage = new ResponseMessage("Sorry! No assignment found for the container " + container);
                                                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                                } else {
                                                    Boolean isContainerExists = gangAssignInsertService.isContainerExists(container);

                                                    List<GangAssign> getUnitGkeyAndVesselByContainer = gangAssignInsertService.getUnitGkeyAndVesselByContainer(container);
                                                    for(int cnt=0;cnt<getUnitGkeyAndVesselByContainer.size();cnt++){
                                                        gangAssign=getUnitGkeyAndVesselByContainer.get(cnt);
                                                        unit_gkey = gangAssign.getUnit_gkey();
                                                        vvd_gkey = gangAssign.getVvd_gkey();
                                                        vsl_name = gangAssign.getVsl_name();


                                                    }
                                                    Integer containerInsert = gangAssignEditService.gangAssignmentWiseContainerInsert(id,container,vsl_name,vvd_gkey,unit_gkey);
                                                }
                                            }
                                        }
                                        responseMessage = new ResponseMessage("Gang Assignment Update Successful.");
                                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                                    }

                                }

                                else if(work_category_id == 8){
                                    // 'Delivery to Shed'
                                    System.out.println("Delivery to Shed");
                                    rotation = gangAssign.getRotation();
                                    bl = gangAssign.getBl();

                                    String queryChkNBRBlockUnblodk = "SELECT COUNT(*) AS rtnValue FROM nbr_block_unblock_data \n" +
                                            "WHERE rotation_no='" + rotation + "' AND bl_ref='" + bl + "' AND release_flag='DO_NOT_RELEASE'";
                                    Integer cntBlocked = cu.getCountValue("IGM", queryChkNBRBlockUnblodk);
                                    if (cntBlocked == 0 || cntBlocked.equals(0) || cntBlocked.equals("0")) {
                                        String query = "SELECT COUNT(*) AS rtnValue FROM oracle_nts_data\n" +
                                                "WHERE imp_rot_no='" + rotation + "' AND bl_no='" + bl + "'";
                                        Integer rtnValue = cu.getCountValue("IGM", query);
                                        System.out.println("rtn" + rtnValue);
                                        if (rtnValue == 0) {
                                            responseMessage = new ResponseMessage("Payment is not completed");
                                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                        } else {
                                            Boolean isRotaionExists = gangAssignInsertService.isRotationExists(rotation);
                                            if(isRotaionExists){
                                                if (laborAssignList.size() == 0) {
                                                    responseMessage = new ResponseMessage("No labor in selected gang.");
                                                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                                } else {
                                                    List<GangAssign> vslNameAndVvvGkey = gangAssignInsertService.getVslNameAndVvdGkeyByRotation(rotation);
                                                    for(int cnt=0;cnt<vslNameAndVvvGkey.size();cnt++){
                                                        gangAssign=vslNameAndVvvGkey.get(cnt);
                                                        vsl_name = gangAssign.getVsl_name();
                                                        vvd_gkey = gangAssign.getVvd_gkey();



                                                    }

                                                    Integer gangAssignUpdate = gangAssignEditService.gangAssignmentEditforShed(id,gang_id,work_location_id,shift,rotation,bl,shed,vsl_name,vvd_gkey,unit_gkey,start_time,end_time);
                                                    if (gangAssignUpdate.equals(1) || gangAssignUpdate.equals("1")) {
                                                        gangAssignEditService.deleteLaborByGangAssignment(id);
                                                        for(int i = 0; i<laborAssignList.size();i++){
                                                            Long labor_id = laborAssignList.get(i).getLabor_id();
                                                            gangAssignEditService.gangAssignWiseLaborInsert(id,gang_id,labor_id,start_time,end_time);
                                                        }
                                                        responseMessage = new ResponseMessage( "Gang Assignment Update Successful.");
                                                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                                                    } else {
                                                        responseMessage = new ResponseMessage("Sorry, Could not update gang assignment.");
                                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                                    }
                                                }
                                            } else {
                                                responseMessage = new ResponseMessage( "Sorry! Invalid Rotation.");
                                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                            }
                                        }
                                    } else {
                                        responseMessage = new ResponseMessage("Sorry, Rotation & BL are blocked by NBR.");
                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                    }

                                }

                                else if(work_category_id == 10){
                                    // 'Delivery from Yard'
                                    yard = gangAssign.getYard();
                                    if(laborAssignList.size() == 0){
                                        responseMessage = new ResponseMessage( "No labor in selected gang.");
                                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                    } else {
                                        gangAssignEditService.gangAssignmentEditforYard(id,gang_id,work_location_id,shift,yard,start_time,end_time);
                                        gangAssignEditService.deleteLaborByGangAssignment(id);
                                        for(int i = 0; i<laborAssignList.size();i++){
                                            Long labor_id = laborAssignList.get(i).getLabor_id();
                                            gangAssignEditService.gangAssignWiseLaborInsert(id,gang_id,labor_id,start_time,end_time);
                                        }
                                        gangAssignEditService.deleteContainerByGangAssignment(id);

                                        String container_array[] = gangAssign.getContainer();
                                        for(int k=0;k<container_array.length;k++){
                                            String container = container_array[k];
                                            List<Object> blockedContainer = new ArrayList<Object>();
                                            blockedContainer = gangAssignInsertService.containerBlockList(container);
                                            if(blockedContainer.size()==0){
//                                                String query = "SELECT COUNT(*) AS rtnValue FROM sparcsn4.inv_unit\n" +
//                                                        "INNER JOIN sparcsn4.inv_unit_fcy_visit ON sparcsn4.inv_unit_fcy_visit.unit_gkey=sparcsn4.inv_unit.gkey\n" +
//                                                        "WHERE DATE(sparcsn4.inv_unit_fcy_visit.flex_date01)='"+ start_time +"' AND id='"+ container +"'";
//                                                Integer rtnValue = cu.getCountValue("CTMSMIS",query);

                                                String query = " SELECT COUNT(*) AS rtnValue FROM inv_unit\n" +
                                                        "INNER JOIN inv_unit_fcy_visit ON inv_unit_fcy_visit.unit_gkey=inv_unit.gkey\n" +
                                                        "WHERE to_char(inv_unit_fcy_visit.flex_date01,'YYYY-MM-DD')='"+ start_time +"' AND id='"+ container +"''";
                                                Integer rtnValue = cu.getCountValue("SPARCSN4",query);

                                                if(rtnValue==0)
                                                {
                                                    responseMessage = new ResponseMessage("Sorry! No assignment found for the container " + container);
                                                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                                } else {
                                                    Boolean isContainerExists = gangAssignInsertService.isContainerExists(container);
                                                    List<GangAssign> getUnitGkeyAndVesselByContainer = gangAssignInsertService.getUnitGkeyAndVesselByContainer(container);
                                                    for(int cnt=0;cnt<getUnitGkeyAndVesselByContainer.size();cnt++){
                                                        gangAssign=getUnitGkeyAndVesselByContainer.get(cnt);
                                                        unit_gkey = gangAssign.getUnit_gkey();
                                                        vvd_gkey = gangAssign.getVvd_gkey();
                                                        vsl_name = gangAssign.getVsl_name();
                                                    }
                                                    Integer containerInsert = gangAssignEditService.gangAssignmentWiseContainerInsert(id,container,vsl_name,vvd_gkey,unit_gkey);
                                                }
                                            }
                                        }
                                        responseMessage = new ResponseMessage("Gang Assignment Successful.");
                                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                                    }

                                } else {
                                    responseMessage = new ResponseMessage( "Please Check Work Category, Could not Save Data.");
                                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                                }
                            } else {
                                responseMessage = new ResponseMessage( "Sorry! Invalid Work Location.");
                                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                            }
                        } else {
                            responseMessage = new ResponseMessage( "Sorry! Invalid Gang Information.");
                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        responseMessage = new ResponseMessage( "Sorry! Invalid Work Category.");
                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Invalid Gang Assignment Information.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            }
        }
    }




}
