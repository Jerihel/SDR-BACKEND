package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.projections.StateReviewerProjection;
import java.math.BigInteger;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, String> {

    /**
     * Este metodo se encarga de realizar una consulta a la base de datos y retornar todos los usuarios registrados en
     * la misma.
     *
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     * @return Lista de {@link User} registrados en la base de datos.
     * */
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
