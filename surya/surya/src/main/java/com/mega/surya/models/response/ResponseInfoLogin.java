package com.mega.surya.models.response;

import com.mega.surya.config.ApplicationConstant;
import com.mega.surya.exception.CommonException;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfoLogin<T> {
    private final ResponseLogin<T> body = new ResponseLogin<>();
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;

    public void setSuccess(String token) {
        this.httpStatus = HttpStatus.OK;
        body.setStatus(true);
        body.setMessage("Successfully Login");
        body.setAccess_token(token);
        body.setRefresh_token(token);
    }

    public void setFailed(String message) {
        this.httpStatus = HttpStatus.FORBIDDEN;
        body.setStatus(false);
        body.setMessage(message);
    }
}
