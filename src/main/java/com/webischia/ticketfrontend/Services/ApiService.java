package com.webischia.ticketfrontend.Services;

import com.webischia.ticketfrontend.Domains.Ticket;

import java.util.List;

public interface ApiService {

    List<Ticket> getTickets(String token);
}
