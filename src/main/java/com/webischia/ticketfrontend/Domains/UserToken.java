package com.webischia.ticketfrontend.Domains;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
public class UserToken {
    private String username;
    private String password;
    private Token token;

}
