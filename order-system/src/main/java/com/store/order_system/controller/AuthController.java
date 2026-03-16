package com.store.order_system.controller;

import com.store.order_system.model.User;
import com.store.order_system.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Registrar usuário")
    @PostMapping("/api/register")
    public User register(@RequestBody User user){
        return userService.registerUser(user);
    }
}