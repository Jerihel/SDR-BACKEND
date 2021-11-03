
package com.enactusumg.sdr.services;

import com.enactusumg.sdr.dto.EntrepreneurRequestDto;
import com.enactusumg.sdr.models.EntrepreneurRequest;
import com.enactusumg.sdr.repositories.EntrepreneurRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrepreneurRequestService {
    
    Logger logger = LoggerFactory.getLogger(EntrepreneurRequestService.class);
    @Autowired
    EntrepreneurRequestRepository entrepreneurRequestRepository;
    
    @Transactional(rollbackFor = {Exception.class})
    public EntrepreneurRequest saveEntrepreneurRequest(EntrepreneurRequestDto dto, Integer idRequest) {
        EntrepreneurRequest entrepreneurRequest = EntrepreneurRequest.saveEntrepreneurRequest(dto, idRequest);
        logger.info("creando solicitud");

        return  entrepreneurRequestRepository.save(entrepreneurRequest);
    }
    
}
