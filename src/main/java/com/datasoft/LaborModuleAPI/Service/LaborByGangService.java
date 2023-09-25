package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.LaborByGang;
import com.datasoft.LaborModuleAPI.Repository.LaborByGangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaborByGangService {
    @Autowired
    private LaborByGangRepository laborByGangRepo;

    public List<LaborByGang> laborListByGangId(Long gang_id){ return laborByGangRepo.laborListByGangId(gang_id); }
}
