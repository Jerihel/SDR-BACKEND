package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RequestRepository extends CrudRepository<Request, Integer> {


    @Override
    List<Request> findAll();
    
    @Query(value = "select * from enactus_sreg.request where state not in (9)",
            nativeQuery = true
    )
    List<Request> findAllRequest();
    
    @Query(value = "select * from enactus_sreg.request where state = 5",
            nativeQuery = true
    )
    List<Request> findAllRequestStatus();
}
