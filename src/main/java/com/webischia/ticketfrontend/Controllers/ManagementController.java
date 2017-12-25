package com.webischia.ticketfrontend.Controllers;

import com.webischia.ticketfrontend.Domains.Messages;
import com.webischia.ticketfrontend.Domains.NewTicketDTO;
import com.webischia.ticketfrontend.Domains.Ticket;
import com.webischia.ticketfrontend.Domains.UserToken;
import com.webischia.ticketfrontend.Services.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ManagementController {
    private final ApiService apiService;

    public ManagementController(ApiService apiService) {
        this.apiService = apiService;
    }

    //index
    @RequestMapping({"/management","/management/"})
    private String userDash(HttpServletRequest request, Model model)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Employee")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user",UserInfo);
            List<Ticket> ticketList = apiService.getTickets(UserInfo.getToken().getAccess_token());
            model.addAttribute("tickets",ticketList);
            return "management/index";
        }
        return "redirect:/index";

    }

    @RequestMapping("/management/show/{id}")
    private String userShow(HttpServletRequest request, Model model, @PathVariable int id)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Employee")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user",UserInfo);
            List<Messages> messagesList = apiService.userGetOwnMessagesByTicketId(UserInfo.getToken().getAccess_token(),UserInfo.getUsername(),id);
            model.addAttribute("msg",messagesList);
            Ticket whichone = apiService.showTicket(UserInfo.getToken().getAccess_token(),id);
            model.addAttribute("ticket",whichone);
            NewTicketDTO newID = new NewTicketDTO();
            newID.setId(id);
            model.addAttribute("newmessage", newID);
            return "management/show";
        }
        return "redirect:/index";

    }

    @RequestMapping("/management/search")
    private String managementSearch(HttpServletRequest request, Model model, @Valid @ModelAttribute NewTicketDTO newTicketDTO , BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
            return "redirect:/management/search";
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Employee")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user",UserInfo);
            //List<Messages> ticketList = apiService.userGetOwnMessagesByTicketId(UserInfo.getToken().getAccess_token(),UserInfo.getUsername());
            //  model.addAttribute("msg",ticketList);
            model.addAttribute("user",UserInfo);
            model.addAttribute("newticket",new NewTicketDTO());
            List<Ticket> ticketList = apiService.searchTickets(UserInfo.getToken().getAccess_token(),newTicketDTO.getMessageContext());
            model.addAttribute("tickets",ticketList);
            NewTicketDTO newID = new NewTicketDTO();
            return "management/search";
        }
        return "redirect:/index";

    }

    //@PostMapping
    @RequestMapping(value="/management/add/message", method= RequestMethod.POST, params="action=answer")
    private String createMessage(@ModelAttribute UserToken user, HttpServletRequest request, Model model , @ModelAttribute NewTicketDTO newTicketDTO) {
        UserToken UserInfo = (UserToken) request.getSession().getAttribute("userinfo");
        if (UserInfo != null && UserInfo.getAccess().equals("Employee")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user", UserInfo);

            apiService.userCreateMessage(UserInfo.getToken().getAccess_token(),UserInfo.getUsername(),newTicketDTO.getMessageContext(),newTicketDTO.getId());
            return "redirect:/management/show/"+newTicketDTO.getId();

        }
        return "redirect:/index";


    }
    @RequestMapping(value="/management/add/message", method= RequestMethod.POST, params="action=close")
    private String closeTicket(@ModelAttribute UserToken user, HttpServletRequest request, Model model , @ModelAttribute NewTicketDTO newTicketDTO) {
        UserToken UserInfo = (UserToken) request.getSession().getAttribute("userinfo");
        if (UserInfo != null && UserInfo.getAccess().equals("Employee")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user", UserInfo);

           apiService.closeTicketEmployee(UserInfo.getToken().getAccess_token(),UserInfo.getUsername(),newTicketDTO.getId());
            return "redirect:/management";

        }
        return "redirect:/index";


    }
    @RequestMapping(value="/management/add/message", method= RequestMethod.POST, params="action=delete")
    private String deleteTicket(@ModelAttribute UserToken user, HttpServletRequest request, Model model , @ModelAttribute NewTicketDTO newTicketDTO) {
        UserToken UserInfo = (UserToken) request.getSession().getAttribute("userinfo");
        if (UserInfo != null && UserInfo.getAccess().equals("Employee")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user", UserInfo);
            apiService.deleteTicket(UserInfo.getToken().getAccess_token(),newTicketDTO.getId());
            return "redirect:/management";

        }
        return "redirect:/index";


    }

}
