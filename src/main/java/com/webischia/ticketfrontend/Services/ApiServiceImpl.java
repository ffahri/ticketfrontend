package com.webischia.ticketfrontend.Services;

import com.webischia.ticketfrontend.Domains.Tickets;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService{

    @Override
    public List<Tickets> getTickets() {
        return null;
    }
}
