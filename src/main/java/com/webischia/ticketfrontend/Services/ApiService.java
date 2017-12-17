package com.webischia.ticketfrontend.Services;

import com.webischia.ticketfrontend.Domains.Messages;
import com.webischia.ticketfrontend.Domains.Ticket;
import com.webischia.ticketfrontend.Domains.UserToken;

import java.util.List;

public interface ApiService {

    //Login
    UserToken loginUser(String uname,String password);

    //User - Ticket
    List<Ticket> userGetOwnTickets(String token,String username);
    void userCreateTicket(String token,String username,String title,String message,Boolean status);
    Ticket showMyTicket(String token,int id);

    //User-Messages
    List<Messages> userGetOwnMessagesByTicketId(String token, String username , int id);
    void userCreateMessage(String token, String username,String message, int id);
    //Employee - Ticket
    List<Ticket> getTickets(String token);
    List<Ticket> getTicketsByUsername(String token,String username);


}
