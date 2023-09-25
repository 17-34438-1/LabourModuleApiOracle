package com.datasoft.LaborModuleAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(value = { "code" })
public class ResponseMessage {
//    @JsonIgnore
//    private long code;
    private String message;

    public ResponseMessage(String message) {
        //this.code = code;
        this.message = message;
    }

//    public long getCode() {
//        return code;
//    }
//
//    public void setCode(long code) {
//        this.code = code;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
