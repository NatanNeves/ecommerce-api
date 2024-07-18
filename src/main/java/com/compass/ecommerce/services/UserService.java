package com.compass.ecommerce.services;

import com.compass.ecommerce.configuration.security.TokenService;
import com.compass.ecommerce.domain.User;
import com.compass.ecommerce.dtos.RegisterDTO;
import com.compass.ecommerce.repositories.UserRepository;
import com.compass.ecommerce.services.exceptions.ExistingObjectException;
import com.compass.ecommerce.services.exceptions.NotFoundException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    public void resetPassword(User user) throws MessagingException {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional!=null) {
            String token = tokenService.generateToken(user);
            String resetLink = "http://localhost:8080/api/v1/users/reset-password?token=" + token;
            emailService.sendEmail(user.getEmail(), "resefina sua senha", "clique no link para atualiza-la " + resetLink);
        } else {
            throw new NotFoundException("usuario não encontrado com email: " + user.getEmail());
        }
    }

    public void updatePassword(String token, String newPassword) {
        String email = tokenService.validateToken(token);
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new NotFoundException("usuario não encontrado com email:" + email);
        }
    }

    public User createUser(RegisterDTO data) {
        UserDetails existingUser = userRepository.findByLogin(data.login());
        if (existingUser != null) {
            throw new ExistingObjectException("Usuário com login " + data.login() + " já existe");
        }

        String encodedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.login(), encodedPassword, data.email(), data.role());
        return userRepository.save(newUser);
    }
}
