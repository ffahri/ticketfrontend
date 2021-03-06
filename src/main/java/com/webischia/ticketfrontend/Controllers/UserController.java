package com.webischia.ticketfrontend.Controllers;

import com.webischia.ticketfrontend.Domains.Messages;
import com.webischia.ticketfrontend.Domains.NewTicketDTO;
import com.webischia.ticketfrontend.Domains.Ticket;
import com.webischia.ticketfrontend.Domains.UserToken;
import com.webischia.ticketfrontend.Services.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private final ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping("/user")
    private String userDash(HttpServletRequest request, Model model)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Client")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user",UserInfo);
            List<Ticket> ticketList = apiService.userGetOwnTickets(UserInfo.getToken().getAccess_token(),UserInfo.getUsername());
            model.addAttribute("tickets",ticketList);
            return "user/index";
        }
        return "redirect:/index";

    }

    @RequestMapping("/user/create")
    private String userCreate(HttpServletRequest request, Model model)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Client")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            String title="",user="",msg="";
            model.addAttribute("user",UserInfo);
            model.addAttribute("newticket",new NewTicketDTO());
            return "user/create";
        }
        return "redirect:/index";

    }

    @PostMapping
    @RequestMapping("/user/add")
    private String userAdd(HttpServletRequest request, Model model, @ModelAttribute NewTicketDTO newTicketDTO)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Client")) {
            System.out.println(UserInfo.getToken().getAccess_token());
            model.addAttribute("user",UserInfo);
            Boolean status = true;
            //System.out.println(title+"  "+msg );
            apiService.userCreateTicket(UserInfo.getToken().getAccess_token(),UserInfo.getUsername(),newTicketDTO.getTitle(),newTicketDTO.getMessageContext(),status);
            return "redirect:/user";

        }
        return "redirect:/index";
    }
    @PostMapping
    @RequestMapping("/user/add/message")
    private String createMessage(@ModelAttribute UserToken user,HttpServletRequest request,Model model ,@ModelAttribute NewTicketDTO newTicketDTO) {
        UserToken UserInfo = (UserToken) request.getSession().getAttribute("userinfo");
        if (UserInfo != null && UserInfo.getAccess().equals("Client")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user", UserInfo);

            apiService.userCreateMessage(UserInfo.getToken().getAccess_token(),UserInfo.getUsername(),newTicketDTO.getMessageContext(),newTicketDTO.getId());
            return "redirect:/user/show/"+newTicketDTO.getId();

        }
        return "redirect:/index";


    }
    @RequestMapping("/user/show/{id}")
    private String userShow(HttpServletRequest request, Model model, @PathVariable int id)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Client")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user",UserInfo);
            List<Messages> ticketList = apiService.userGetOwnMessagesByTicketId(UserInfo.getToken().getAccess_token(),UserInfo.getUsername(),id);
            model.addAttribute("msg",ticketList);
            Ticket whichone = apiService.showMyTicket(UserInfo.getToken().getAccess_token(),id,UserInfo.getUsername());
            model.addAttribute("ticket",whichone);
            NewTicketDTO newID = new NewTicketDTO();
            newID.setId(id);
            model.addAttribute("newmessage", newID);
            return "user/show";
        }
        return "redirect:/index";

    }
    @RequestMapping("/user/search")
    private String userSearch(HttpServletRequest request, Model model, @Valid @ModelAttribute NewTicketDTO newTicketDTO, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
            return "redirect:/user/search";
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Client")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user",UserInfo);
            //List<Messages> ticketList = apiService.userGetOwnMessagesByTicketId(UserInfo.getToken().getAccess_token(),UserInfo.getUsername());
          //  model.addAttribute("msg",ticketList);
            model.addAttribute("user",UserInfo);
            model.addAttribute("newticket",new NewTicketDTO());
            List<Ticket> ticketList = apiService.userSearchTickets(UserInfo.getToken().getAccess_token(),UserInfo.getUsername(),newTicketDTO.getMessageContext());
            model.addAttribute("tickets",ticketList);
            NewTicketDTO newID = new NewTicketDTO();
            return "user/search";
        }
        return "redirect:/index";

    }
}
