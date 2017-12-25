package com.webischia.ticketfrontend.Domains;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class User {

    private int id;
    @NotBlank
    private String name;
    private String surname;
    private String email;
    @NotBlank
    private String password;

}
