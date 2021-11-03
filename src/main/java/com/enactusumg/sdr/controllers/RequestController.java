package com.enactusumg.sdr.controllers;

import com.enactusumg.sdr.dto.RequestEnacterDto;
import com.enactusumg.sdr.dto.RequestEnacterQuery;
import com.enactusumg.sdr.dto.RequestEntrepreneurDto;
import com.enactusumg.sdr.models.Request;
import com.enactusumg.sdr.projections.SolicitudesAsignables;
import com.enactusumg.sdr.services.RequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
public class RequestController {

    @Autowired
    RequestService requestSrv;

    @GetMapping(value = "/external/get/requestUserEnacters/{id}")
    @ApiOperation(value = "obtiene la informacion de una solicitud")
    public RequestEnacterQuery obtenerValoresRequest(@PathVariable @ApiParam(value = "no de la solicitud") Integer id) {
        return requestSrv.getRequestEnacterQuery(id);
    }

    @PatchMapping(value = "/external/requestUserEnacters/update")
    @ApiOperation(value = "actualiza una solicitud de usuario enacters")
    public Request updateRequestUser(@RequestBody Request dto) {
        return requestSrv.updateRequest(dto);
    }

    @PostMapping(value = "/external/save/requestUserEnacters")
    @ApiOperation(value = "crea una nueva solicitud y guarda la informacion segun el tipo de solicitud")
    public Request saveRequest(@RequestBody RequestEnacterDto dto) {
        return requestSrv.saveRequest(dto);
    }

    @GetMapping(value = "external/get/requestUserEnacters/status")
    @ApiOperation(value = "Retorna todas las solicitudes con estado diferente a finalizado.")
    public List<Request> getRequest() {
        return requestSrv.getAllRequest();
    }

    @GetMapping(value = "external/get/requestUserEnacters/statusAnalysis")
    @ApiOperation(value = "Retorna todas las solicitudes con estado -Analisis de la Solicitud-.")
    public List<Request> getRequestStatus() {
        return requestSrv.getAllRequestStatus();
    }

    @GetMapping(value = "internal/get/requestReasignables")
    @ApiOperation(value = "retorna las solicitudes para su reasignacion")
    public List<SolicitudesAsignables> getSolicitudesResignables() {
        return requestSrv.getSolicitudesReasignacion();
    }

    @PostMapping(value = "internal/reasign/requestReasignables")
    @ApiOperation(value = "reasigna las solicitudes para su reasignacion")
    public void reasignarSolicitudes(@RequestBody List<Integer> idSolicitudes) {
        requestSrv.reasignarSolicitudes(idSolicitudes);
    }

    @PostMapping(value = "/external/save/requestEntrepreneur")
    @ApiOperation(value = "crea una nueva solicitud y guarda la informacion")
    public Request saveRequest(@RequestBody RequestEntrepreneurDto dto) {
        return requestSrv.saveRequestEntrepreneur(dto);
    }
}
