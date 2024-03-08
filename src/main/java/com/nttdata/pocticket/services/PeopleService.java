package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.repositories.PeopleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle operations related to people.
 */
@Service
public class PeopleService {
    @Autowired
    private PeopleRepository peopleRepository;

    /**
     * Retrieves all people.
     * @return List of all people.
     */
    public List<People> getAllPeople(){
        if (peopleRepository == null){
            throw new IllegalArgumentException("PeopleRepository cannot be null");
        }
        try {
            List<People> people = peopleRepository.findAll();
            if (people.isEmpty()) {
                throw new IllegalArgumentException("People cannot be empty");
            }
            return people;
        }catch (DataAccessException e){
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves a person by ID.
     * @param id The ID of the person to retrieve.
     * @return An Optional containing the person if found.
     */
    public Optional<People> getPeopleById(Long id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return peopleRepository.findById(id);
    }
    public List<People> getPeopleByName(String name){
        if (StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("The name cannot be empty");
        }
        return peopleRepository.findByName(name);
    }

    /**
     * Creates a new person.
     * @param people The person to be created.
     * @return The newly created person.
     */
    public People createPeople(People people){
        if (people == null){
            throw new IllegalArgumentException("People cannot be null");
        }
        if (people.getName() == null || people.getName().isEmpty()){
            throw new IllegalArgumentException("People name is required");
        }
        return peopleRepository.save(people);
    }

    /**
     * Updates an existing person.
     * @param updatePeople The person with updated data.
     * @return The updated person.
     * @throws EntityNotFoundException If the person is not found.
     */
    public People updatePeople(People updatePeople){
        if (updatePeople == null){
            throw new IllegalArgumentException("Update cannot be null");
        }
        Optional<People> existingPeople = peopleRepository.findById(updatePeople.getId());

        if (existingPeople.isEmpty()) {
            throw new EntityNotFoundException("People with id " + updatePeople.getId() + " not found!");
        }
            People peopleToUpdate = existingPeople.get();
            peopleToUpdate.setName(updatePeople.getName());
            peopleToUpdate.setEmail(updatePeople.getEmail());

            return peopleRepository.save(peopleToUpdate);
    }

    /**
     * Deletes a person by ID.
     * @param id The ID of the person to be deleted.
     */
    public void deletePeople(Long id){
        if (id == null || id <= 0){
            throw new IllegalArgumentException("Invalid id people");
        }
        peopleRepository.deleteById(id);
    }
}
