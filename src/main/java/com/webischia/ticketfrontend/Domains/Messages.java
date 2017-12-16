package com.webischia.ticketfrontend.Domains;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Messages {

    private Integer id;
    private String messageContext;
    private User userMessage;
    private Date creationDate;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
