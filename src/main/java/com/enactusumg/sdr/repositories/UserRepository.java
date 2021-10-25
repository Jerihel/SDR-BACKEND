package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.User;
import com.sun.istack.NotNull;
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
}
