package com.nttdata.pocticket.controller;

import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private PeopleService peopleService;

    @GetMapping
    public List<People> getAllPeople(){
        return peopleService.getAllPeople();
    }

    @GetMapping("/{id}")
    public People getPeopleById(@PathVariable Long id){
        return peopleService.getPeopleById(id).orElse(null);
    }

    @PostMapping
    public People createPeople(@RequestBody People people){
        return peopleService.createPeople(people);
    }

    @PutMapping("/{id}")
    public People updatePeople(@RequestBody People people){
        return peopleService.updatePeople(people);
    }

    @DeleteMapping("/{id}")
    public void deletePeople(@PathVariable Long id){
        peopleService.deletePeople(id);
    }
}
