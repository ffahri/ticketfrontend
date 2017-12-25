package com.webischia.ticketfrontend.Domains;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NewTicketDTO {
    private String title;
    @Size(min = 1)
    private String messageContext;
    private int id;//hem msg hem ticket ortak interface
}
