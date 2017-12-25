package com.webischia.ticketfrontend.Controllers;

import com.webischia.ticketfrontend.Domains.Token;
import com.webischia.ticketfrontend.Domains.User;
import com.webischia.ticketfrontend.Domains.UserToken;
import com.webischia.ticketfrontend.Services.ApiService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @RequestMapping({"/register","/register/"})
    private String register(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }
    @RequestMapping({"/login","/login/"})
    private String login(Model model)
    {
            model.addAttribute("user", new UserToken());
            return "login";
    }
    @PostMapping
    @RequestMapping("/register/try")
    private String getRegister(@Valid @ModelAttribute User user, HttpServletRequest request , BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
            throw new RuntimeException("HATALI VERİ GİRİLDİ");
        //ShoppingCart cart = (ShoppingCart)request.getSession().setAttribute("cart",value);
        //UserToken test = (UserToken)request.getSession().setAttribute()
        //System.out.println(user.getPassword());
        apiService.register(user.getName(),user.getSurname(),user.getEmail(),user.getPassword());

        return "redirect:/index";

    }
    @PostMapping
    @RequestMapping("/login/try")
    private String getLogin(@ModelAttribute UserToken user,HttpServletRequest request)
    {
        //ShoppingCart cart = (ShoppingCart)request.getSession().setAttribute("cart",value);
        //UserToken test = (UserToken)request.getSession().setAttribute()
        //System.out.println(user.getPassword());
        UserToken userToken = apiService.loginUser(user.getUsername(),user.getPassword());

        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile("(?<=\\[).+?(?=\\])")
                .matcher(Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary("TWFZemtTam1relBDNTdM"))
                        .parseClaimsJws(userToken.getToken().getAccess_token()).toString());
        while (m.find()) {
            allMatches.add(m.group());
        }
        userToken.setAccess(allMatches.get(2));
        if(allMatches.get(2).equals("Client")) {
            userToken.setAccess_id(1);
            request.getSession().setAttribute("userinfo",userToken); //ALL HAIL THE HTTPSESSION \v/
            return "redirect:/user";
        }
        else if(allMatches.get(2).equals("Employee")) {
            userToken.setAccess_id(2);
            request.getSession().setAttribute("userinfo",userToken); //ALL HAIL THE HTTPSESSION \v/
            return "redirect:/management";

        }
        else if(allMatches.get(2).equals("Admin")) {
            userToken.setAccess_id(3);
            request.getSession().setAttribute("userinfo",userToken); //ALL HAIL THE HTTPSESSION \v/
            return "redirect:/admin";
        }
        //request.getSession().setAttribute("userinfo",userToken); //ALL HAIL THE HTTPSESSION \v/
        return "redirect:index";

    }

    @RequestMapping("/logout")
    private String logout(HttpServletRequest request,Model model)
    {
        request.getSession().invalidate();
        model.addAttribute("status","logout yaptınız");//todo güzel index sayfası
        return "redirect:/index";
    }
}