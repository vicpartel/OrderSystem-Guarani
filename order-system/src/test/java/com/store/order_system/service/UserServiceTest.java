package com.store.order_system.service;

import com.store.order_system.model.Role;
import com.store.order_system.model.User;
import com.store.order_system.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserWithEncodedPassword() {

        User user = new User();
        user.setPassword("123");

        when(passwordEncoder.encode("123"))
                .thenReturn("encoded123");

        when(userRepository.save(any()))
                .thenReturn(user);

        User saved = userService.registerUser(user);

        assertEquals("encoded123", saved.getPassword());
        assertEquals(Role.CLIENT, saved.getRole());
    }
}