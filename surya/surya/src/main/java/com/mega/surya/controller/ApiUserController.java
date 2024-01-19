package com.mega.surya.controller;


import com.mega.surya.models.request.LoginRequest;
import com.mega.surya.models.request.UserRequest;
import com.mega.surya.models.response.ResponseInfo;
import com.mega.surya.models.response.ResponseInfoLogin;
import com.mega.surya.service.JwtService;
import com.mega.surya.usecase.UserUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ApiUserController {

    @Autowired
    public UserUsecase userUsecase;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
    {
        ResponseInfoLogin<Object> responseInfoLogin = userUsecase.login(loginRequest);
        return new ResponseEntity<>(responseInfoLogin.getBody(),
                responseInfoLogin.getHttpHeaders(),
                responseInfoLogin.getHttpStatus());
    }

    @GetMapping("account")
    public ResponseEntity<?> getAccount(@RequestHeader("Authorization") String authorizationHeader)
    {
        ResponseInfo<Object> responseInfo = userUsecase.getAccount(authorizationHeader);
        return new ResponseEntity<>(responseInfo.getBody(),
                responseInfo.getHttpHeaders(),
                responseInfo.getHttpStatus());
    }

    @GetMapping("users")
    public ResponseEntity<?> getUsers(@RequestHeader("Authorization") String authorizationHeader)
    {
        ResponseInfo<Object> responseInfo = userUsecase.getUsers(authorizationHeader);
        return new ResponseEntity<>(responseInfo.getBody(),
                responseInfo.getHttpHeaders(),
                responseInfo.getHttpStatus());
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUserById(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("id") Long id
    ){
        ResponseInfo<Object> responseInfo = userUsecase.getUserById(authorizationHeader, id);
        return new ResponseEntity<>(responseInfo.getBody(),
                responseInfo.getHttpHeaders(),
                responseInfo.getHttpStatus());
    }

    @PostMapping("users")
    public ResponseEntity<?> addPerson(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UserRequest userRequest
    ){
        ResponseInfo<Object> responseInfo = userUsecase.createUser(authorizationHeader, userRequest);
        return new ResponseEntity<>(responseInfo.getBody(),
                responseInfo.getHttpHeaders(),
                responseInfo.getHttpStatus());
    }

    @PutMapping("users/edit/{id}")
    public ResponseEntity<?> editUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("id") Long id,
            @RequestBody UserRequest userRequest
    ){
        ResponseInfo<Object> responseInfo = userUsecase.editUser(authorizationHeader, id, userRequest);
        return  new ResponseEntity<>(responseInfo.getBody(),
                responseInfo.getHttpHeaders(),
                responseInfo.getHttpStatus());
    }

    @PutMapping("users/delete/{id}")
    public ResponseEntity<?> deleteUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("id") Long id
    ){
        ResponseInfo<Object> responseInfo = userUsecase.deleteUser(authorizationHeader, id);
        return  new ResponseEntity<>(responseInfo.getBody(),
                responseInfo.getHttpHeaders(),
                responseInfo.getHttpStatus());
    }
}
