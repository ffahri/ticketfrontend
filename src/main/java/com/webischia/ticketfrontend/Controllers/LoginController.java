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

@Controller
@SessionAttributes("UserInfo")
public class LoginController {

    private final ApiService apiService;
    DefaultListableBeanFactory beanFactory =
            new DefaultListableBeanFactory();
    public LoginController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping("/user")
    private String userDash(@ModelAttribute("UserInfo") UserToken UserInfo)
    {

        if(UserInfo != null) {
            System.out.println(UserInfo.getToken().getAccess_token());
            return "/user/index";
        }
        return "redirect:/index";

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
    private ModelAndView getLogin(@ModelAttribute UserToken user)
    {
        //ShoppingCart cart = (ShoppingCart)request.getSession().setAttribute("cart",value);
        //UserToken test = (UserToken)request.getSession().setAttribute()
        System.out.println(user.getPassword());
        UserToken userToken = apiService.loginUser(user.getUsername(),user.getPassword());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("UserInfo", userToken);
        //modelAndView.setViewName("single-field-page");
        return modelAndView;
        //return "redirect:/user";
    }
}