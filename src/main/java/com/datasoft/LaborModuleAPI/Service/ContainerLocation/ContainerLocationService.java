package com.datasoft.LaborModuleAPI.Service.ContainerLocation;

import com.datasoft.LaborModuleAPI.DAO.ContainerLocationDAO;
import com.datasoft.LaborModuleAPI.Repository.ContainerLocationRepositories.ContainerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerLocationService {
    @Autowired
    private ContainerLocationDAO cont_loc_dao;

    public List location(String cont_number){
        List location = cont_loc_dao.getContainerLocation(cont_number);
        return location;
    }
}
