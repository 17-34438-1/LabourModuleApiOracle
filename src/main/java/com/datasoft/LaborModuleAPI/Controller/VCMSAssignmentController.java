package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Model.VCMSAssignment;
import com.datasoft.LaborModuleAPI.Service.VCMSAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/VCMSAssignment")
public class VCMSAssignmentController {

    @Autowired
    private VCMSAssignmentService vcmsAssignmentService;

    ResponseMessage responseMessage;

    @RequestMapping(value = "/containerListByDateAndYard/{assignmentDate}/{yardNo}", method = RequestMethod.GET)
    public @ResponseBody List<VCMSAssignment> containerListByDateAndYard(@PathVariable("assignmentDate") String assignmentDate, @PathVariable("yardNo") String yardNo){
        List<VCMSAssignment> vcmsAssignments = new ArrayList<VCMSAssignment>();
        String exception = null;
        try
        {
            vcmsAssignments = vcmsAssignmentService.containerListByDateAndYard(assignmentDate,yardNo);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return vcmsAssignments;
    }

    @RequestMapping(value = "/containerListOfGangAssignmentForUnstuffing/{assignment_id}", method = RequestMethod.GET)
    public @ResponseBody Object containerListOfGangAssignmentForUnstuffing(@PathVariable("assignment_id") Integer assignment_id){
        List<VCMSAssignment> containers = new ArrayList<VCMSAssignment>();
        String exception = null;
        try
        {
            containers = vcmsAssignmentService.containerListOfGangAssignmentForUnstuffing(assignment_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return containers;
    }

    @RequestMapping(value = "/containerListOfGangAssignmentForYard/{assignment_id}", method = RequestMethod.GET)
    public @ResponseBody Object containerListOfGangAssignmentForYard(@PathVariable("assignment_id") Integer assignment_id){
        List<VCMSAssignment> containers = new ArrayList<VCMSAssignment>();
        String exception = null;
        try
        {
            containers = vcmsAssignmentService.containerListOfGangAssignmentForYard(assignment_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return containers;
    }


}
