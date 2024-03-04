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

    /**
     * Retrieves all people.
     * @return A list of all people.
     */
    @GetMapping
    public List<People> getAllPeople(){
        return peopleService.getAllPeople();
    }

    /**
     * Retrieves a person by their ID.
     * @param id The ID of the person to retrieve.
     * @return The person with the specified ID, or null if not found.
     */
    @GetMapping("/{id}")
    public People getPeopleById(@PathVariable Long id){
        return peopleService.getPeopleById(id).orElse(null);
    }

    /**
     * Creates a new person.
     * @param people The person data for creation.
     * @return The newly created person.
     */
    @PostMapping
    public People createPeople(@RequestBody People people){
        return peopleService.createPeople(people);
    }

    /**
     * Updates an existing person.
     * @param people The updated person data.
     * @return The updated person.
     */
    @PutMapping("/{id}")
    public People updatePeople(@RequestBody People people){
        return peopleService.updatePeople(people);
    }

    /**
     * Deletes a person by their ID.
     * @param id The ID of the person to delete.
     */
    @DeleteMapping("/{id}")
    public void deletePeople(@PathVariable Long id){
        peopleService.deletePeople(id);
    }
}
