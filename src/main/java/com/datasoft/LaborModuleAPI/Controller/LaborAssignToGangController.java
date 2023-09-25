package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.GangAssign;
import com.datasoft.LaborModuleAPI.Model.LaborByGang;
import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Model.LaborAssignToGang;
import com.datasoft.LaborModuleAPI.Service.GangAssignServices.GangAssignListService;
import com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices.LaborAssignToGangInsertService;
import com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices.LaborAssignmentDeleteService;
import com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices.LaborAssignmentEditService;
import com.datasoft.LaborModuleAPI.Service.LaborAssignToGangServices.LaborAssignmentListService;
import com.datasoft.LaborModuleAPI.Service.LaborByGangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/LaborAssignmentToGang")
public class LaborAssignToGangController {
    @Autowired
    private LaborAssignToGangInsertService laborAssignmentService;

    @Autowired
    private LaborAssignmentListService laborAssignmentListService;

    @Autowired
    private LaborAssignmentEditService laborAssignmentEditService;

    @Autowired
    private LaborAssignmentDeleteService labor_assignment_dlt_service;

    @Autowired
    private LaborByGangService laborByGangService;

    @Autowired
    private GangAssignListService gangAssignListService;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> index(@RequestBody LaborAssignToGang laborAssignment) throws IOException{
        Long gang_id = laborAssignment.getGang_id();
        Long labor_id = laborAssignment.getLabor_id();

        if(gang_id == null || gang_id.equals("") || gang_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Labor Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(labor_id == null || labor_id.equals("") || labor_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Labor Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
                Boolean gangExistence = laborAssignmentService.gangExistenceCnt(gang_id);
                Boolean laborExistenceCnt = laborAssignmentService.laborExistenceCnt(labor_id);
                Boolean laborAssignmentCnt = laborAssignmentService.laborAssignmentCnt(labor_id,gang_id);
                System.out.println(labor_id);
                if (!gangExistence) {
                    responseMessage = new ResponseMessage( "Sorry! Invalid Gang Info.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                } else if (!laborExistenceCnt) {
                    responseMessage = new ResponseMessage( "Sorry! Invalid Labor Info.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                } else if (!laborAssignmentCnt) {
                    Boolean responseStatus = laborAssignmentService.laborAssignToGangInsert(gang_id,labor_id);
                    if (responseStatus) {
                        Integer gangSize = laborAssignmentService.getGangSize(gang_id);
                        gangSize = gangSize+1;
                        laborAssignmentService.updateGangSize(gang_id,gangSize);

                        List<GangAssign> runningAssignmentListByGang = new ArrayList<GangAssign>();
                        runningAssignmentListByGang = gangAssignListService.runningAssignmentListByGang(gang_id);
                        for(int r = 0; r<runningAssignmentListByGang.size();r++){
                            Long gang_assignment_id = runningAssignmentListByGang.get(r).getId();
                            laborAssignmentService.gangAssignWiseLaborInsert(gang_assignment_id,gang_id,labor_id);
                        }

                        responseMessage = new ResponseMessage( "Added Data Successfully.");
                        return new ResponseEntity(responseMessage, HttpStatus.OK);
                    } else {
                        responseMessage = new ResponseMessage( "Sorry! Could not Add Data.");
                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Labor Assignment Info already exists.");
                    return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                }
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<LaborAssignToGang> assignmentList(){
        List<LaborAssignToGang> assignmentList = new ArrayList<LaborAssignToGang>();
        String exception = null;
        try
        {
            assignmentList = laborAssignmentListService.laborAssignmentList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return assignmentList ;
    }

    @RequestMapping(value = "/laborAssignmentById/{id}", method = RequestMethod.GET)
    public ResponseEntity<LaborAssignToGang> laborAssignmentById(@PathVariable("id") Long id){
        LaborAssignToGang laborAssignmentById = laborAssignmentListService.laborAssignmentListById(id);
        return new ResponseEntity<>(laborAssignmentById,HttpStatus.OK);
    }

    @RequestMapping(value = "/getAssignedLaborByGangId/{gang_id}", method = RequestMethod.GET)
    public @ResponseBody
    List<LaborByGang> getAssignedLaborByGangId(@PathVariable("gang_id") Long gang_id){
        List<LaborByGang> laborByGangList = new ArrayList<LaborByGang>();
        String exception = null;
        try
        {
            laborByGangList = laborByGangService.laborListByGangId(gang_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return laborByGangList ;
    }

    @RequestMapping(value = "/editLaborAssignment", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> editLaborAssignment(@RequestBody LaborAssignToGang laborAssignment) throws IOException{
        Long id = laborAssignment.getId();
        Long gang_id = laborAssignment.getGang_id();
        Long labor_id = laborAssignment.getLabor_id();

        if(id == null || id.equals("") || id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(gang_id == null || gang_id.equals("") || gang_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Gang ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(labor_id == null || labor_id.equals("") || labor_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Work Labor ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean gangExistenceCnt = laborAssignmentEditService.gangExistenceCnt(gang_id);
            Boolean laborExistenceCnt = laborAssignmentEditService.laborExistenceCnt(labor_id);
            Boolean laborAssignmentCnt = laborAssignmentEditService.laborAssignmentCnt(id,labor_id,gang_id);

            if (!gangExistenceCnt) {
                responseMessage = new ResponseMessage( "Sorry! Invalid Gang Info.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            } else if (!laborExistenceCnt) {
                responseMessage = new ResponseMessage( "Sorry! Invalid Labor Info.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            } else if (!laborAssignmentCnt) {
                Boolean responseStatus = laborAssignmentEditService.LaborAssignmentUpdate(id,gang_id,labor_id);
                if (responseStatus) {
                    responseMessage = new ResponseMessage( "Updated Labor Assignment Successfully");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Update Labor Assignment.");
                    return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                }
            } else {
                responseMessage = new ResponseMessage( "Sorry! Labor Assignment Info already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }

        }
    }

//    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
//    public JsonResponse deleteAll(){
//        Integer delete_all_assignment = labor_assignment_dlt_service.deleteAllLaborAssignment();
//        if(delete_all_assignment >= 1){
//            responseMessage = new JsonResponse(200, "Deleted Labor Assignment Successfully");
//        } else {
//            responseMessage = new JsonResponse(402, "Sorry! Could not Delete Labor Assignment");
//        }
//        return responseMessage;
//    }

    @DeleteMapping("/deleteLaborAssignment/{id}")
    public ResponseEntity<ResponseMessage> deleteCategory(@PathVariable Long id) throws IOException{
        Long gang_id = labor_assignment_dlt_service.getGangId(id);
        Boolean responseStatus = labor_assignment_dlt_service.deleteLaborAssignmentById(id);
        if(responseStatus){
            Integer gangSize = laborAssignmentService.getGangSize(gang_id);
            gangSize = gangSize-1;
            laborAssignmentService.updateGangSize(gang_id,gangSize);

            responseMessage = new ResponseMessage( "Deleted Labor Assignment Successfully.");
            return new ResponseEntity(responseMessage, HttpStatus.OK);
        } else {
            responseMessage = new ResponseMessage( "Sorry! Could not Delete Labor Assignment.");
            return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/deleteLaborAssignmentByGandAndLabor/{gang_id}/{labor_id}")
    public ResponseEntity<ResponseMessage> deleteLaborAssignmentByGandAndLabor(@PathVariable Long gang_id,@PathVariable Long labor_id) throws IOException{
        Integer cntLaborAssignment = labor_assignment_dlt_service.cntLaborAssignmentByGangAndLabor(gang_id,labor_id);
        if(cntLaborAssignment.equals(1) || cntLaborAssignment.equals("1") || cntLaborAssignment == 1){
            Boolean responseStatus = labor_assignment_dlt_service.deleteLaborAssignmentByGandAndLabor(gang_id,labor_id);
            if(responseStatus){
                Integer gangSize = laborAssignmentService.getGangSize(gang_id);
                gangSize = gangSize-1;
                laborAssignmentService.updateGangSize(gang_id,gangSize);

                labor_assignment_dlt_service.endTimeForLabor(gang_id,labor_id);

                responseMessage = new ResponseMessage( "Deleted Labor Assignment Successfully.");
                return new ResponseEntity(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = new ResponseMessage( "Sorry! Could not Delete Labor Assignment.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMessage = new ResponseMessage( "Sorry! No Labor Assignment for given gang and labor.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }

    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ResponseMessage> exceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! ID must be a number.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }
}
