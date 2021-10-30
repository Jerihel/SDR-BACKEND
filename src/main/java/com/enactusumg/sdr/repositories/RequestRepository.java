package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.Request;
import com.enactusumg.sdr.projections.SolicitudesAsignables;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

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



    @Query(value=("select new com.enactusumg.sdr.projections.SolicitudesAsignables( r.idRequest,  r.name,  u.name, c.name) from Request r join CatalogueChild  c on  c.idCatalogueChild=r.state " +
            "join User u on u.idUser=r.idReviwer where r.state not in(11) "),nativeQuery = false)
    List<SolicitudesAsignables> getAllSolicitudesReasignacion();

}
