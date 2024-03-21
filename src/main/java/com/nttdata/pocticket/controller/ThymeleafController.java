package com.nttdata.pocticket.controller;


import com.nttdata.pocticket.services.PeopleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ThymeleafController {

    @Autowired
    private PeopleService peopleService;


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request, Model model){

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (peopleService.isValidUser(username, password)){
            request.getSession().setAttribute("username", username);
            return "redirect:/home";
        }else {
            model.addAttribute("error", "Invalid username ou password");
            return "login";
        }
    }

//    @GetMapping("/home")
//    public String home(Model model){
//        model.addAttribute("message", "Hello, Welcome!");
//        return "home";
//    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model){
        if (request.getSession().getAttribute("username") == null){
            return "redirect:/login";
        }else {
            model.addAttribute("message", "Hello, Welcome!");
            return "home";
        }
    }

    @GetMapping("/menu")
    public String getMenuPage(HttpServletRequest request){
        if (request.getSession().getAttribute("username") == null){
            return "redirect:/login";
        }else {
            return "menu";
        }
    }

    @GetMapping("/area")
    public String getAreaPage(HttpServletRequest request){
        if (request.getSession().getAttribute("username") == null){
            return "redirect:/login";
        }else {
            return "area";
        }
    }


    @GetMapping("/person")
    public String getPeoplePage(HttpServletRequest request){
        if (request.getSession().getAttribute("username") == null){
            return "redirect:/login";
        }else {
            return "person";
        }
    }


    @GetMapping("/project")
    public String getProjectPage(HttpServletRequest request){
        if (request.getSession().getAttribute("username") == null){
            return "redirect:/login";
        }else {
            return "project";
        }
    }


    @GetMapping("/ticket")
    public String getTicketPage(HttpServletRequest request){
        if (request.getSession().getAttribute("username") == null){
            return "redirect:/login";
        }else {
            return "ticket";
        }
    }
}
