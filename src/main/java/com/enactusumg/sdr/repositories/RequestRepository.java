package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RequestRepository extends CrudRepository<Request, Integer> {


    @Override
    List<Request> findAll();
    
    @Query(value = "select * from enactus_sreg.request where status not in ('finalizado')",
            nativeQuery = true
    )
    List<Request> findAllRequest();
    
    @Query(value = "select * from enactus_sreg.request where status = 'Análisis de la Solicitud'",
            nativeQuery = true
    )
    List<Request> findAllRequestStatus();
}
