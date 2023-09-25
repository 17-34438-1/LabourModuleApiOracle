package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.Locations;
import com.datasoft.LaborModuleAPI.Repository.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationsService {
    @Autowired
    private LocationsRepository locationRepo;

    public Boolean isExists(String name){
        if(locationRepo.isExists(name) > 0) return true;
        else return false;
    }

    public Boolean workLocationInsert(String name){
        if(locationRepo.workLocationsInsert(name) == 1) return true;
        else return false;
    }

    public List<Locations> workLocationList(){ return locationRepo.workLocationsList(); }

    public Locations workLocationById(Long id){ return locationRepo.workLocationById(id);}

    public Boolean isUnique(Long id,String name){
        if(locationRepo.isUnique(id,name) > 0) return true;
        else return false;
    }

    public Boolean WorkLocationUpdate(Long id,String name){
        if(locationRepo.WorkLocationUpdate(id,name) ==1) return true;
        else return false;
    }
}
