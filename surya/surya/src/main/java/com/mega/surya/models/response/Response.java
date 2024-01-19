package com.mega.surya.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import com.mega.surya.config.ApplicationConstant;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    @JsonProperty("status")
    private Boolean status;
    @JsonProperty("data")
    private T data;
}
