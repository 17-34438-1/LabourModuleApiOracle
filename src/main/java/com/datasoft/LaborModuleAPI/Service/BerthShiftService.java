package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.BerthShift;
import com.datasoft.LaborModuleAPI.Repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BerthShiftService {
    @Autowired
    private ShiftRepository shiftRepository;

    public List<BerthShift> shiftListByOrg(Integer org_id){
        String shift_for = "";
        if(org_id.equals(3363) || org_id.equals("3363")){
            shift_for = "SF";
        } else {
            shift_for = "OTH";
        }
        return shiftRepository.shiftListByOrg(shift_for);
    }
}
