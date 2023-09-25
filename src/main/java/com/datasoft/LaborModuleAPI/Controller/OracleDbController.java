package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.Locations;
import com.datasoft.LaborModuleAPI.Model.OracleDb;
import com.datasoft.LaborModuleAPI.Service.LocationsService;
import com.datasoft.LaborModuleAPI.Service.OracleDbServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/OracleDb")
public class OracleDbController {
    @Autowired
    private OracleDbServices oracleDbServices;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<OracleDb> workLocationList(){
        List<OracleDb> location_list = new ArrayList<OracleDb>();
        String exception = null;
        try
        {
            location_list = oracleDbServices.OracleDbList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return location_list ;
    }

}
