package com.fpt.capstone.dto;

import java.util.HashMap;

public class ResponseDTO {
    private int code;
    private String message;
    private HashMap<String, Object> responseObj = new HashMap<>();

    public void addObject(String key, Object object) {
        this.responseObj.put(key, object);
    }

    public ResponseDTO() {
    }

    public ResponseDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDTO(int code, String message, HashMap<String, Object> responseObj) {
        this.code = code;
        this.message = message;
        this.responseObj = responseObj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(HashMap<String, Object> responseObj) {
        this.responseObj = responseObj;
    }
}
