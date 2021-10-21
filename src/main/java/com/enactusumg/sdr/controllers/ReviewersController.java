package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.projections.StateReviewerProjection;
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


    @GetMapping(value = "external/stateReviewer/{id}")
    @ApiOperation(value = "Retorna el estado del revisor.")
    public List<StateReviewerProjection> getState(@PathVariable int id) {
        return reviewersService.getStateReviewer(id);
    }

    @GetMapping(value = "external/countRequestReviewer/{user}")
    @ApiOperation(value = "Retorna solicitudes por revisor en estado analisis de la solicitud.")
    public List<BigInteger> getCountRequestReviewer(@PathVariable String user) {
        return reviewersService.getCountRequest(user);
    }

}
