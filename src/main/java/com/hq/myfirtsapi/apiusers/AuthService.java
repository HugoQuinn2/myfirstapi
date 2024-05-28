package com.hq.myfirtsapi.apiusers;

import com.hq.myfirtsapi.jwt.IJwtService;
import com.hq.myfirtsapi.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final IUserApiRepository userApiRepository;
    private final IJwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthModel login(LoginModel request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        UserDetails user = userApiRepository.findByUsername(
                request.getUsername()).orElseThrow();

        return AuthModel.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public AuthModel register(RegisterModel request) {
        UserApiModel user = UserApiModel.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        if (doesUserExist(request.getUsername())){
            userApiRepository.save(user);
        }else{
            log.info(AuthError.DUPLICATED_USER.getMessage(request.getUsername()));
        }

        return AuthModel.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    private boolean doesUserExist(String username){
        Optional<UserApiModel> userApiModel = userApiRepository.findByUsername(username);
        return userApiModel.isPresent();
    }
}
