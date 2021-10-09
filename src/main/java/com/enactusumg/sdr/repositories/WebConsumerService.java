package com.enactusumg.sdr.repositories;

import com.enactusumg.sdr.utils.HeaderUtils;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WebConsumerService {
    @Value("${enactus.email.url}")
    private String baseUrl;
    @Value("${enactus.email.user}")
    private String user;
    @Value("${enactus.email.password}")
    private String password;

    public <T> T consume(@Nullable Object param, @NotNull String url, @NotNull Class<T> klass, MediaType contentType, @NotNull HttpMethod type) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = HeaderUtils.createHeaders(user, password);
        headers.setContentType(contentType);
        headers.add("Accept", "application/json;charset=UTF-8");
        String ruta = baseUrl + url;
        log.info(ruta);
        HttpEntity<?> requestBody = new HttpEntity<>(param, headers);
        return restTemplate.exchange(ruta, type, requestBody, klass).getBody();
    }
}
