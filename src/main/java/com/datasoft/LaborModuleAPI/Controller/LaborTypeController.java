package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Model.LaborType;
import com.datasoft.LaborModuleAPI.Service.LaborTypeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/laborType")
public class LaborTypeController {
    @Autowired
    private LaborTypeService laborTypeService;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody List<LaborType> index(){
        List<LaborType> typeList = new ArrayList<LaborType>();
        String exception = null;
        try
        {
            typeList = laborTypeService.listLaborType();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return typeList ;
    }


    @RequestMapping(value = "/getLaborTypeById/{id}", method = RequestMethod.GET)
    public ResponseEntity<LaborType> getLaborTypeById(@PathVariable("id") Long id){
        LaborType laborType = laborTypeService.getLaborTypeById(id);
        return new ResponseEntity<>(laborType,HttpStatus.OK);
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> addLaborType(@RequestBody LaborType laborType) throws IOException{

        if(laborType.getName() == null || laborType.getName().equals("") || laborType.getName().equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Type Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(laborType.getName().length()>50){
            responseMessage = new ResponseMessage( "Sorry! Maximum Type Name length is 30.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isExists = laborTypeService.isExists(laborType.getName());
            if(!isExists){
                Boolean responseStatus = laborTypeService.addLaborType(laborType);
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Labor Type Added Successfully");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Add Data");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Labor Type Info already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/editType", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> updateType(@RequestBody LaborType laborType) throws IOException{
        Long id = laborType.getId();
        String name = laborType.getName();
        String updated_by = laborType.getUpdated_by();

        if(id == null || id.equals("") || id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name == null || name.equals("") || name.equals(" ") || name.isEmpty()){
            responseMessage = new ResponseMessage( "Type Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name.length()>50){
            responseMessage = new ResponseMessage( "Sorry! Maximum Type Name length is 30.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isUnique = laborTypeService.isUnique(id,name);
            if(!isUnique){
                Integer update_type = laborTypeService.updateLaborType(id,name);
                if(update_type==1){
                    responseMessage = new ResponseMessage( "Labor Type Updated Successfully");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Update Data");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Labor Type Info already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
    }

//    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
//    public JsonResponse deleteAllType(){
//        Integer delete_all_Type = laborTypeService.deleteAllType();
//        if(delete_all_Type >= 1){
//            responseMessage = new JsonResponse(412, "Labor Type Deleted Successfully");
//        } else {
//            responseMessage = new JsonResponse(412, "Sorry! Could not Delete Data");
//        }
//        return responseMessage;
//    }

    //@DeleteMapping("/deleteType/{id}")
    @RequestMapping(value = "/deleteType/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteType(@PathVariable Long id) throws IOException{
        Integer chkLaborDtls = laborTypeService.chkLaborDtlsInfo(id);
        Integer chkDesignation = laborTypeService.chkDesignation(id);
        if(chkDesignation == 0){
            if(chkLaborDtls == 0){
                Integer delete_Type = laborTypeService.deleteTypeById(id);
                if(delete_Type == 1){
                    responseMessage = new ResponseMessage( "Labor Type Deleted Successfully.");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Delete Data.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! This labor Type exists in labor details info.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMessage = new ResponseMessage( "Sorry! This labor type exists in designation category info.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }

    }

//    @RequestMapping(value = "/deleteType", method = RequestMethod.DELETE, consumes="application/json")
//    public JsonResponse deleteType(@RequestBody LaborType labor_type){
//        Long id = labor_type.getId();
//        if(id == null || id.equals("") || id.equals(" ")){
//            return responseMessage = new JsonResponse(412, "ID can not be null or empty.");
//        } else {
//            Integer chkLaborDtls = laborTypeService.chkLaborDtlsInfo(id);
//            if(chkLaborDtls == 0){
//                Integer delete_Type = laborTypeService.deleteTypeById(id);
//                if(delete_Type == 1){
//                    responseMessage = new JsonResponse(412, "Labor Type Deleted Successfully");
//                } else {
//                    responseMessage = new JsonResponse(412, "Sorry! Could not Delete Data");
//                }
//            } else {
//                responseMessage = new JsonResponse(412, "Sorry! This labor Type exists in labor details info.");
//            }
//        }
//        return responseMessage;
//    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ResponseMessage> exceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! Could not Delete Data.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
