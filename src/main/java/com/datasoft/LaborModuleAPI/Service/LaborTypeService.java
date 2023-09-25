package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Model.LaborType;
import com.datasoft.LaborModuleAPI.Repository.LaborTypeRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaborTypeService {
    @Autowired
    private LaborTypeRepository laborTypeRepository;
    
    ResponseMessage jsonMsg;
    
    public List<LaborType> listLaborType(){ return laborTypeRepository.laborTypeListQuery(); }

    public List<LaborType> listLaborTypeById(Long id){
        return laborTypeRepository.laborTypeListByIdQuery(id);
    }

    public Boolean isExists(String name){
        if(laborTypeRepository.isExists(name) > 0)
            return true;
         else
            return false;
    }

    public Boolean addLaborType(LaborType laborType){
        if(laborTypeRepository.insertTypeQuery(laborType.getName()) == 1){
            return true;
        } else {
            return false;
        }
    }

    public Boolean isUnique(Long id,String name){
        if(laborTypeRepository.isUnique(id,name) > 0){
            return true;
        } else {
            return false;
        }
    }

    public Integer updateLaborType(Long id,String name){
        return laborTypeRepository.updateLaborType(id,name);
    }

    public Integer deleteAllType(){
        return laborTypeRepository.deleteAllLaborType();
    }

    public Integer chkLaborDtlsInfo(Long id){
        return laborTypeRepository.chkLaborDtlsInfo(id);
    }

    public Integer chkDesignation(Long id){
        return laborTypeRepository.chkDesignation(id);
    }

    public Integer deleteTypeById(Long id){
        return laborTypeRepository.deleteLaborTypeById(id);
    }

    public LaborType getLaborTypeById(Long id){
        return laborTypeRepository.getLaborTypeById(id);
    }
}
