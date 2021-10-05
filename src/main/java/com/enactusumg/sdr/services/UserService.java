package com.enactusumg.sdr.services;

import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    public UserRepository userRepository;

    /**
     * Este metodo de solo lectura (no se adhiere a una transacción existente) encargado de consultar todos los usuarios
     * registrados en base de datos.
     *
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     * @return Lista de {@link User} registrados en la base de datos.
     * */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
