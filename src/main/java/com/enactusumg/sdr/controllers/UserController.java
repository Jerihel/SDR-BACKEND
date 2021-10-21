package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.config.security.JwtUtil;
import com.enactusumg.sdr.dto.CreateUserDto;
import com.enactusumg.sdr.dto.UserCreatedDto;
import com.enactusumg.sdr.dto.UserDto;
import com.enactusumg.sdr.dto.UserLoggedDto;
import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.projections.StateReviewerProjection;
import com.enactusumg.sdr.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "internal/users/find")
    @ApiOperation(value = "Retorna todos los usuarios registrados en el sistema.")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "internal/users/find/{userId}")
    @ApiOperation(value = "Retorna todos los usuarios registrados en el sistema.")
    public User getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PostMapping(value = "internal/users/create")
    @ApiOperation(value = "Crea un registro de usuario en base de datos.")
    public UserCreatedDto createUser(@RequestBody CreateUserDto dto) {
        return userService.createUser(dto);
    }

    @PatchMapping(value = "internal/users/edit")
    @ApiOperation(value = "Actualiza el registro de un usuario en base de datos.")
    public void updateUser() {
    }

    @PostMapping(value = "external/users/login")
    @ApiOperation(value = "Inicia la sesion de un usuario en base a el nombre de usuario y contraseña.")
    public UserLoggedDto loginUser(@RequestBody UserDto dto) {
        final String msg = "Credenciales invalidas. Por favor, revise que el usuario y la contraseña sean los correctos.";
        final User user = userService.getUser(dto.getUsername().replace("@enactusumg.com", ""));
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, msg);
        }
        return UserLoggedDto.builder()
                .username(user.getIdUser())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .token(JwtUtil.generateToken(dto.getUsername()))
                .roles(userService.getRolesByUser(user.getIdUser()))
                .build();
    }

    @PatchMapping(value = "external/users/recover/password/{token}")
    @ApiOperation(value = "Recupera la contraseña de un usuario en base de datos.")
    public boolean recoverPassword(@PathVariable String token, @RequestBody UserDto dto) {
        return userService.changePassword(dto, token);
    }

    @PatchMapping(value = "external/users/request/recover/password/{username}")
    @ApiOperation(value = "Recupera la contraseña de un usuario en base de datos.")
    public boolean requestRecoverPassword(@PathVariable String username) {
        return userService.requestChangePassword(username);
    }

    @PatchMapping(value = "internal/users/change/password")
    @ApiOperation(value = "Actualiza la contraseña de un usuario en base de datos.")
    public boolean updatePassword(@RequestBody UserDto dto) {
        return userService.changePassword(dto, null);
    }
    
    @GetMapping(value = "external/stateReviewer/{id}")
    @ApiOperation(value = "Retorna el estado del revisor.")
    public List<StateReviewerProjection> getState(@PathVariable int id) {
        return userService.getStateReviewer(id);
    }
    
    @GetMapping(value = "external/countRequestReviewer/{user}")
    @ApiOperation(value = "Retorna solicitudes por revisor en estado analisis de la solicitud.")
    public List<BigInteger> getCountRequestReviewer(@PathVariable String user) {
        return userService.getCountRequest(user);
    }
}
