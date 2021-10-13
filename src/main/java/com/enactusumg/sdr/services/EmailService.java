package com.enactusumg.sdr.services;

import com.enactusumg.sdr.dto.EmailBodyDto;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

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
     * @param dto Es la estructura del correo necesaria para poder realizar el correcto envio del mismo al receptor.
     * @return Respuesta {@link Response} con los parametros que indican si el correo fue enviado correctamente o no.
     * @throws IOException En caso de no poder encontrar una de las propiedades asignadas.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    public Response sendEmail(EmailBodyDto dto) throws IOException {
        final Content content = new Content("text/html", dto.getContent());
        final Mail mail = new Mail(
                new Email(noReply, "Enactus UMG"),
                dto.getSubject(),
                new Email(dto.getEmail()),
                content
        );

        return sendEmailInternal(mail);
    }

    /**
     * Este método se encarga de enviar un correo electronico en base a `dto` {@link EmailBodyDto} con una plantilla
     * de sendgrid.
     *
     * @param dto    Es la estructura del correo necesaria para poder realizar el correcto envio del mismo al receptor.
     * @param params Son los parametros que se envian a la plantilla de sendgrid
     * @return Respuesta {@link Response} con los parametros que indican si el correo fue enviado correctamente o no.
     * @throws IOException En caso de no poder encontrar una de las propiedades asignadas.
     * @author Carlos Ramos (cramosl3@miumg.edu.gt)
     */
    public Response sendEmail(EmailBodyDto dto, Map<String, String> params) throws IOException {
        final Mail mail = new Mail();
        final Personalization personalization = new Personalization();

        mail.setFrom(new Email(noReply, "Enactus UMG"));
        mail.setTemplateId(dto.getTemplateId());
        personalization.addTo(new Email(dto.getEmail()));
        params.forEach(personalization::addDynamicTemplateData);

        mail.addPersonalization(personalization);
        return sendEmailInternal(mail);
    }

    private Response sendEmailInternal(Mail mail) throws IOException {
        SendGrid sg = new SendGrid(sendGridKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return sg.api(request);
    }
}
