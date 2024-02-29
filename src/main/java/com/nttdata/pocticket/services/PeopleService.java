package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.repositories.PeopleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return peopleRepository.findAll();
    }

    /**
     * Retrieves a person by ID.
     * @param id The ID of the person to retrieve.
     * @return An Optional containing the person if found.
     */
    public Optional<People> getPeopleById(Long id){
        return peopleRepository.findById(id);
    }

    /**
     * Creates a new person.
     * @param people The person to be created.
     * @return The newly created person.
     */
    public People createPeople(People people){
        return peopleRepository.save(people);
    }

    /**
     * Updates an existing person.
     * @param id The ID of the person to be updated.
     * @param updatePeople The person with updated data.
     * @return The updated person.
     * @throws EntityNotFoundException If the person is not found.
     */
    public People updatePeople(Long id, People updatePeople){
        Optional<People> existingPeople = peopleRepository.findById(id);
        if (existingPeople.isPresent()){
            People peopleToUpdate = existingPeople.get();
            peopleToUpdate.setName(updatePeople.getName());
            peopleToUpdate.setEmail(updatePeople.getEmail());
            return peopleRepository.save(peopleToUpdate);
        }else {
            throw new EntityNotFoundException("People with id " + id + " not found!");
        }
    }

    /**
     * Deletes a person by ID.
     * @param id The ID of the person to be deleted.
     */
    public void deletePeople(Long id){
        peopleRepository.deleteById(id);
    }
}
