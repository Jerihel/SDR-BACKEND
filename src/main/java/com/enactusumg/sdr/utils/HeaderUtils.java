package com.enactusumg.sdr.utils;

import org.springframework.http.HttpHeaders;
import java.nio.charset.Charset;
import java.util.Base64;

public class HeaderUtils {

    public static HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }
}
