package com.hq.myfirtsapi.services;

import com.hq.myfirtsapi.apiusers.Role;
import com.hq.myfirtsapi.apiusers.UserApiModel;
import com.hq.myfirtsapi.jwt.IJwtService;
import com.hq.myfirtsapi.models.*;
import com.hq.myfirtsapi.apiusers.IUserApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserApiRepository userApiRepository;
    private final IJwtService jwtService;
    public AuthModel login(LoginModel request) {
        return null;
    }

    public AuthModel register(RegisterModel request) {
        UserApiModel user = UserApiModel.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .password(request.getPassword())
                .role(Role.USER)
                .build();

        userApiRepository.save(user);

        return AuthModel.builder()
                .token(jwtService.getToken(user))
                .build();

    }
}
