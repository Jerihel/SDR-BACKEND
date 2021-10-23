package com.enactusumg.sdr.services;

import com.enactusumg.sdr.repositories.ReviewersRepository;

import com.enactusumg.sdr.projections.StateReviewerProjection;
import com.enactusumg.sdr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
public class ReviewersService {

    @Autowired
    public ReviewersRepository reviewersRepository;
    
    @Transactional(readOnly = true)
    public List<StateReviewerProjection> getStateReviewer(int id) {
        List<StateReviewerProjection> state = reviewersRepository.getState(id);
        return state;
    }
        
    @Transactional(readOnly = true)
    public List<BigInteger> getCountRequest(String pUser) {
	List<BigInteger> user = reviewersRepository.getCountRequest(pUser);
	return user;
    }
}
