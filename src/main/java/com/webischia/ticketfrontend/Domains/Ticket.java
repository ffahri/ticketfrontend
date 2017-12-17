package com.webischia.ticketfrontend.Domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Data
public class Ticket implements Serializable {

    private Integer id;
    private String ticketTitle;
    private User userTicket;
    private Boolean status;
    private Date creationDate;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
