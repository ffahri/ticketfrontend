package com.webischia.ticketfrontend.Domains;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;

}
