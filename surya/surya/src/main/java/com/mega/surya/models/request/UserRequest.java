package com.mega.surya.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String roles;
    private Boolean isActive;
    private UserDetailRequest userDetail;
    private List<UserJobRequest> userJobs;
}
