package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.models.Request;
import com.enactusumg.sdr.services.ReviewersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Api
@RestController
@Slf4j
public class ReviewersController {

    @Autowired
    public ReviewersService reviewersService;
    


    @GetMapping(value = "external/stateReviewer/{pUser}")
    @ApiOperation(value = "Retorna el estado del revisor.")
    public List<BigInteger> getState(@PathVariable String pUser) {
        return reviewersService.getStateReviewer(pUser);
    }

    @GetMapping(value = "external/countRequestReviewer/{pUser}")
    @ApiOperation(value = "Retorna solicitudes por revisor en estado analisis de la solicitud.")
    public List<BigInteger> getCountRequestReviewer(@PathVariable String pUser) {
        return reviewersService.getCountRequest(pUser);
    }
    
     @GetMapping("external/obtenerRequest")
    @ApiOperation(value="Obtiene listado de todas las solicitudes", notes="Retorna todas las solicitudes")
    public List<Request> obtenerRequest() {
        List<Request> obtenerRequest = reviewersService.obtenerRequest();
        return obtenerRequest;
    }

}
