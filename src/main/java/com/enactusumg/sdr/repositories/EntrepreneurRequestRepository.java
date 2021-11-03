/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.CriterionEvaluation;
import com.enactusumg.sdr.models.EntrepreneurRequest;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author pdmelend
 */
public interface EntrepreneurRequestRepository extends CrudRepository<EntrepreneurRequest, Integer>{
    
    @Override
    List<EntrepreneurRequest> findAll();
}
