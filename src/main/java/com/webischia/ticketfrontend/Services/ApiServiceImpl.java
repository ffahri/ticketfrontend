package com.webischia.ticketfrontend.Services;

import com.webischia.ticketfrontend.Domains.ListTickets;
import com.webischia.ticketfrontend.Domains.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService{

    private RestTemplate restTemplate;

    private final String api_url;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String api_url) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }

    @Override
    public List<Ticket> getTickets(String token) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(api_url);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);

        HttpEntity entity = new HttpEntity(headers);
/*
        ListTickets liste = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListTickets.class).getBody();
*/

        List<Ticket> liste = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListTickets.class).getBody().getTickets();
        System.out.println(restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListTickets.class).getStatusCode());

        return liste;

    }
}
