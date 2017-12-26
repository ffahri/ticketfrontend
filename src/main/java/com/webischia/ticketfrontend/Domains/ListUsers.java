package com.webischia.ticketfrontend.Domains;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListUsers implements Serializable {

    List<User> userList;

}
