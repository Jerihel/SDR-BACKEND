package com.enactusumg.sdr.services;

import com.enactusumg.sdr.dto.CreateUserDto;
import com.enactusumg.sdr.dto.EmailBodyDto;
import com.enactusumg.sdr.dto.UserCreatedDto;
import com.enactusumg.sdr.dto.UserDto;
import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.models.UserRole;
import com.enactusumg.sdr.projections.StateReviewerProjection;
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

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    public EmailService emailService;

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
    public boolean changePassword(UserDto dto, String token) {
        final String msg = "El token está vencido o es incorrecto";
        final EmailBodyDto bodyDto = new EmailBodyDto();
        final User user = userRepository.findById(dto.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")
        );

        if(token != null){
            if (!BCrypt.checkpw(token, user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, msg);
            }
        }

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

        bodyDto.setEmail(user.getEmail());
        bodyDto.setTemplateId("d-ab4965009ad64c53a5051e7d128d6aee");

        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName() + " " + user.getLastName());

        try {
            emailService.sendEmail(bodyDto, params);
        } catch (IOException e) {
        }
        return resp.equalsIgnoreCase("OK");
    }

    /**
     * Este metodo es de solo lectura (no se adhiere a una transacción existente) se encarga de consultar todos los roles
     * de un usuario registrados en base de datos.
     *
     * @return Lista de {@link UserRole} registrados en la base de datos.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Transactional
    public boolean requestChangePassword(String username) {
        final StringBuilder token = new StringBuilder();
        final EmailBodyDto bodyDto = new EmailBodyDto();
        final User user = userRepository.findById(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")
        );

        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < 6; i++) {
            int pos = (int) Math.floor((Math.random() * 15));
            token.append(letters[pos]);
        }

        bodyDto.setEmail(user.getEmail());
        bodyDto.setSubject("Reestablecer Contraseña");
        bodyDto.setTemplateId("d-8b9bf15da6bd463991af65619c1b298b");

        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName() + " " + user.getLastName());
        params.put("token", token.toString());

        try {
            emailService.sendEmail(bodyDto, params);
            user.setPassword(BCrypt.hashpw(token.toString(), BCrypt.gensalt()));
        } catch (IOException e) {
            return false;
        }

        return true;
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
    
    @Transactional(readOnly = true)
    public List<StateReviewerProjection> getStateReviewer(int id) {
        List<StateReviewerProjection> state = userRepository.getState(id);
        return state;
    }
        
    @Transactional(readOnly = true)
    public List<BigInteger> getCountRequest(String pUser) {
	List<BigInteger> user = userRepository.getCountRequest(pUser);
	return user;
    }
}
