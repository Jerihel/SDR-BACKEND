package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.dto.*;
import com.enactusumg.sdr.projections.UserDetailProjection;
import com.enactusumg.sdr.services.EmailService;
import com.enactusumg.sdr.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api
@RestController
@Slf4j
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public EmailService emailService;

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
    public List<UserDetailProjection> getAllUser(@RequestHeader HttpHeaders headers) {
        return userService.getAllUsers(headers);
    }

    @GetMapping(value = "internal/users/find/{userId}")
    @ApiOperation(value = "Retorna todos los usuarios registrados en el sistema.")
    public UserProfileDto getUser(@PathVariable String userId) {
        return userService.getUserProfile(userId);
    }

    @PostMapping(value = "internal/users/create")
    @ApiOperation(value = "Crea un registro de usuario en base de datos.")
    public UserCreatedDto createUser(@RequestHeader HttpHeaders headers, @RequestBody CreateUserDto dto) {
        return userService.createUser(headers, dto);
    }

    @PatchMapping(value = "internal/users/edit/{username}")
    @ApiOperation(value = "Actualiza el registro de un usuario en base de datos.")
    public void updateUser(@PathVariable String username, @RequestBody EditUserDto dto) {
        userService.updateUser(username, dto);
    }

    @PostMapping(value = "external/users/login")
    @ApiOperation(value = "Inicia la sesion de un usuario en base a el nombre de usuario y contrase??a.")
    public UserLoggedDto loginUser(@RequestBody UserDto dto) {
        return userService.authUser(dto);
    }

    @PatchMapping(value = "external/users/recover/password/{token}")
    @ApiOperation(value = "Recupera la contrase??a de un usuario en base de datos.")
    public boolean recoverPassword(@PathVariable String token, @RequestBody UserDto dto) {
        final ChangePassDto changePassDto = ChangePassDto.builder()
                .oldPass(token)
                .newPass(dto.getPassword())
                .username(dto.getUsername())
                .build();
        return userService.changePassword(null, changePassDto);
    }

    @PostMapping(value = "external/users/request/recover/password/{username}")
    @ApiOperation(value = "Recupera la contrase??a de un usuario en base de datos.")
    public boolean requestRecoverPassword(@PathVariable String username) {
        return userService.requestChangePassword(username);
    }

    @PostMapping(value = "external/users/request/recover/validate/{username}/{token}")
    @ApiOperation(value = "Recupera la contrase??a de un usuario en base de datos.")
    public boolean validateToken(@PathVariable String username, @PathVariable String token) {
        return userService.validateToken(username, token);
    }

    @PatchMapping(value = "external/users/change/password")
    @ApiOperation(value = "Actualiza la contrase??a de un usuario en base de datos.")
    public boolean updatePassword(@RequestHeader HttpHeaders headers, @RequestBody ChangePassDto dto) {
        return userService.changePassword(headers, dto);
    }

    @PostMapping(value = "external/users/support")
    @ApiOperation(value = "Actualiza la contrase??a de un usuario en base de datos.")
    public boolean support(@RequestBody SupportDto dto) {
        final EmailBodyDto bodyDto = new EmailBodyDto();
        bodyDto.setEmail("soporte@enactusumg.com");
        bodyDto.setTemplateId("d-68ca7caef99a4a98b0bbb4c7bcd07420");

        Map<String, String> params = new HashMap<>();
        params.put("name", dto.getName());
        params.put("email", dto.getEmail());
        params.put("subject", dto.getSubject());
        params.put("message", dto.getMessage());

        try {
            emailService.sendEmail(bodyDto, params);
            bodyDto.setTemplateId("d-afee2707acf644d09fac9d2a746fca7e");
            bodyDto.setEmail(dto.getEmail());
            emailService.sendEmail(bodyDto, params);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
