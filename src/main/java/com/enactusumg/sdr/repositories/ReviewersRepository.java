/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.projections.StateReviewerProjection;
import java.math.BigInteger;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewersRepository extends CrudRepository<User, String> {

    @Override
    List<User> findAll();
    
        
    @Query(value = "select state as \"state\" from users where idUser=:pId",
            nativeQuery = true
    )
    List<StateReviewerProjection> getState(@Param("pId") int pId);
    
    @Query(value = "select count(noCriterio) as \"cantidad\" from criterion_evaluation where usuarioAgrega=:pUser",
            nativeQuery = true
    )
    List<BigInteger> getCountRequest(@Param("pUser") String pUser);
    
}
