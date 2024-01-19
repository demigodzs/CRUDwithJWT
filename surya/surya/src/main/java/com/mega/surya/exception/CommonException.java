package com.mega.surya.exception;

import com.mega.surya.config.ApplicationConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommonException extends Exception {
    private ApplicationConstant.COMPLETION_STATUS status;
    private String code;
    private String type;
    private String displayMessage;
    private HttpStatus httpStatus;


    public CommonException(Exception e) {
        super(e.getMessage());
        this.status = ApplicationConstant.COMPLETION_STATUS.SYSTEM_ERROR;
        this.code = "99";
        this.type = e.getClass().getSimpleName();
        this.displayMessage = "Error: " + e.getMessage();
    }
}
