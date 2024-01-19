package com.mega.surya.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailRequest {
    private Long id;
    private String firstName;
    private String lastName;
}
