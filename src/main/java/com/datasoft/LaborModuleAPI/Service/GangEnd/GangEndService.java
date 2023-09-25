package com.datasoft.LaborModuleAPI.Service.GangEnd;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Repository.GangEnd.GangEndRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GangEndService {
    @Autowired
    GangEndRepository gangEndRepository;
    ResponseMessage responseMessage;
    public ResponseEntity<ResponseMessage> getActualEndTime( Integer id){
            Integer status=gangEndRepository.updateActualEndTime(id);
            if(status==1){
                Integer updateLaborEndTime = gangEndRepository.updateLaborEndTime(id);
                responseMessage=new ResponseMessage(" End Time Has Updated Successfully");
                return new ResponseEntity(responseMessage,HttpStatus.OK);
            }
            else{
                responseMessage=new ResponseMessage("Try Again");
                return new ResponseEntity(responseMessage,HttpStatus.BAD_REQUEST);
            }
    }
}
