package com.webischia.ticketfrontend.Services;

import com.webischia.ticketfrontend.Domains.Ticket;
import com.webischia.ticketfrontend.Domains.UserToken;

import java.util.List;

public interface ApiService {

    List<Ticket> getTickets(String token);
    UserToken loginUser(String uname,String password);
}
