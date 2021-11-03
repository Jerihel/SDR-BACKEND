package com.enactusumg.sdr.services;


import com.enactusumg.sdr.dto.EmailBodyDto;
import com.enactusumg.sdr.dto.RequestEnacterDto;
import com.enactusumg.sdr.dto.RequestEnacterQuery;
import com.enactusumg.sdr.dto.RequestEntrepreneurDto;
import com.enactusumg.sdr.models.EntrepreneurRequest;
import com.enactusumg.sdr.models.Request;
import com.enactusumg.sdr.models.UserRequest;
import com.enactusumg.sdr.projections.SolicitudesAsignables;
import com.enactusumg.sdr.repositories.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class RequestService {
    @Autowired
    RequestRepository requestRpstry;
    @Autowired
    UserRequestService requestUsr;
    @Autowired
    EmailService emailService;

    Logger logger = LoggerFactory.getLogger(RequestService.class);

    @Transactional
    public Request saveRequest(RequestEnacterDto dto) {
        logger.info("creando solicitud");
        Request request = Request.saveRequest(dto.getRequest());
        Request request1 = requestRpstry.save(request);
        UserRequest subRequest = requestUsr.saveUserRequest(dto.getUserRequest(), request1.getIdRequest());

        if (subRequest == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al crear la solicitud");
        }
        return request1;
    }

    @Transactional(readOnly = true)
    public Request getRequest(Integer id) {
        if (!requestRpstry.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. No existe la solicitud");
        }
        Optional<Request> request = requestRpstry.findById(id);
        if (!request.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. No existe informacion para esta consulta");
        }

        return request.get();
    }

    public RequestEnacterQuery getRequestEnacterQuery(Integer id) {
        RequestEnacterQuery requestEnacterDto = new RequestEnacterQuery();

        Request request = this.getRequest(id);
        UserRequest userRequest = requestUsr.getUserRequest(id);
        requestEnacterDto.setRequest(request);
        requestEnacterDto.setUserRequest(userRequest);

        return requestEnacterDto;
    }

    @Transactional
    public Request updateRequest(Request dto) {

        if (!requestRpstry.existsById(dto.getIdRequest())) {
            logger.error("No existe la solicitud");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe esta solicitud");

        }
        logger.info("actualizando solicitud");
        return requestRpstry.save(dto);
    }


    @Transactional(readOnly = true)
    public List<Request> getAllRequest() {
        logger.debug("Consultado todas las solicitudes con estado distinto a finalizado");
        return requestRpstry.findAllRequest();
    }

    @Transactional(readOnly = true)
    public List<Request> getAllRequestStatus() {
        logger.debug("Consultado todas las solicitudes con estado Analisis de la Solicitud");
        return requestRpstry.findAllRequestStatus();
    }

    @Transactional(readOnly = true)
    public List<SolicitudesAsignables> getSolicitudesReasignacion() {
        logger.info("recuperando solicitudes asignables ");

        List<SolicitudesAsignables> solicitudes = requestRpstry.getAllSolicitudesReasignacion();

        if (solicitudes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existen solicitudes ");
        }

        return solicitudes;
    }

    @Transactional
    public void reasignarSolicitudes(List<Integer> ids) {
        requestRpstry.findAllById(ids).forEach(request -> {
            request.setState(9);
        });
    }


    @Transactional
    public void saveRequestEntrepreneur(RequestEntrepreneurDto dto) {
        logger.info("creando solicitud");
        Request request = Request.saveRequest(dto.getRequest());

        Request request2 = requestRpstry.save(request);

        EntrepreneurRequest subRequest = EntrepreneurRequest.saveEntrepreneurRequest(dto.getEntrepreneurRequest(), request2.getIdRequest());

        final EmailBodyDto bodyDto = new EmailBodyDto();
        bodyDto.setEmail(dto.getRequest().getEmail());
        bodyDto.setTemplateId("d-789ea15a857049c7acb8decca6a77a4c");

        Map<String, String> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("phone", request.getTelephone());
        params.put("email", request.getEmail());
        params.put("detail", subRequest.getDetails());

        try {
            emailService.sendEmail(bodyDto, params);
        } catch (IOException e) {
        }
    }

    @Transactional(readOnly = true)
    public Request getRequestEnd(Integer id) {
        if (!requestRpstry.existsById(id)) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. No existe la solicitud");

        }
        Optional<Request> request = requestRpstry.findById(id);
        if (!request.isPresent()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. No existe informacion para esta consulta");

        }

        return request.get();
    }
}
