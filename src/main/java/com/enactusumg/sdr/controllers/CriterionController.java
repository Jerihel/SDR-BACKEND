package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.dto.CriterionDto;
import com.enactusumg.sdr.dto.CriterionUpdatDto;
import com.enactusumg.sdr.models.CriterionEvaluation;
import com.enactusumg.sdr.services.CriterionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
public class CriterionController {
    @Autowired
    CriterionService criterionService;

    @PostMapping(value = "internal/create/criterion")
    @ApiOperation(value = "crea un nuevo criterio segun los parametros enviados")
    public CriterionEvaluation createCriterion(@RequestBody CriterionDto dto) {


        return criterionService.createCriterio(dto);
    }

@PatchMapping(value = "internal/update/criterion")
    @ApiOperation(value = "actualiza el criterio segun la informacion proporcionada")
    public CriterionEvaluation updateCriterion(@RequestBody CriterionUpdatDto dto){

        return criterionService.upDateCriterio(dto);

}
}
