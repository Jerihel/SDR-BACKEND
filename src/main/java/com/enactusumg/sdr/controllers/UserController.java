package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.services.EmailService;
import com.enactusumg.sdr.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@Slf4j
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public EmailService emailService;

    @GetMapping(value = "internal/users")
    @ApiOperation(value = "Retorna todos los usuarios registrados en el sistema.")
    public List<User> loginUser() {
        return userService.getAllUsers();
    }
}
