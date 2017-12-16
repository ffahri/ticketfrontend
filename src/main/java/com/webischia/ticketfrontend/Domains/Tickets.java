package com.webischia.ticketfrontend.Domains;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Data
public class Tickets {

    private Integer id;
    private String ticketTitle;
    private User userTicket;
    private Boolean status;
    private Date creationDate;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
