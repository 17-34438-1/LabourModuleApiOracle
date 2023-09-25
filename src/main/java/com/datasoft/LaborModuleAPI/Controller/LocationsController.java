package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;

import com.datasoft.LaborModuleAPI.Model.Locations;
import com.datasoft.LaborModuleAPI.Service.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/Locations")
public class LocationsController {
    @Autowired
    private LocationsService locationService;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> index(@RequestBody Locations workLocation) throws IOException {
        if(workLocation.getName() == null || workLocation.getName().equals("") || workLocation.getName().equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Location Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(workLocation.getName().length()>255){
            responseMessage = new ResponseMessage( "Sorry! Maximum Work Location Name length is 50.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isExists = locationService.isExists(workLocation.getName());
            if(!isExists){
                Boolean responseStatus = locationService.workLocationInsert(workLocation.getName());
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Added Work Location Successfully.");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Add Work Location.");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Work Location Info already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<Locations> workLocationList(){
        List<Locations> location_list = new ArrayList<Locations>();
        String exception = null;
        try
        {
            location_list = locationService.workLocationList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return location_list ;
    }

    @RequestMapping(value = "/getLocationById/{id}", method = RequestMethod.GET)
    public ResponseEntity<Locations> getLaborCategoryById(@PathVariable("id") Long id){
        Locations workLocation = locationService.workLocationById(id);
        return new ResponseEntity<>(workLocation,HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> editLocation(@RequestBody Locations workLocation) throws IOException{
        Long id = workLocation.getId();
        String name = workLocation.getName();

        if(id == null || id.equals("") || id.equals(" ")){
            responseMessage = new ResponseMessage( "ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name == null || name.equals("") || name.equals(" ") || name.isEmpty()){
            responseMessage = new ResponseMessage( "Work Location Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name.length()>255){
            responseMessage = new ResponseMessage( "Sorry! Maximum Work Location Name length is 50.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isUnique = locationService.isUnique(id,name);
            if(!isUnique){
                Boolean responseStatus = locationService.WorkLocationUpdate(id,name);
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
}
