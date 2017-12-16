package com.webischia.ticketfrontend.Services;

import com.webischia.ticketfrontend.Domains.Tickets;

import java.util.List;

public interface ApiService {

    List<Tickets> getTickets();
}
