package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.CatalogueChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CatalogueChildRepository extends CrudRepository<CatalogueChild, Integer> {


    boolean existsByIdCatalogue(Integer idCatalogue);

    @Override
    List<CatalogueChild> findAll();


    List<CatalogueChild> findAllByIdCatalogue(Integer idCatalogue);
}
