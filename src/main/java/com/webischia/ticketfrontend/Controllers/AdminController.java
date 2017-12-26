package com.webischia.ticketfrontend.Controllers;

import com.webischia.ticketfrontend.Domains.NewTicketDTO;
import com.webischia.ticketfrontend.Domains.Ticket;
import com.webischia.ticketfrontend.Domains.User;
import com.webischia.ticketfrontend.Domains.UserToken;
import com.webischia.ticketfrontend.Services.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {

    private final ApiService apiService;

    public AdminController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping({"/admin","/admin/"})
    private String adminDash(HttpServletRequest request, Model model)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Admin")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            model.addAttribute("user",UserInfo);
            List<User> ticketList = apiService.getUsers(UserInfo.getToken().getAccess_token());
            model.addAttribute("tickets",ticketList);
            return "management/index";
        }
        return "redirect:/index";

    }
    @RequestMapping("/admin/create")
    private String userCreate(HttpServletRequest request, Model model)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Client")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            String title="",user="",msg="";
            model.addAttribute("user",UserInfo);
            model.addAttribute("userz", new User());
            return "admin/create";
        }
        return "redirect:/index";

    }

    @RequestMapping("/admin/show/{id}")
    private String userCreate(HttpServletRequest request, Model model, @PathVariable int id)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Client")) {
            //System.out.println(UserInfo.getToken().getAccess_token()); this way faster than debugging
            String title="",user="",msg="";
            User exist = apiService.getUser(UserInfo.getToken().getAccess_token(),id);
            model.addAttribute("user",UserInfo);
            model.addAttribute("userz",exist);
            return "admin/create";
        }
        return "redirect:/index";

    }
    @PostMapping
    @RequestMapping("/admin/add")
    private String userAdd(HttpServletRequest request, @ModelAttribute User user)
    {
        UserToken UserInfo = (UserToken)request.getSession().getAttribute("userinfo");
        if(UserInfo != null && UserInfo.getAccess().equals("Admin")) {
            apiService.register(user.getName(),user.getSurname(),user.getEmail(),user.getPassword());
            return "redirect:/admin";

        }
        return "redirect:/index";
    }
}
