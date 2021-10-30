package com.enactusumg.sdr.services;

import com.enactusumg.sdr.dto.CriterionDto;
import com.enactusumg.sdr.dto.CriterionUpdatDto;
import com.enactusumg.sdr.models.CriterionEvaluation;
import com.enactusumg.sdr.projections.CriterionEvalutionProjection;
import com.enactusumg.sdr.repositories.CriterioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CriterionService {
    @Autowired
    CriterioRepository criterioRps;

    Logger logger = LoggerFactory.getLogger(CriterionService.class);


    public CriterionEvaluation createCriterio(CriterionDto dto) {
        logger.info("creando crieterio");

        if (criterioRps.existsByNombreCriterio(dto.getNombreCriterio())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Ya existe un criterio con el nobmre: " + dto.getNombreCriterio());


        }

        CriterionEvaluation criterio = CriterionEvaluation.createCriterion(dto);

        return criterioRps.save(criterio);
    }

    @Transactional
    public CriterionEvaluation upDateCriterio(CriterionUpdatDto dto) {
        if (!criterioRps.existsById(dto.getNoCriterio())) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. No existe el criterio a Actualizar");

        }


        logger.info("actualizando criterio");


        Optional<CriterionEvaluation> criterionEvaluation = criterioRps.findById(dto.getNoCriterio());

        CriterionEvaluation criterio = criterionEvaluation.get();
logger.info("numero a actualizar" + dto.getEstadoColaborador());
        criterio.setNombreCriterio(dto.getNombreCriterio());
        criterio.setPonderacion(dto.getPonderacion());
        criterio.setFechaModifica(new Date());
        criterio.setEstado(dto.getEstadoColaborador());
        criterio.setUsuarioModifica(dto.getUsuarioModifica());

        logger.info("datos estado " + criterio.getEstado());
        logger.info("datos criterio " + criterio.toString());
        return criterio;
    }

    public List<CriterionEvalutionProjection> getAllCriterion() {
        logger.info("obteniendo todos los criterios");

        List<CriterionEvalutionProjection> criterios = criterioRps.obtenerCriterionEvaluation();

        if (criterios.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existen datos");
        }

        return criterios;
    }
}
