/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.dto.EntrepreneurRequestDto;
import com.enactusumg.sdr.dto.UserRequestDto;
import com.enactusumg.sdr.models.EntrepreneurRequest;
import com.enactusumg.sdr.services.EntrepreneurRequestService;
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
public class EntrepreneurRequestController {
     Logger logger = LoggerFactory.getLogger(EntrepreneurRequestController.class);
    @Autowired
    EntrepreneurRequestService claseService;


    @PostMapping(value = "external/save/entrepreneurRequest")
    @ApiOperation(value = "crea una nueva solicitud y guarda la informacion")
    public EntrepreneurRequest saveEntrepreneurRequest(@RequestBody EntrepreneurRequestDto dto, Integer idEntrepreneurReqeust) {
        return claseService.saveEntrepreneurRequest(dto, idEntrepreneurReqeust);
    }
    
}
