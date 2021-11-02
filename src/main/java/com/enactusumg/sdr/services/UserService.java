package com.enactusumg.sdr.services;

import com.enactusumg.sdr.config.security.JwtUtil;
import com.enactusumg.sdr.dto.*;
import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.models.UserRole;
import com.enactusumg.sdr.projections.UserDetailProjection;
import com.enactusumg.sdr.repositories.UserRepository;
import com.enactusumg.sdr.repositories.UserRoleRepository;
import com.enactusumg.sdr.repositories.WebConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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
    public List<UserDetailProjection> getAllUsers(HttpHeaders headers) {
        final String username = JwtUtil.parseToken(headers.getFirst("Authorization"));
        if (!userRoleRepository.existsByIdUserAndIdRole(username, 4)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tiene los permisos para poder realizar esta acción.");
        }
        return userRepository.findAllPopulated();
    }

    /**
     * Este metodo es de solo lectura (no se adhiere a una transacción existente) encargado de consultar todos los usuarios
     * registrados en base de datos.
     *
     * @return Usuario {@link User} registrado en la base de datos.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(String userId) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

        return UserProfileDto.builder()
                .username(user.getIdUser())
                .name(user.getName())
                .lastName(user.getLastName())
                .roles(getRolesByUser(user.getIdUser()))
                .email(user.getEmail())
                .state(user.getState())
                .build();
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
    public UserCreatedDto createUser(HttpHeaders headers, CreateUserDto dto) {
        final String username = JwtUtil.parseToken(headers.getFirst("Authorization"));
        if (!userRoleRepository.existsByIdUserAndIdRole(username, 4)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tiene los permisos para poder realizar esta acción.");
        }
        final User user = User.fromDto(dto);
        final String login = generateEmail(dto);
        final UserCreatedDto userCreatedDto = new UserCreatedDto(login, login + "@enactusumg.com", generatePassword());

        user.setIdUser(login);
        user.setEmail(userCreatedDto.getEmail());
        user.setPassword(BCrypt.hashpw(userCreatedDto.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);

        dto.getRoles().forEach(role -> userRoleRepository.save(new UserRole(role, login)));

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
        final EmailBodyDto bodyDto = new EmailBodyDto();
        bodyDto.setEmail(userCreatedDto.getEmail());
        bodyDto.setTemplateId("d-a56dad7fb12048c8a080762f441261fd");

        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName() + " " + user.getLastName());
        params.put("username", user.getIdUser());
        params.put("password", userCreatedDto.getPassword());

        try {
            emailService.sendEmail(bodyDto, params);
        } catch (IOException e) {
        }
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
    public boolean changePassword(@Nullable HttpHeaders headers, ChangePassDto dto) {
        final EmailBodyDto bodyDto = new EmailBodyDto();

        if (headers != null) {
            if (!dto.getUsername().equalsIgnoreCase(JwtUtil.parseToken(headers.getFirst("Authorization")))) {
                revokeToken(headers);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tiene los permisos para poder realizar esta accion.");
            }
        }

        final User user = userRepository.findById(dto.getUsername()).orElseThrow(() -> {
                    revokeToken(headers);
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tiene los permisos para poder realizar esta accion.");
                }
        );

        if (!BCrypt.checkpw(dto.getOldPass(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "La contraseña ingresada es incorrecta.");
        }

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", user.getEmail());
        map.add("password", dto.getNewPass());

        final String resp = webConsumerService.consume(
                map,
                "/admin/mail/users/password",
                String.class,
                MediaType.APPLICATION_FORM_URLENCODED,
                HttpMethod.POST
        );
        user.setPassword(BCrypt.hashpw(dto.getNewPass(), BCrypt.gensalt()));

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

        final User user = userRepository.findById(Arrays.stream(username.split("@")).findFirst().orElse(username)).orElseThrow(
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
            log.info("Codigo: " + token);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserLoggedDto authUser(UserDto dto) {
        final String msg = "Credenciales invalidas. Por favor, revise que el usuario y la contraseña sean los correctos.";
        final User user = userRepository.findById(dto.getUsername().replace("@enactusumg.com", "")).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, msg)
        );
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, msg);
        }

        user.setToken(JwtUtil.generateToken(dto.getUsername()));

        return UserLoggedDto.builder()
                .username(user.getIdUser())
                .token(user.getToken())
                .roles(getRolesByUser(user.getIdUser()))
                .build();
    }

    @Transactional
    public UserLoggedDto refreshToken(HttpHeaders headers) {
        final User user = getTokenByHeader(headers);
        user.setToken(JwtUtil.generateToken(user.getIdUser()));
        return UserLoggedDto.builder()
                .username(user.getIdUser())
                .token(user.getToken())
                .roles(getRolesByUser(user.getIdUser()))
                .build();
    }

    @Transactional
    public void revokeToken(HttpHeaders headers) {
        final User user = getTokenByHeader(headers);
        user.setToken(null);
    }

    /**
     * Este metodo es de solo lectura (no se adhiere a una transacción existente) encargado de actualizar un usuario
     * registrado en base de datos.
     *
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    @Transactional
    public void updateUser(String userId, EditUserDto dto) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")
        );

        user.setState(dto.getState());
        userRoleRepository.deleteByIdUser(userId);
        dto.getRoles().forEach(role -> userRoleRepository.save(new UserRole(role, userId)));
    }

    @Transactional(readOnly = true)
    public boolean validateToken(String userId, String token) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tiene los permisos para poder realizar esta accion.")
        );

        if (!BCrypt.checkpw(token, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "La contraseña ingresada es incorrecta.");
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

    private User getTokenByHeader(HttpHeaders headers) {
        return userRepository.findById(JwtUtil.parseToken(headers.getFirst("Authorization"))).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")
        );
    }

    private User validateToken(HttpHeaders headers, String userId) {
        if (headers != null) {
            final String username = JwtUtil.parseToken(headers.getFirst("Authorization"));
            if (!userId.equalsIgnoreCase(username)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tiene los permisos para poder realizar esta acción.");
            }
        }

        return userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.")
        );
    }
}
