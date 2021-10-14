package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.CriterionEvaluation;
import com.enactusumg.sdr.projections.CriterionEvalutionProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CriterioRepository extends CrudRepository<CriterionEvaluation,Integer> {

    @Override
    List<CriterionEvaluation> findAll();


    @Transactional(readOnly = true)
    @Query(value = "select new com.enactusumg.sdr.projections.CriterionEvalutionProjection( ce.noCriterio,  ce.nombreCriterio,  ce.ponderacion,  ch.name) from CriterionEvaluation ce join CatalogueChild ch on ch.idCatalogueChild=ce.estado",nativeQuery = false)
    List<CriterionEvalutionProjection> obtenerCriterionEvaluation();
}
