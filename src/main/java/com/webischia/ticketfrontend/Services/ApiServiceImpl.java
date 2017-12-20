package com.webischia.ticketfrontend.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.webischia.ticketfrontend.Domains.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      //  System.out.println(restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListTickets.class).getStatusCode());

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


    @Override
    public List<Ticket> userGetOwnTickets(String token,String username) {
        String url="http://94.177.170.47:8080/api/v1/tickets/user/"+username;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
/*
        ListTickets liste = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListTickets.class).getBody();
*/

        List<Ticket> liste = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListTickets.class).getBody().getTickets();
       // System.out.println(restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListTickets.class).getStatusCode());

        return liste;
    }

    @Override
    public void userCreateTicket(String token,String username,String title,String message,Boolean status) {

        String url="http://94.177.170.47:8080/api/v1/tickets/user/"+username;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(url);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("ticketTitle",title);
        postMap.put("status",status);
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(postMap, headers);
        int id= restTemplate.postForObject(uriBuilder.toUriString(), request, Ticket.class).getId();
        this.userCreateMessage(token,username,message,id);

    }

    @Override
    public void userCreateMessage(String token, String username,String message, int id) {
        String url="http://94.177.170.47:8080/api/v1/messages/"+username+"/"+id+"/new";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(url);
        //System.out.println("buradayım");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("messageContext",message);
        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(postMap, headers);
        restTemplate.postForObject(uriBuilder.toUriString(), request, Messages.class);
    }

    @Override
    public Ticket showMyTicket(String token, int id) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsByUsername(String token, String username) {
        return null;
    }

    @Override
    public List<Messages> userGetOwnMessagesByTicketId(String token, String username, int id) {
        String url="http://94.177.170.47:8080/api/v1/messages/"+username+"/"+id;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        List<Messages> liste = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListMessages.class).getBody().getMessages();
        //System.out.println(restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET,entity,ListTickets.class).getStatusCode());
       // System.out.println(liste.get(0).getUserMessage().getName());
        return liste;
    }
}

