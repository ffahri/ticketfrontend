package com.webischia.ticketfrontend.Domains;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Data
public class Token {

    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String scope;
    private String jti;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}