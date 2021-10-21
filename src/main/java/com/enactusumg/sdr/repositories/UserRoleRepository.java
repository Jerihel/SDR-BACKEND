package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.models.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {

    /**
     * Este metodo se encarga de realizar una consulta a la base de datos y retornar todos los roles de usuarios
     * registrados en la misma.
     *
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     * @return Lista de {@link User} registrados en la base de datos.
     * */
    @Override
    List<UserRole> findAll();

    List<UserRole> findByIdUser(String s);
}
