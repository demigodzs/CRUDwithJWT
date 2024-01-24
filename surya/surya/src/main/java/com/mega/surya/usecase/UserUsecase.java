package com.mega.surya.usecase;

import com.mega.surya.models.User;
import com.mega.surya.models.UserDetail;
import com.mega.surya.models.UserJob;
import com.mega.surya.models.request.LoginRequest;
import com.mega.surya.models.request.UserRequest;
import com.mega.surya.models.response.*;
import com.mega.surya.repository.UserDetailRepository;
import com.mega.surya.repository.UserJobRepository;
import com.mega.surya.repository.UserRepository;
import com.mega.surya.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserUsecase {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtTokenProvider;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserDetailRepository userDetailRepository;

    @Autowired
    public UserJobRepository userJobRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public ResponseInfoLogin<Object> login(LoginRequest loginRequest)
    {
        ResponseInfoLogin responseInfoLogin = ResponseInfoLogin.builder().build();
        try {

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                responseInfoLogin.setSuccess(jwtTokenProvider.generateToken(loginRequest.getUsername()));
            }
            else
            {
                responseInfoLogin.setFailed("Unauthorized");
            }
        }
        catch (Exception e)
        {
            responseInfoLogin.setFailed("Unauthorized");
        }

        return responseInfoLogin;
    }

    public ResponseInfo<Object> getAccount(String token)
    {
        ResponseInfo responseInfo = ResponseInfo.builder().build();
        try
        {
            String tokenTemp = extractTokenFromAuthorizationHeader(token);
            String username = jwtTokenProvider.extractUsername(tokenTemp);

            Optional<User> user = this.userRepository.findByUsername(username);

            if(user.isPresent())
            {
                responseInfo.setSuccess(user);
            }
            else
            {
                responseInfo.setFailed();
            }
        }
        catch (Exception e)
        {
            responseInfo.setException(e);
        }

        return responseInfo;
    }

    public ResponseInfo<Object> getUsers(String token)
    {
        ResponseInfo responseInfo = ResponseInfo.builder().build();
        try
        {
            String tokenTemp = extractTokenFromAuthorizationHeader(token);
            String username = jwtTokenProvider.extractUsername(tokenTemp);

            List<UserDto> userDTOs = new ArrayList<>();
            List<User> users = this.userRepository.findByIsActive(true);

            for (User user : users) {
                UserDto userDTO = new UserDto();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());

                UserDetail userDetail = userDetailRepository.findByUserId(user.getId()).orElse(null);
                if (userDetail != null) {
                    UserDetailDto detailDTO = new UserDetailDto();
                    detailDTO.setFirstName(userDetail.getFirstName());
                    detailDTO.setLastName(userDetail.getLastName());
                    userDTO.setDetail(detailDTO);
                }

                List<UserJob> userJobs = userJobRepository.findByUserId(user.getId());
                List<UserJobDto> jobDTOs = new ArrayList<>();
                for (UserJob job : userJobs) {
                    UserJobDto jobDTO = new UserJobDto();
                    jobDTO.setName(job.getName());
                    jobDTO.setStartAt(job.getStartAt());
                    jobDTO.setUntilAt(job.getEndAt());
                    jobDTOs.add(jobDTO);
                }
                userDTO.setJobs(jobDTOs);

                userDTO.setCreatedBy(user.getCreatedBy());
                userDTO.setCreatedAt(user.getCreatedAt());
                userDTO.setUpdatedBy(user.getUpdatedBy());
                userDTO.setUpdatedAt(user.getUpdatedAt());
                userDTO.setDeletedBy(user.getDeletedBy());
                userDTO.setDeletedAt(user.getDeletedAt());

                userDTOs.add(userDTO);
            }



            responseInfo.setSuccess(userDTOs);
        }
        catch (Exception e)
        {
            responseInfo.setException(e);
        }

        return responseInfo;
    }

    public ResponseInfo<Object> getUserById(String token, Long id)
    {
        ResponseInfo responseInfo = ResponseInfo.builder().build();
        try
        {
            String tokenTemp = extractTokenFromAuthorizationHeader(token);
            String username = jwtTokenProvider.extractUsername(tokenTemp);

            Optional<User> user = this.userRepository.findById(id);

            if(user.isPresent())
            {
                responseInfo.setSuccess(user);
            }
            else
            {
                responseInfo.setFailed();
            }
        }
        catch (Exception e)
        {
            responseInfo.setException(e);
        }

        return responseInfo;
    }

    public ResponseInfo<Object> createUser(String token, UserRequest userRequest)
    {
        ResponseInfo responseInfo = ResponseInfo.builder().build();
        try
        {
            String tokenTemp = extractTokenFromAuthorizationHeader(token);
            String username = jwtTokenProvider.extractUsername(tokenTemp);
            Optional<User> userLogin = this.userRepository.findByUsername(username);

            User user = new User(userRequest.getId(),
                    userRequest.getUsername(),
                    passwordEncoder.encode(userRequest.getPassword()),
                    userRequest.getRoles(),
                    true,
                    userLogin.get().getId(),
                    Date.from(Instant.now()),
                    userLogin.get().getId(),
                    Date.from(Instant.now()),
                    userLogin.get().getId(),
                    Date.from(Instant.now())
                    );

//            UserDetail userDetail = new UserDetail(userRequest.getUserDetail().getId(),
//                    userRequest.getId(),
//                    user,
//                    userRequest.getUserDetail().getFirstName(),
//                    userRequest.getUserDetail().getLastName(),
//                    userLogin.get().getId(),
//                    Date.from(Instant.now()),
//                    userLogin.get().getId(),
//                    Date.from(Instant.now()),
//                    userLogin.get().getId(),
//                    Date.from(Instant.now())
//            );

            this.userRepository.save(user);
//            this.userDetailRepository.save(userDetail);

//            for(int i=0;i<userRequest.getUserJobs().size(); i++)
//            {
//                UserJob userJob = new UserJob(
//                        userRequest.getUserJobs().get(i).getId(),
//                        userRequest.getId(),
//                        user,
//                        userRequest.getUserJobs().get(i).getName(),
//                        userRequest.getUserJobs().get(i).getStartAt(),
//                        userRequest.getUserJobs().get(i).getEndAt(),
//                        userLogin.get().getId(),
//                        Date.from(Instant.now()),
//                        userLogin.get().getId(),
//                        Date.from(Instant.now()),
//                        userLogin.get().getId(),
//                        Date.from(Instant.now())
//                );
//
//                this.userJobRepository.save(userJob);
//            }

            responseInfo.setSuccess();

        }
        catch (Exception e)
        {
            responseInfo.setException(e);
        }

        return responseInfo;
    }

    public ResponseInfo<Object> editUser(String token, Long id, UserRequest userRequest)
    {

        ResponseInfo responseInfo = ResponseInfo.builder().build();
        try
        {
            String tokenTemp = extractTokenFromAuthorizationHeader(token);
            String username = jwtTokenProvider.extractUsername(tokenTemp);
            Optional<User> userLogin = this.userRepository.findByUsername(username);

            Optional<User> userData = this.userRepository.findById(id);
            if(userData.isPresent())
            {
                User user = new User(id,
                        userRequest.getUsername(),
                        passwordEncoder.encode(userRequest.getPassword()),
                        userRequest.getRoles(),
                        userData.get().getIsActive(),
                        userData.get().getCreatedBy(),
                        userData.get().getCreatedAt(),
                        userLogin.get().getId(),
                        Date.from(Instant.now()),
                        userData.get().getDeletedBy(),
                        userData.get().getDeletedAt()
                );

                Optional<UserDetail> userDetailData = this.userDetailRepository.findByUserId(id);

                UserDetail userDetail = new UserDetail(userDetailData.get().getId(),
                        id,
                        user,
                        userRequest.getUserDetail().getFirstName(),
                        userRequest.getUserDetail().getLastName(),
                        userDetailData.get().getCreatedBy(),
                        userDetailData.get().getCreatedAt(),
                        userLogin.get().getId(),
                        Date.from(Instant.now()),
                        userDetailData.get().getDeletedBy(),
                        userDetailData.get().getDeletedAt()
                );

                this.userRepository.save(user);
                this.userDetailRepository.save(userDetail);

                responseInfo.setSuccess();
            }
        }
        catch (Exception e)
        {
            responseInfo.setException(e);
        }

        return responseInfo;
    }

    public ResponseInfo<Object> deleteUser(String token, Long id)
    {
        ResponseInfo responseInfo = ResponseInfo.builder().build();
        try
        {
            String tokenTemp = extractTokenFromAuthorizationHeader(token);
            String username = jwtTokenProvider.extractUsername(tokenTemp);
            Optional<User> userLogin = this.userRepository.findByUsername(username);

            Optional<User> userData = this.userRepository.findById(id);

            if(userData.isPresent())
            {
                User user = new User();
                user.setId(id);
                user.setIsActive(false);
                user.setDeletedBy(userLogin.get().getId());
                user.setDeletedAt(Date.from(Instant.now()));
                user.setUpdatedBy(userData.get().getUpdatedBy());
                user.setUpdatedAt(userData.get().getUpdatedAt());
                user.setCreatedBy(userData.get().getCreatedBy());
                user.setCreatedAt(userData.get().getCreatedAt());
                user.setUsername(userData.get().getUsername());
                user.setPassword(userData.get().getPassword());
                user.setRoles(userData.get().getRoles());
                this.userRepository.save(user);

                responseInfo.setSuccess();
            }
            else
            {
                responseInfo.setFailed();
            }
        }
        catch (Exception e)
        {
            responseInfo.setException(e);
        }

        return responseInfo;
    }

    private String extractTokenFromAuthorizationHeader(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
