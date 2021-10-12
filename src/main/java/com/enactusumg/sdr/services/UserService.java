package com.enactusumg.sdr.services;

import com.enactusumg.sdr.dto.CreateUserDto;
import com.enactusumg.sdr.dto.UserCreatedDto;
import com.enactusumg.sdr.dto.UserDto;
import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.models.UserRole;
import com.enactusumg.sdr.repositories.UserRepository;
import com.enactusumg.sdr.repositories.UserRoleRepository;
import com.enactusumg.sdr.repositories.WebConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserRoleRepository userRoleRepository;

    @Autowired
    public WebConsumerService webConsumerService;

    /**
     * Este metodo es de solo lectura (no se adhiere a una transacción existente) encargado de consultar todos los usuarios
     * registrados en base de datos.
     *
     * @return Lista de {@link User} registrados en la base de datos.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Este metodo es de solo lectura (no se adhiere a una transacción existente) encargado de consultar todos los usuarios
     * registrados en base de datos.
     *
     * @return Usuario {@link User} registrado en la base de datos.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Transactional(readOnly = true)
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")
        );
    }

    /**
     * Este metodo es escritura (se adhiere a una transacción existente) encargado de consultar todos los usuarios
     * registrados en base de datos.
     *
     * @return Usuario {@link User} registrado en la base de datos.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Transactional
    public UserCreatedDto createUser(CreateUserDto dto) {
        final User user = User.fromDto(dto);
        final String login = generateEmail(dto);
        final UserCreatedDto userCreatedDto = new UserCreatedDto(login, login + "@enactusumg.com", generatePassword());

        user.setIdUser(login);
        user.setEmail(userCreatedDto.getEmail());
        user.setPassword(BCrypt.hashpw(userCreatedDto.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);

        dto.getRoles().forEach(role -> {
            userRoleRepository.save(new UserRole(role, login));
        });

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", userCreatedDto.getEmail());
        map.add("password", userCreatedDto.getPassword());
        final String resp = webConsumerService.consume(
                map,
                "/admin/mail/users/add",
                String.class,
                MediaType.APPLICATION_FORM_URLENCODED,
                HttpMethod.POST
        );
        System.out.println(resp);
        return userCreatedDto;
    }

    /**
     * Este metodo es de solo lectura (no se adhiere a una transacción existente) se encarga de consultar todos los roles
     * de un usuario registrados en base de datos.
     *
     * @return Lista de {@link UserRole} registrados en la base de datos.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Transactional(readOnly = true)
    public List<UserRole> getRolesByUser(String userId) {
        return userRoleRepository.findByIdUser(userId);
    }


    /**
     * Este metodo es de solo lectura (no se adhiere a una transacción existente) se encarga de consultar todos los roles
     * de un usuario registrados en base de datos.
     *
     * @return Lista de {@link UserRole} registrados en la base de datos.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Transactional
    public boolean changePassword(UserDto dto) {
        final User user = userRepository.findById(dto.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")
        );

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", user.getEmail());
        map.add("password", dto.getPassword());

        final String resp = webConsumerService.consume(
                map,
                "/admin/mail/users/password",
                String.class,
                MediaType.APPLICATION_FORM_URLENCODED,
                HttpMethod.POST
        );
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        return resp.equalsIgnoreCase("OK");
    }

    private String generateEmail(CreateUserDto dto) {
        final String[] nameParts = dto.getName().split(" ");
        final String names = nameParts.length == 1 ? String.valueOf(nameParts[0].charAt(0)) : Arrays.stream(nameParts).reduce((s, s2) -> String.valueOf(s.charAt(0)) + s2.charAt(0)).orElse("");
        final String lastNames = Arrays.stream(dto.getLastName().split(" ")).reduce((s, s2) -> s + s2.charAt(0)).orElse("");

        return (names + lastNames + getMagicNumber(new Date())).toLowerCase(Locale.ROOT);
    }

    private String generatePassword() {
        return Long.toString(System.currentTimeMillis() * 2, 36) + "$";
    }

    private int getMagicNumber(Date date) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date).replaceAll("-", "");

        while (dateStr.length() > 1) {
            dateStr = Arrays.stream(dateStr.split("")).reduce((s, s2) -> String.valueOf(Integer.parseInt(s) + Integer.parseInt(s2))).orElse("");
        }
        return Integer.parseInt(dateStr);
    }
}
