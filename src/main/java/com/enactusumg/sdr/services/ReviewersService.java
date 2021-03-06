package com.enactusumg.sdr.services;

import com.enactusumg.sdr.models.Request;
import com.enactusumg.sdr.repositories.ReviewersRepository;

import com.enactusumg.sdr.projections.StateReviewerProjection;
import com.enactusumg.sdr.repositories.RequestRepository;
import com.enactusumg.sdr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
public class ReviewersService {

    @Autowired
    public ReviewersRepository reviewersRepository;
    
        @Autowired
    public RequestRepository requestRepository;
    
    @Transactional(readOnly = true)
    public List<BigInteger> getStateReviewer(String pUser) {
        List<BigInteger> state = reviewersRepository.getState(pUser);
        return state;
    }
        
    @Transactional(readOnly = true)
    public List<BigInteger> getCountRequest(String pUser) {
	List<BigInteger> user = reviewersRepository.getCountRequest(pUser);
	return user;
    }
    
    @Transactional(readOnly = true)
    public List<Request> obtenerRequest() {
        List<Request> obtenerRequest = requestRepository.findAll();
        return obtenerRequest;
    }
}
