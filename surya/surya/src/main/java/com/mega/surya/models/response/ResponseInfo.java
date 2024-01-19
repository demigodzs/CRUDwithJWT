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
public class ResponseInfo<T> {
    private final Response<T> body = new Response<>();
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;

    public void setSuccess() {
        this.httpStatus = HttpStatus.OK;
        body.setStatus(true);
    }

    public void setSuccess(T data) {
        body.setData(data);
        setSuccess();
    }

    public void setCommonException(CommonException e) {
        if (e.getHttpStatus() != null) {
            this.httpStatus = e.getHttpStatus();
        } else {
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        body.setStatus(false);
    }

    public void setException(Exception e) {
        if (e instanceof CommonException) {
            setCommonException((CommonException) e);
        } else {
            setCommonException(new CommonException(e));
        }
    }

    public void setFailed() {
        this.httpStatus = HttpStatus.FORBIDDEN;
        body.setStatus(false);
    }
}
