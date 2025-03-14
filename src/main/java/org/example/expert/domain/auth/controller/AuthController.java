package org.example.expert.domain.auth.controller;

import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.dto.response.SigninResponse;
import org.example.expert.domain.auth.dto.response.SignupResponse;
import org.example.expert.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponse> signUp(@Valid @RequestBody SignupRequest signupRequest) {

        SignupResponse signupResponse = authService.signUp(signupRequest);

        String bearerToken = signupResponse.getBearerToken();

        return ResponseEntity.ok()
                .header("Authorization", bearerToken)
                .body(signupResponse);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SigninResponse> signIn(
            @Valid @RequestBody SigninRequest signinRequest) {
        SigninResponse signinResponse = authService.signIn(signinRequest);

        String bearerToken = signinResponse.getBearerToken();
        
        return ResponseEntity.ok()
                .header("Authorization",bearerToken)
                .build();
    }

}
