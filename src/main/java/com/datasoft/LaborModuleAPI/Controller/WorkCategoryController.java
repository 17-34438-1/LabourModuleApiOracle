package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Model.WorkCategory;
import com.datasoft.LaborModuleAPI.Service.WorkCategoryServices.WorkCategoryDeleteService;
import com.datasoft.LaborModuleAPI.Service.WorkCategoryServices.WorkCategoryEditService;
import com.datasoft.LaborModuleAPI.Service.WorkCategoryServices.WorkCategoryInsertService;
import com.datasoft.LaborModuleAPI.Service.WorkCategoryServices.WorkCategoryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/WorkCategory")
public class WorkCategoryController {
    @Autowired
    private WorkCategoryInsertService workCatInsertService;

    @Autowired
    private WorkCategoryListService workCatListService;

    @Autowired
    private WorkCategoryEditService workCatEditService;

    @Autowired
    private WorkCategoryDeleteService workCatDltService;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/addWorkCategory", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> index(@RequestBody WorkCategory workCategory) throws IOException {
        if(workCategory.getName() == null || workCategory.getName().equals("") || workCategory.getName().equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Category Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(workCategory.getName().length()>30){
            responseMessage = new ResponseMessage( "Sorry! Maximum Work Category Name length is 30.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isExist = workCatInsertService.isExist(workCategory.getName());
            if(!isExist){
                Boolean responseStatus = workCatInsertService.workCategoryInsert(workCategory.getName());
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Added Work Category Successfully");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Add Work Category.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Work Category Info already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<WorkCategory> workCategoryList(){
        List<WorkCategory> workCatList = new ArrayList<WorkCategory>();
        String exception = null;
        try
        {
            workCatList = workCatListService.workCategoryList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return workCatList ;
    }

    @RequestMapping(value = "/workCategoryById/{id}", method = RequestMethod.GET)
    public ResponseEntity<WorkCategory> workCategoryById(@PathVariable("id") Long id){
        WorkCategory workCategory = workCatListService.workCategoryListById(id);
        return new ResponseEntity<>(workCategory,HttpStatus.OK);
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> editCategory(@RequestBody WorkCategory workCategory) throws IOException{
        Long id = workCategory.getId();
        String name = workCategory.getName();

        if(id == null || id.equals("") || id.equals(" ")){
            return new ResponseEntity("ID can not be null or empty.", HttpStatus.BAD_REQUEST);
        }
        else if(name == null || name.equals("") || name.equals(" ") || name.isEmpty()){
            return new ResponseEntity("Category Name can not be null or empty.", HttpStatus.BAD_REQUEST);
        }
        else if(name.length()>30){
            return new ResponseEntity("Sorry! Maximum Work Category Name length is 30.", HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isUnique = workCatEditService.isUnique(id,name);
            if(!isUnique){
                Boolean responseStatus = workCatEditService.WorkCategoryUpdate(id,name);
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Updated Work Category Successfully.");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Update Data.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Work Category Info already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
    }

//    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
//    public JsonResponse deleteAll(){
//        Integer delete_all_category = workCatDltService.deleteAllWorkCategory();
//        if(delete_all_category >= 1){
//            responseMessage = new JsonResponse(200, "Deleted Work Category Successfully");
//        } else {
//            responseMessage = new JsonResponse(402, "Sorry! Could not Delete Work Category");
//        }
//        return responseMessage;
//    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<ResponseMessage> deleteCategory(@PathVariable Long id) throws IOException{
        Boolean cntWorkLocation = workCatDltService.chkWorkLocation(id);
        if(!cntWorkLocation){
            Boolean isWorkCategoryExistInGang = workCatDltService.isWorkCategoryExistInGang(id);
            if(!isWorkCategoryExistInGang){
                Boolean responseStatus = workCatDltService.deleteWorkCategoryById(id);
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Deleted Work Category Successfully.");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Delete Work Category.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! This Work category has already been assigned to some gangs.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMessage = new ResponseMessage( "Sorry! This Work category has already been assigned to some locations.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ResponseMessage> numberFormatExceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! ID must be a number.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }

}
