package com.example.demo.dto;

import com.example.demo.exception.CustomizedErrorCode;
import com.example.demo.exception.CustomizedException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizedErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("Success!");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizedException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }
}
