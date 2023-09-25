package com.datasoft.LaborModuleAPI.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalAPIService {
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String consumeBiometricAPI(String card_number){

        return restTemplate.getForObject("http://10.1.100.105:8095/agentdetail.php?CARDNUMBER="+card_number, String.class);
    }
}
