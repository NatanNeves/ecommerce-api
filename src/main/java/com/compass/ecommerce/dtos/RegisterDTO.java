package com.compass.ecommerce.dtos;

import com.compass.ecommerce.domain.enums.UserType;

public record RegisterDTO(String login, String password, String email, UserType role) {
}
