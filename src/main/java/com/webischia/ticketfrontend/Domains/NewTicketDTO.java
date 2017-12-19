package com.webischia.ticketfrontend.Domains;

import lombok.Data;

@Data
public class NewTicketDTO {
    private String title;
    private String messageContext;
    private int id;//hem msg hem ticket ortak interface
}
