package com.datasoft.LaborModuleAPI.Service.LaborServices;

import com.datasoft.LaborModuleAPI.Repository.LaborRepositories.LaborDetailsEditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LaborDetailsEditService {
    @Autowired
    private LaborDetailsEditRepository laborEditRepository;

    public Long findLaborIdByNID(String nid){
        Long id = laborEditRepository.findLaborIdByNID(nid);
        return id;
    }

    public Long findLaborIdByPhone(String phone){
        Long id = laborEditRepository.findLaborIdByPhone(phone);
        return id;
    }

    public Long findLaborIdByEntryPassNo(String entryPassNo){
        Long id = laborEditRepository.findLaborIdByEntryPassNo(entryPassNo);
        return id;
    }

    public String isValidUpdate(Long laborId,String nid, String phone, String entryPassNo){
        Boolean validEntryPass = false;
        Boolean validNID = false;
        Boolean validPhone = false;
        if(laborEditRepository.findLaborIdByEntryPassNo(entryPassNo) == null){
            validEntryPass = true;
        } else if(laborEditRepository.findLaborIdByEntryPassNo(entryPassNo) == laborId) {
            validEntryPass = true;
        } else {
            return "Sorry! Entry Pass ID already exists.";
        }

        if(laborEditRepository.findLaborIdByPhone(phone) == null){
            validPhone = true;
        } else if(laborEditRepository.findLaborIdByPhone(phone) == laborId) {
            validPhone = true;
        } else {
            return "Sorry! Phone Number already exists.";
        }

        if(laborEditRepository.findLaborIdByNID(nid) == null){
            validNID = true;
        } else if(laborEditRepository.findLaborIdByNID(nid) == laborId) {
            validNID = true;
        } else {
            return "Sorry! NID Number already exists.";
        }

        if(validEntryPass && validPhone && validNID){
            return "VALID";
        }
        else return "NOT VALID";
    }



    public Boolean isExist(String nid,String phone, String entry_pass_no){
        if(laborEditRepository.isExist(nid,phone,entry_pass_no) > 0) return true;
        else return false;
    }

    public Boolean laborTypeExistence(Long labor_type_id){
        if(laborEditRepository.laborTypeExistence(labor_type_id) == 1) return true;
        else return false;
    }

    public Boolean laborDesignationExistence(Long designation_category_id){
        if(laborEditRepository.laborDesignationExistence(designation_category_id) == 1) return true;
        else return false;
    }

    public Boolean laborUpdate(Long id,String name,Long labor_type_id,Long designation_category_id,Long berth_operator_id, Integer org_id ,String nid, String phone, String entry_pass_no, Date expiration_date, String photo,String present_address,String permanent_address,Date date_of_birth,Date date_of_joining){
        if(laborEditRepository.laborUpdate(id,name,labor_type_id,designation_category_id,berth_operator_id,org_id,nid,phone,entry_pass_no,expiration_date,photo,present_address,permanent_address,date_of_birth,date_of_joining) == 1)
            return true;
        else return false;
    }
}
