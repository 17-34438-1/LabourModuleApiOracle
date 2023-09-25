package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Service.GangEnd.GangEndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GangEndController {
    @Autowired
    GangEndService gangEndService;

    @RequestMapping(value = "/GangEnd/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseMessage> getActualEndTime(@PathVariable Integer id){
        return gangEndService.getActualEndTime(id);
    }
}
