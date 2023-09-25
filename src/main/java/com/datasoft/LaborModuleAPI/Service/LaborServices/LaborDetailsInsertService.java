package com.datasoft.LaborModuleAPI.Service.LaborServices;

import com.datasoft.LaborModuleAPI.Repository.LaborRepositories.LaborDetailsInsertRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LaborDetailsInsertService {
    @Autowired
    private LaborDetailsInsertRepository laborInsertRepository;

    public Boolean isExist(String nid, String phone, String entry_pass_no){
        //if(laborInsertRepository.isExist(nid,phone,entry_pass_no) > 0)
            if(laborInsertRepository.isExist(entry_pass_no) > 0)return true;
        else return false;
    }

    public Boolean laborTypeExistence(Long labor_type_id){
        if(laborInsertRepository.laborTypeExistence(labor_type_id) == 1) return true;
        else return false;
    }

    public Boolean laborDesignationExistence(Long labor_designation_id){
        if(laborInsertRepository.laborDesignationExistence(labor_designation_id) == 1) return true;
        else return false;
    }

    public Boolean laborDetailsInfoInsert(String name, Long labor_type_id, Long designation_category_id, Long berth_operator_id, Integer org_id, String nid, String phone, String entry_pass_no, Date expiration_date, String photo, String present_address, String permanent_address, Date date_of_birth, Date date_of_joining){
        if(laborInsertRepository.laborDetailsInfoInsert(name,labor_type_id,designation_category_id,berth_operator_id,org_id,nid,phone,entry_pass_no,expiration_date,photo,present_address,permanent_address,date_of_birth,date_of_joining) == 1) {
            System.out.println("Inserted");
            return true;
        } else {
            System.out.println("Failed");
            return false;
        }
    }
}
