package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.UserRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRequestRepository extends CrudRepository<UserRequest,Integer> {

    @Override
    List<UserRequest> findAll();


}
