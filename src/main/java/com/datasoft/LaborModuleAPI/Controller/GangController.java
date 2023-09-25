package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.BerthOperator;
import com.datasoft.LaborModuleAPI.Model.Gang;
import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Service.BerthOperatorListService;
import com.datasoft.LaborModuleAPI.Service.GangServices.GangDeleteService;
import com.datasoft.LaborModuleAPI.Service.GangServices.GangEditService;
import com.datasoft.LaborModuleAPI.Service.GangServices.GangInsertService;
import com.datasoft.LaborModuleAPI.Service.GangServices.GangListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/gang")
public class GangController{
    @Autowired
    private GangInsertService gangService;

    @Autowired
    private GangListService gangListService;

    @Autowired
    private GangEditService gangEditService;

    @Autowired
    private GangDeleteService gang_dlt_service;

    @Autowired
    private BerthOperatorListService berthOperatorListService;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> index(@RequestBody Gang gang) throws IOException {
        if(gang.getName().length()>30){
            responseMessage = new ResponseMessage( "Sorry! Maximum Gang Name length is 30.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(gang.getName() == null || gang.getName().equals("") || gang.getName().equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Gang Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(gang.getWork_category_id() == null || gang.getWork_category_id().equals("") || gang.getWork_category_id().equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Category can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Integer gangExistenceCnt = gangService.isExist(gang.getName());
            if(gangExistenceCnt == 0){
                Integer isWorkCategoryExist = gangService.isWorkCategoryExist(gang.getWork_category_id());
                if(isWorkCategoryExist == 1){
                    Integer responseStatus = gangService.gangInsertQuery(gang.getName(),gang.getWork_category_id(),gang.getBerth_operator_id());
                    if(responseStatus==1){
                        responseMessage = new ResponseMessage( "Gang Information has been Added Successfully.");
                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                    } else {
                        responseMessage = new ResponseMessage( "Sorry! Could not Add Gang Information.");
                        return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                    }
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Invalid Work Category.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Gang Information already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.ALREADY_REPORTED);
            }
        }
    }

    @RequestMapping(value = "/berthOperatorList", method = RequestMethod.GET)
    public @ResponseBody Object berthOperatorList(){
        List<Object> berthOperatorList = new ArrayList<Object>();
        String exception = null;
        try {
            berthOperatorList = berthOperatorListService.berthOperatorList();
            return berthOperatorList;
        }catch(Exception ex) {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return berthOperatorList;
    }

    @RequestMapping(value = "/berthOperatorListByOrgType/{user_role_id}/{org_id}", method = RequestMethod.GET)
    public @ResponseBody Object berthOperatorListByOrgType(@PathVariable Integer user_role_id,@PathVariable Integer org_id){
        List<Object> berthOperatorList = new ArrayList<Object>();
        String exception = null;
        try {
            berthOperatorList = berthOperatorListService.berthOperatorListByOrgType(user_role_id,org_id);
            return berthOperatorList;
        }catch(Exception ex) {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return berthOperatorList;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody List<Gang> list(){
        List<Gang> gang_list = new ArrayList<Gang>();
        String exception = null;
        try
        {
            gang_list = gangListService.gangList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return gang_list ;
    }

    @RequestMapping(value = "/listByOrgType/{user_role_id}/{org_id}", method = RequestMethod.GET)
    public @ResponseBody List<Gang> listByOrgType(@PathVariable("user_role_id") Integer user_role_id,@PathVariable("org_id") Integer org_id){
        List<Gang> gang_list = new ArrayList<Gang>();
        String exception = null;
        try
        {
            gang_list = gangListService.gangListByOrgType(user_role_id,org_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return gang_list ;
    }

    @RequestMapping(value = "/listByOrg/{berth_operator_id}", method = RequestMethod.GET)
    public @ResponseBody List<Gang> listByOrg(@PathVariable("berth_operator_id") Long berth_operator_id){
        List<Gang> gang_list_by_org = new ArrayList<Gang>();
        String exception = null;
        try
        {
            gang_list_by_org = gangListService.gangListByOrg(berth_operator_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return gang_list_by_org ;
    }

    @RequestMapping(value = "/listByOrgAndWorkCategory/{berth_operator_id}/{work_category_id}", method = RequestMethod.GET)
    public @ResponseBody List<Gang> listByOrg(@PathVariable("berth_operator_id") Long berth_operator_id,@PathVariable("work_category_id") Long work_category_id){
        List<Gang> gang_list_by_org_and_work_category = new ArrayList<Gang>();
        String exception = null;
        try
        {
            gang_list_by_org_and_work_category = gangListService.gangListByOrgAndWorkCategory(berth_operator_id,work_category_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return gang_list_by_org_and_work_category ;
    }

    @RequestMapping(value = "/getGangById/{id}", method = RequestMethod.GET)
    public ResponseEntity<Gang> getById(@PathVariable("id") Long id){
        Gang gangById = gangListService.gangById(id);
        return new ResponseEntity<>(gangById,HttpStatus.OK);
    }



    @RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> edit(@RequestBody Gang gang) throws IOException{
        Long id = gang.getId();
        String name = gang.getName();
        Long berth_operator_id = gang.getBerth_operator_id();
        Long work_category_id = gang.getWork_category_id();

        if(id == null || id.equals("") || id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name == null || name.equals("") || name.equals(" ") || name.isEmpty()){
            responseMessage = new ResponseMessage( "Gang Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name.length()>30){
            responseMessage = new ResponseMessage( "Sorry! Maximum Gang Name length is 30.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(berth_operator_id == null || berth_operator_id.equals("") || berth_operator_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Berth Operator ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(work_category_id == null || work_category_id.equals("") || work_category_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Category can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isExist = gangEditService.isExist(id,name);
            if(!isExist){
                Integer isWorkCategoryExist = gangService.isWorkCategoryExist(gang.getWork_category_id());
                if(isWorkCategoryExist == 1){
                    Boolean responseStatus = gangEditService.gangEdit(id,name,work_category_id,berth_operator_id);
                    if(responseStatus){
                        responseMessage = new ResponseMessage( "Gang Information Updated Successfully");
                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                    } else {
                        responseMessage = new ResponseMessage( "Sorry! Could not Update Gang Information");
                        return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                    }
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Invalid Work Category");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Gang Informarion already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.ALREADY_REPORTED);
            }
        }
    }

//    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
//    public JsonResponse deleteAll(){
//        Integer delete_all_gang = gang_dlt_service.deleteAllGang();
//        if(delete_all_gang >= 1){
//            responseMessage = new JsonResponse(204, "Gang Information was Deleted Successfully.");
//        } else {
//            responseMessage = new JsonResponse(501, "Sorry! Could not Delete Gang Information.");
//        }
//        return responseMessage;
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long id) throws IOException{
        Integer cntLaborAssignToGangCnt = gang_dlt_service.chkLaborAssignToGangCnt(id);
        Integer cntGangAssignToWorkplace = gang_dlt_service.chkGangAssignToWorkplace(id);
        if(cntLaborAssignToGangCnt > 0){
            responseMessage = new ResponseMessage( "Sorry! Gang information can not be deleted because labors have been assigned to this gang.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        } else if(cntGangAssignToWorkplace > 0){
            responseMessage = new ResponseMessage( "Sorry! Gang information can not be deleted because this gang has been assigned to some workplaces.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        } else {
            Integer delete_gang = gang_dlt_service.deleteGangById(id);
            if(delete_gang == 1){
                responseMessage = new ResponseMessage( "Gang Information Deleted Successfully");
                return new ResponseEntity(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = new ResponseMessage( "Sorry! Could not Delete Gang Information");
                return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
            }
        }
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ResponseMessage> numberFormatExceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! ID must be a number.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
