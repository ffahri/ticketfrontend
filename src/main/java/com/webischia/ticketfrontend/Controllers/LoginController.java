package com.webischia.ticketfrontend.Controllers;

import com.webischia.ticketfrontend.Domains.Token;
import com.webischia.ticketfrontend.Domains.UserToken;
import com.webischia.ticketfrontend.Services.ApiService;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final ApiService apiService;
    DefaultListableBeanFactory beanFactory =
            new DefaultListableBeanFactory();
    public LoginController(ApiService apiService) {
        this.apiService = apiService;
    }


    @RequestMapping({"","/","/index"})
    private String getIndex()
    {
        return "index";
    }

    @RequestMapping({"/login","/login/"})
    private String login(Model model)
    {
            model.addAttribute("user", new UserToken());
            return "/login";
    }
    @PostMapping
    @RequestMapping("/login/try")
    private String getLogin(@ModelAttribute UserToken user,HttpServletRequest request)
    {
        //ShoppingCart cart = (ShoppingCart)request.getSession().setAttribute("cart",value);
        //UserToken test = (UserToken)request.getSession().setAttribute()
        //System.out.println(user.getPassword());
        UserToken userToken = apiService.loginUser(user.getUsername(),user.getPassword());
        request.getSession().setAttribute("userinfo",userToken); //ALL HAIL THE HTTPSESSION \v/

        return "redirect:/user";
    }

    @RequestMapping("/logout")
    private String logout(HttpServletRequest request,Model model)
    {
        request.getSession().invalidate();
        model.addAttribute("status","logout yaptınız");//todo güzel index sayfası
        return "redirect:/index";
    }
}