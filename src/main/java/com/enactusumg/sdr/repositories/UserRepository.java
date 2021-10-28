package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.projections.UserDetailProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {

    /**
     * Este metodo se encarga de realizar una consulta a la base de datos y retornar todos los usuarios registrados en
     * la misma.
     *
     * @return Lista de {@link User} registrados en la base de datos.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Override
    List<User> findAll();

    @Query(value = "select" +
            " us.id_user as username, (us.name || ' ' || us.last_name) as name, us.email, cc.name state," +
            " (select count(*) from enactus_sreg.request where id_reviwer = us.id_user) workload" +
            " from enactus_sreg.users us" +
            " join enactus_sreg.catalogue_child cc on us.state = cc.id_catalogue_child", nativeQuery = true)
    List<UserDetailProjection> findAllPopulated();
}
