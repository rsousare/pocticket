package com.nttdata.pocticket.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("message", "Hello, User!");
        return "home";
    }

    @GetMapping("/menu")
    public String getMenuPage(){
        return "menu";
    }

    @GetMapping("/area")
    public String getAreaPage(){ return "area";}

    @GetMapping("/person")
    public String getPeoplePage(){ return "person"; }

    @GetMapping("/project")
    public String getProjectPage(){ return "project"; }

    @GetMapping("/ticket")
    public String getTicketPage(){ return "ticket"; }

}
