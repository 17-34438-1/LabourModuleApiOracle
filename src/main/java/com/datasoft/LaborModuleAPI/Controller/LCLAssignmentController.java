package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.LCLAssignment;
import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Service.LCLAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/lclAssignment")
public class LCLAssignmentController {
    @Autowired
    private LCLAssignmentService lclAssignmentService;

    ResponseMessage responseMessage;

    @Autowired
    @Qualifier("jdbcTemplatePrimary")
    private JdbcTemplate primaryDBTemplate;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    @RequestMapping(value = "/rotAndBlList", method = RequestMethod.POST)
    public @ResponseBody Object index(@RequestBody LCLAssignment lclAssignment) throws IOException {
        String assignment_date = lclAssignment.getAssignment_date();
        String cont_loc_shed = lclAssignment.getCont_loc_shed();
        List<Object> rotAndBlList = new ArrayList<Object>();
        String exception = null;
        try {
            rotAndBlList = lclAssignmentService.rotAndBlList(assignment_date,cont_loc_shed);
            return rotAndBlList;
        }catch(Exception ex) {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return rotAndBlList;
    }

    @RequestMapping(value = "/containerList", method = RequestMethod.POST)
    public @ResponseBody Object containerList(@RequestBody LCLAssignment lclAssignment){
        String assignment_date = lclAssignment.getAssignment_date();
        String cont_loc_shed = lclAssignment.getCont_loc_shed();
        List<Object> containers = new ArrayList<Object>();
        String exception = null;
        try {
            containers = lclAssignmentService.containerList(assignment_date,cont_loc_shed);
            return containers;
        }catch(Exception ex) {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return containers;
    }

    @RequestMapping(value = "/rotListByDateAndShed", method = RequestMethod.POST)
    public @ResponseBody Object rotListByDateAndShed(@RequestBody LCLAssignment lclAssignment) throws IOException {
        String assignment_date = lclAssignment.getAssignment_date();
        String cont_loc_shed = lclAssignment.getCont_loc_shed();
        List<Object> rotList = new ArrayList<Object>();
        String exception = null;
        try {
            rotList = lclAssignmentService.rotList(assignment_date,cont_loc_shed);
            return rotList;
        }catch(Exception ex) {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return rotList;
    }

    @RequestMapping(value = "/blListByDateAndShed", method = RequestMethod.POST)
    public @ResponseBody Object blList(@RequestBody LCLAssignment lclAssignment) throws IOException {
        String assignment_date = lclAssignment.getAssignment_date();
        String cont_loc_shed = lclAssignment.getCont_loc_shed();
        List<Object> blList = new ArrayList<Object>();
        String exception = null;
        try {
            blList = lclAssignmentService.blList(assignment_date,cont_loc_shed);
            System.out.println(blList.size());
            return blList;
        }catch(Exception ex) {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return blList;
    }

    @RequestMapping(value = "/assignmentPendingRotationList", method = RequestMethod.GET)
    public @ResponseBody List<LCLAssignment> assignmentPendingRotationList(){
        List<LCLAssignment> rot_list = new ArrayList<LCLAssignment>();
        String exception = null;
        try
        {
            rot_list = lclAssignmentService.notAssignedRotationList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return rot_list ;
    }

}
