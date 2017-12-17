package com.webischia.ticketfrontend.Domains;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListMessages implements Serializable {

    List<Messages> messages;

}
