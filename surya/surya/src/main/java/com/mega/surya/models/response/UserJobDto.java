package com.mega.surya.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJobDto {
    private String name;
    private Date startAt;
    private Date untilAt;
}
