package com.compass.ecommerce.services;

import com.compass.ecommerce.domain.User;
import com.compass.ecommerce.dtos.RegisterDTO;
import com.compass.ecommerce.repositories.UserRepository;
import com.compass.ecommerce.services.exceptions.ExistingObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
