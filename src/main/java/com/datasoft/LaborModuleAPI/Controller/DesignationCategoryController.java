package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Service.DesignationCategoryServices.DesignationCategoryDeleteService;
import com.datasoft.LaborModuleAPI.Service.DesignationCategoryServices.DesignationCategoryEditService;
import com.datasoft.LaborModuleAPI.Service.DesignationCategoryServices.DesignationCategoryInsertService;
import com.datasoft.LaborModuleAPI.Service.DesignationCategoryServices.DesignationCategoryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/DesignationCategory")
public class DesignationCategoryController {
    @Autowired
    private DesignationCategoryInsertService desigCatInsertService;

    @Autowired
    private DesignationCategoryListService desig_cat_list_service;

    @Autowired
    private DesignationCategoryEditService desig_cat_edit_service;

    @Autowired
    private DesignationCategoryDeleteService desigCatDltService;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/addDesignationCategory", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> addNewCategory(@RequestBody DesignationCategory designationCategory) throws IOException {
        if(designationCategory.getName() == null || designationCategory.getName().equals("") || designationCategory.getName().equals(" ")){
            responseMessage = new ResponseMessage( "Designation Category Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(designationCategory.getName().length()>50){
            responseMessage = new ResponseMessage( "Sorry! Maximum Designation Category Name length is 30.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
//        else if(designationCategory.getLabor_type_id() == null || designationCategory.getLabor_type_id().equals("") || designationCategory.getLabor_type_id().equals(" ")){
//            responseMessage = new ResponseMessage( "Labor Type ID can not be null or empty.");
//            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//        }
        else{
            Boolean isLaborTypeExists = desigCatInsertService.isLaborTypeExists(designationCategory.getLabor_type_id());
            if(isLaborTypeExists){
                Boolean isExists = desigCatInsertService.isExists(designationCategory.getName(),designationCategory.getLabor_type_id());
                if(!isExists){
                    Boolean responseStatus = desigCatInsertService.DesignationCategoryAdd(designationCategory);
                    if(responseStatus){
                        responseMessage = new ResponseMessage( "Designation Category Added Successfully.");
                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                    } else {
                        responseMessage = new ResponseMessage( "Sorry! Could not Add Data.");
                        return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                    }
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Designation Category Info already exists.");
                    return new ResponseEntity(responseMessage, HttpStatus.ALREADY_REPORTED);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Invalid Labor Type ID.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<DesignationCategory> index(){
        List<DesignationCategory> category_list = new ArrayList<DesignationCategory>();
        String exception = null;
        try
        {
            category_list = desig_cat_list_service.listDesignationCategory();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return category_list ;
    }

    @RequestMapping(value = "/designationCategoryListByLaborTypeId/{laborTypeId}", method = RequestMethod.GET)
    public @ResponseBody
    List<DesignationCategory> designationCategoryListByLaborTypeId(@PathVariable("laborTypeId") Long laborTypeId){
        List<DesignationCategory> category_list = new ArrayList<DesignationCategory>();
        String exception = null;
        try
        {
            category_list = desig_cat_list_service.designationCategoryListByLaborTypeId(laborTypeId);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return category_list ;
    }

    @RequestMapping(value = "/laborTypeByDesignationId/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<DesignationCategory> laborTypeByDesignationId(@PathVariable("id") Long id){
        List<DesignationCategory> laborType = new ArrayList<DesignationCategory>();
        String exception = null;
        try
        {
            laborType = desig_cat_list_service.laborTypeByDesignationId(id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return laborType ;
    }

    @RequestMapping(value = "/desigCategoryById/{id}", method = RequestMethod.GET)
    public ResponseEntity<DesignationCategory> getDesigCategoryById(@PathVariable("id") Long id){
        DesignationCategory designationCategory = desig_cat_list_service.getDesigCategoryById(id);
        return new ResponseEntity<>(designationCategory,HttpStatus.OK);
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> updateCategory(@RequestBody DesignationCategory designation_category) throws IOException{
        Long id = designation_category.getId();
        Long labor_type_id = designation_category.getLabor_type_id();
        String name = designation_category.getName();

        if(id == null || id.equals("") || id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name.length()>30){
            responseMessage = new ResponseMessage( "Sorry! Maximum Designation Category Name length is 30.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
//        else if(labor_type_id == null || labor_type_id.equals("") || labor_type_id.equals(" ")){
//            responseMessage = new ResponseMessage( "Sorry! Labor Type ID can not be null or empty.");
//            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//        }
        else if(name == null || name.equals("") || name.equals(" ") || name.isEmpty()){
            responseMessage = new ResponseMessage( "Category Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isLaborTypeExists = desigCatInsertService.isLaborTypeExists(labor_type_id);
            if(isLaborTypeExists){
                Boolean isExists = desig_cat_edit_service.chkCategoryforEdit(id,labor_type_id,name);
                if(!isExists){
                    Boolean responseStatus = desig_cat_edit_service.updateDesignationCategory(id,labor_type_id,name);
                    if(responseStatus){
                        responseMessage = new ResponseMessage( "Designation Category Updated Successfully.");
                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                    } else {
                        responseMessage = new ResponseMessage( "Sorry! Could not Update Data.");
                        return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                    }
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Designation Category Info already exists.");
                    return new ResponseEntity(responseMessage, HttpStatus.ALREADY_REPORTED);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Invalid Labor Type ID.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }

        }
    }

//    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
//    public JsonResponse deleteAllCategory(){
//        Integer delete_all_category = desigCatDltService.deleteAllCategory();
//        if(delete_all_category >= 1){
//            jsonMsg = new JsonResponse(412, "Designation Category was Deleted Successfully");
//        } else {
//            jsonMsg = new JsonResponse(412, "Sorry! Could not Delete Data");
//        }
//        return jsonMsg;
//    }

    @DeleteMapping("/deleteDesigCategory/{id}")
    public ResponseEntity<ResponseMessage> deleteCategory(@PathVariable Long id) throws IOException {
        Integer chkLaborDtls = desigCatDltService.chkLaborInfo(id);
        if(chkLaborDtls == 0){
            Integer responseStatus = desigCatDltService.deleteCategoryById(id);
            if(responseStatus == 1){
                responseMessage = new ResponseMessage( "Designation Category was Deleted Successfully.");
                return new ResponseEntity(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = new ResponseMessage( "Sorry! Could not Delete Data.");
                return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            responseMessage = new ResponseMessage( "Sorry! This labor category exists in labor details info.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ResponseMessage> numberFormatExceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! ID must be a number.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
