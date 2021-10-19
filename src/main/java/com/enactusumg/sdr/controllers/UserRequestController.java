package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.dto.UserRequestDto;
import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.models.UserRequest;
import com.enactusumg.sdr.services.UserRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class UserRequestController {
    Logger logger = LoggerFactory.getLogger(UserRequestController.class);
    @Autowired
    UserRequestService userService;


    @PostMapping(value = "/external/save/userRequest")
    @ApiOperation(value = "crea una nueva solicitud y guarda la informacion segun el tipo de solicitud")
    public UserRequest saveRequest(@RequestBody UserRequestDto dto, int idRequest) {
        return userService.saveUserRequest(dto, idRequest);
    }
}
