package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.repositories.PeopleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {
    @Autowired
    private PeopleRepository peopleRepository;

    public List<People> getAllPeople(){
        return peopleRepository.findAll();
    }

    public Optional<People> getPeopleById(Long id){
        return peopleRepository.findById(id);
    }

    public People createPeople(People people){
        return peopleRepository.save(people);
    }

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

    public void deletePeople(Long id){
        peopleRepository.deleteById(id);
    }
}
