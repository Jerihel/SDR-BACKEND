package com.enactusumg.sdr.services;

import com.enactusumg.sdr.dto.UserRequestDto;
import com.enactusumg.sdr.models.Request;
import com.enactusumg.sdr.models.UserRequest;
import com.enactusumg.sdr.repositories.UserRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserRequestService {
    Logger logger = LoggerFactory.getLogger(UserRequestService.class);
    @Autowired
    UserRequestRepository userRepository;

    @Transactional(rollbackFor = {Exception.class})
    public UserRequest saveUserRequest(UserRequestDto dto,Integer idRequest) {
        UserRequest userRequest = UserRequest.saveUserRequest(dto,idRequest);
        logger.info("creando solicitud");

        return  userRepository.save(userRequest);
    }

    @Transactional(readOnly = true)
    public UserRequest getUserRequest(Integer id){

      if(!userRepository.existsById(id)){

         throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Error. No existe la solicitud");
      }

      return userRepository.findById(id).get();
    }

}
