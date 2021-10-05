package com.enactusumg.sdr.services;

import com.enactusumg.sdr.dto.EmailBodyDto;
import com.enactusumg.sdr.models.User;
import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
@Slf4j
@Transactional
public class EmailService {

    @Value("${enactus.email.no-reply}")
    private String noReply;
    @Value("${enactus.email.send-grid}")
    private String sendGridKey;

    /**
     * Este método se encarga de enviar un correo electronico en base a `dto` {@link EmailBodyDto}.
     *
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     * @param dto Es la estructura del correo necesaria para poder realizar el correcto envio del mismo al receptor.
     * @throws IOException En caso de no poder encontrar una de las propiedades asignadas.
     * @return Respuesta {@link Response} con los parametros que indican si el correo fue enviado correctamente o no.
     * */
    public Response sendEmail(@RequestBody EmailBodyDto dto) throws IOException {
        Content content = new Content("text/html", dto.getContent());
        Mail mail = new Mail(
                new Email(noReply),
                dto.getSubject(),
                new Email(dto.getEmail()),
                content
        );

        SendGrid sg = new SendGrid(sendGridKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return sg.api(request);
    }
}
