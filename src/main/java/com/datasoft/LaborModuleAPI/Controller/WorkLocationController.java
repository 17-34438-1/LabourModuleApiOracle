package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Model.WorkLocation;
import com.datasoft.LaborModuleAPI.Service.WorkLocationServices.WorkLocationDeleteService;
import com.datasoft.LaborModuleAPI.Service.WorkLocationServices.WorkLocationEditService;
import com.datasoft.LaborModuleAPI.Service.WorkLocationServices.WorkLocationInsertService;
import com.datasoft.LaborModuleAPI.Service.WorkLocationServices.WorkLocationListService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/WorkLocation")
public class WorkLocationController {
    @Autowired
    private WorkLocationInsertService workLocInsertService;

    @Autowired
    private WorkLocationListService workLocListService;

    @Autowired
    private WorkLocationEditService workLocEditService;

    @Autowired
    private WorkLocationDeleteService workLocDeleteService;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/addWorkLocation", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> index(@RequestBody WorkLocation workLocation) throws IOException {
        if(workLocation.getName() == null || workLocation.getName().equals("") || workLocation.getName().equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Location Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(workLocation.getName().length()>50){
            responseMessage = new ResponseMessage( "Sorry! Maximum Work Location Name length is 50.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(workLocation.getWork_category_id() == null || workLocation.getWork_category_id().equals("") || workLocation.getWork_category_id().equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Location Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isExists = workLocInsertService.isExists(workLocation.getName());
            if(!isExists){
                Boolean responseStatus = workLocInsertService.workLocationInsert(workLocation.getName(),workLocation.getWork_category_id());
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Added Work Location Successfully.");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Add Work Location.");
                    return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Work Location Info already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.ALREADY_REPORTED);
            }
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<WorkLocation> workLocationList(){
        List<WorkLocation> location_list = new ArrayList<WorkLocation>();
        String exception = null;
        try
        {
            location_list = workLocListService.workLocationList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return location_list ;
    }

    @RequestMapping(value = "/workLocationListByWorkCategory/{work_category_id}", method = RequestMethod.GET)
    public @ResponseBody
    List<WorkLocation> workLocationListByWorkCategory(@PathVariable("work_category_id") Long work_category_id){
        List<WorkLocation> locationListByWorkCategory = new ArrayList<WorkLocation>();
        String exception = null;
        try
        {
            locationListByWorkCategory = workLocListService.workLocationList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return locationListByWorkCategory ;
    }

    @RequestMapping(value = "/getJobLocationListbyGangId/{gang_id}", method = RequestMethod.GET)
    public @ResponseBody
    List<WorkLocation> getJobLocationListbyGangId(@PathVariable("gang_id") Long gang_id){
        Long work_category_id = workLocListService.getWorkCategoryByGangId(gang_id);
        List<WorkLocation> locationListByWorkCategory = new ArrayList<WorkLocation>();
        String exception = null;
        try
        {
            locationListByWorkCategory = workLocListService.workLocationListByWorkCategory(work_category_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return locationListByWorkCategory ;
    }

    @RequestMapping(value = "/getWorkLocationById/{id}", method = RequestMethod.GET)
    public ResponseEntity<WorkLocation> getLaborCategoryById(@PathVariable("id") Long id){
        WorkLocation workLocation = workLocListService.workLocationById(id);
        return new ResponseEntity<>(workLocation,HttpStatus.OK);
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> editLocation(@RequestBody WorkLocation workLocation) throws IOException{
        Long id = workLocation.getId();
        String name = workLocation.getName();
        Integer work_category_id = workLocation.getWork_category_id();

        if(id == null || id.equals("") || id.equals(" ")){
            responseMessage = new ResponseMessage( "ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name == null || name.equals("") || name.equals(" ") || name.isEmpty()){
            responseMessage = new ResponseMessage( "Work Location Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name.length()>50){
            responseMessage = new ResponseMessage( "Sorry! Maximum Work Location Name length is 50.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(work_category_id == null || work_category_id.equals("") || work_category_id.equals(" ")){
            responseMessage = new ResponseMessage( "Work Category ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isUnique = workLocEditService.isUnique(id,name);
            if(!isUnique){
                Boolean responseStatus = workLocEditService.WorkLocationUpdate(id,name,work_category_id);
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Updated Work Location Successfully.");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Update Work Location.");
                    return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Work Location Info already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.ALREADY_REPORTED);
            }
        }
    }

//    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
//    public JsonResponse deleteAll(){
//        Integer delete_all_location = workLocDeleteService.deleteAllWorkLocation();
//        if(delete_all_location >= 1){
//            responseMessage = new JsonResponse(200, "Deleted Work Location Successfully");
//        } else {
//            responseMessage = new JsonResponse(402, "Sorry! Could not Delete Work Location");
//        }
//        return responseMessage;
//    }

    @DeleteMapping("/deleteLocationById/{id}")
    public ResponseEntity<ResponseMessage> deleteLocationById(@PathVariable Long id) throws IOException{
        Boolean cntGangAssign = workLocDeleteService.chkGangAssign(id);
        if(!cntGangAssign){
            Boolean responseStatus = workLocDeleteService.deleteWorkLocationById(id);
            if(responseStatus){
                responseMessage = new ResponseMessage( "Deleted Work Location Successfully.");
                return new ResponseEntity(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = new ResponseMessage( "Sorry! Could not Delete Work Location.");
                return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            responseMessage = new ResponseMessage( "Sorry! This Work Location can't be deleted because some gang have been assigned to this location.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    //This method will be called automatically if any Exception found...
    @ExceptionHandler(value = MySQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> exceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! Invalid Work Category Info ID.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ResponseMessage> numberFormatExceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! ID must be a number.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
