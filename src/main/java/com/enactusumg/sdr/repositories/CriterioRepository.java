package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.CriterionEvaluation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CriterioRepository extends CrudRepository<CriterionEvaluation,Integer> {

    @Override
    List<CriterionEvaluation> findAll();
}
