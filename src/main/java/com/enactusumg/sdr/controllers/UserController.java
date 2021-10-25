package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.config.security.JwtUtil;
import com.enactusumg.sdr.dto.CreateUserDto;
import com.enactusumg.sdr.dto.UserCreatedDto;
import com.enactusumg.sdr.dto.UserDto;
import com.enactusumg.sdr.dto.UserLoggedDto;
import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Api
@RestController
@Slf4j
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping(value = "internal/users/token/refresh")
    @ApiOperation(value = "Retorna todos los usuarios registrados en el sistema.")
    public UserLoggedDto refreshToken(@RequestHeader HttpHeaders headers) {
        return userService.refreshToken(headers);
    }

    @PostMapping(value = "internal/users/token/revoke")
    @ApiOperation(value = "Retorna todos los usuarios registrados en el sistema.")
    public void revokeToken(@RequestHeader HttpHeaders headers) {
        userService.revokeToken(headers);
    }

    @GetMapping(value = "internal/users/find")
    @ApiOperation(value = "Retorna todos los usuarios registrados en el sistema.")
    public List<User> getAllUser(@RequestHeader HttpHeaders headers) {
        return userService.getAllUsers(headers);
    }

    @GetMapping(value = "internal/users/find/{userId}")
    @ApiOperation(value = "Retorna todos los usuarios registrados en el sistema.")
    public User getUser(@RequestHeader HttpHeaders headers, @PathVariable String userId) {
        return userService.getUser(headers, userId);
    }

    @PostMapping(value = "internal/users/create")
    @ApiOperation(value = "Crea un registro de usuario en base de datos.")
    public UserCreatedDto createUser(@RequestHeader HttpHeaders headers, @RequestBody CreateUserDto dto) {
        return userService.createUser(headers, dto);
    }

    @PatchMapping(value = "internal/users/edit")
    @ApiOperation(value = "Actualiza el registro de un usuario en base de datos.")
    public void updateUser() {
    }

    @PostMapping(value = "external/users/login")
    @ApiOperation(value = "Inicia la sesion de un usuario en base a el nombre de usuario y contraseña.")
    public UserLoggedDto loginUser(@RequestBody UserDto dto) {
        return userService.authUser(dto);
    }

    @PatchMapping(value = "external/users/recover/password/{token}")
    @ApiOperation(value = "Recupera la contraseña de un usuario en base de datos.")
    public boolean recoverPassword(@PathVariable String token, @RequestBody UserDto dto) {
        return userService.changePassword(null, dto, token);
    }

    @PatchMapping(value = "external/users/request/recover/password/{username}")
    @ApiOperation(value = "Recupera la contraseña de un usuario en base de datos.")
    public boolean requestRecoverPassword(@PathVariable String username) {
        return userService.requestChangePassword(username);
    }

    @PatchMapping(value = "internal/users/change/password")
    @ApiOperation(value = "Actualiza la contraseña de un usuario en base de datos.")
    public boolean updatePassword(@RequestHeader HttpHeaders headers, @RequestBody UserDto dto) {
        return userService.changePassword(headers, dto, null);
    }

}
