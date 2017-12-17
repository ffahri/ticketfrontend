package com.webischia.ticketfrontend.Services;

import com.webischia.ticketfrontend.Domains.ListTickets;
import com.webischia.ticketfrontend.Domains.Ticket;
import com.webischia.ticketfrontend.Domains.Token;
import com.webischia.ticketfrontend.Domains.UserToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService{

    private RestTemplate restTemplate;


    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<Ticket> getTickets(String token) {

        String url="http://94.177.170.47:8080/api/v1/tickets";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(url);

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

    @Override
    public UserToken loginUser(String uname, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Basic QXBpU2VydmVyOlhZN2ttem9OemwxMDA=");
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", uname);
        map.add("grant_type","password");
        map.add("password",password);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString("http://94.177.170.47:8080/oauth/token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        Token token = restTemplate.postForObject(uriBuilder.toUriString(),request,Token.class);
        UserToken userToken = new UserToken();
        userToken.setUsername(uname);
        userToken.setPassword(password);
        userToken.setToken(token);
        return userToken;
    }
}

