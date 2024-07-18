package com.compass.ecommerce.controllers;

import com.compass.ecommerce.domain.User;
import com.compass.ecommerce.dtos.UpdatePasswordDTO;
import com.compass.ecommerce.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Endpoints for managing user accounts")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/reset-password")
    @Operation(summary = "Reset user password", description = "Send password reset email to user")
    @ApiResponse(responseCode = "200", description = "Password reset email sent", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json"))
    public void resetPassword(@RequestBody User user) throws MessagingException {
        userService.resetPassword(user);
    }

    @PostMapping("/update-password")
    @Operation(summary = "Update user password", description = "Update user password using reset token")
    @ApiResponse(responseCode = "200", description = "Password updated successfully", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json"))
    public void updatePassword(@RequestBody UpdatePasswordDTO request) {
        userService.updatePassword(request.token(), request.newPassword());
    }
}
