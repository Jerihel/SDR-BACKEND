package com.enactusumg.sdr.services;



import com.enactusumg.sdr.dto.RequestEnacterDto;
import com.enactusumg.sdr.dto.RequestEnacterQuery;
import com.enactusumg.sdr.models.Request;
import com.enactusumg.sdr.models.UserRequest;
import com.enactusumg.sdr.repositories.RequestRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;


@Service
public class RequestService {
    @Autowired
    RequestRepository requestRpstry;
@Autowired
   UserRequestService  requestUsr;

    Logger logger = LoggerFactory.getLogger(RequestService.class);
    private Request request;
    private Request request1;


    @Transactional
    public Request saveRequest(RequestEnacterDto dto){
logger.info("creando solicitud");
Request request = Request.saveRequest(dto.getRequest());

   Request request1=   requestRpstry.save(request);

        UserRequest subRequest= requestUsr.saveUserRequest(dto.getUserRequest(),request1.getIdRequest());

if(subRequest==null){

    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Error al crear la solicitud");
}
        return request1;
    }

    @Transactional(readOnly = true)
    public  Request getRequest(Integer id){
      if(!requestRpstry.existsById(id)){

          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Error. No existe la solicitud");

      }
Optional<Request> request = requestRpstry.findById(id);
      if(!request.isPresent()){

          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Error. No existe informacion para esta consulta");

      }

      return request.get();
    }

    public RequestEnacterQuery getRequestEnacterQuery(Integer id){
        RequestEnacterQuery requestEnacterDto = new RequestEnacterQuery();

        Request request= this.getRequest(id);
        UserRequest userRequest= requestUsr.getUserRequest(id);
        requestEnacterDto.setRequest(request);
        requestEnacterDto.setUserRequest(userRequest);

return requestEnacterDto;
    }

    @Transactional
    public Request updateRequest(Request dto){

        if (!requestRpstry.existsById(dto.getIdRequest())) {
            logger.error("No existe la solicitud");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe esta solicitud");

        }
        logger.info("actualizando solicitud");
        return requestRpstry.save(dto);
    }
}
