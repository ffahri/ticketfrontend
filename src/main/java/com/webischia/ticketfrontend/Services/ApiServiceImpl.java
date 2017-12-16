package com.webischia.ticketfrontend.Services;

import com.webischia.ticketfrontend.Domains.ListTickets;
import com.webischia.ticketfrontend.Domains.Tickets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public List<Tickets> getTickets(String token) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(api_url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+token);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ListTickets tickets = restTemplate.postForObject(uriBuilder.toUriString(),entity,ListTickets.class);


        return tickets.getTicketsList();

    }
}
