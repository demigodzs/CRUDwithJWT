package com.mega.surya.models.request;

import com.mega.surya.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJobRequest {
    private Long id;
    private User user;
    private String name;
    private Date startAt;
    private Date endAt;
}
