package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Integer> {


    @Override
    List<Request> findAll();
}
