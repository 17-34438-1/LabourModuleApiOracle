package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.BerthShift;
import com.datasoft.LaborModuleAPI.Service.BerthShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/shift")
public class ShiftController {

    @Autowired
    private BerthShiftService berthShiftService;

    @RequestMapping(value = "/listByOrg/{org_id}", method = RequestMethod.GET)
    public @ResponseBody List<BerthShift> shiftListByOrg(@PathVariable Integer org_id){
        return berthShiftService.shiftListByOrg(org_id);
    }
}
