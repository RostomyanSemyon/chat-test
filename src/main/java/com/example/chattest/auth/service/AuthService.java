package com.example.chattest.auth.service;


import com.example.chattest.auth.AuthRequest;
import com.example.chattest.auth.AuthResponse;
import com.example.chattest.dto.RegisterRequest;
import com.example.chattest.model.user.User;
import com.example.chattest.model.user.UserRole;
import com.example.chattest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //TODO possible to make tokens refreshable
    // currently we assume that token is valid for simplicity

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                //TODO currently default role
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.userName(),
                        request.password()
                )
        );
        var user = userRepository.findUserByUserName(request.userName())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}