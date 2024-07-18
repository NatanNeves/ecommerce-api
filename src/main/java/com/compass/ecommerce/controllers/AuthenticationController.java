package com.compass.ecommerce.controllers;

import com.compass.ecommerce.configuration.security.TokenService;
import com.compass.ecommerce.domain.User;
import com.compass.ecommerce.dtos.AuthenticationDTO;
import com.compass.ecommerce.dtos.LoginResponseDTO;
import com.compass.ecommerce.dtos.RegisterDTO;
import com.compass.ecommerce.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and generate JWT token")
    @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json"))
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Create a new user account")
    @ApiResponse(responseCode = "200", description = "User registered", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json"))
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        userService.createUser(data);

        return ResponseEntity.ok().build();
    }
}
