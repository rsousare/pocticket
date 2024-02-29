package com.nttdata.pocticket.servicesTests;

import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.repositories.PeopleRepository;
import com.nttdata.pocticket.services.PeopleService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleService peopleService;

    @Test
    public void getAllPeopleTest(){
        List<People> mockPeopleList = new ArrayList<>();
        mockPeopleList.add(new People(1L, "Nome", "email", new ArrayList<>()));
        mockPeopleList.add(new People(2L, "Nome2", "email2", new ArrayList<>()));
        when(peopleRepository.findAll()).thenReturn(mockPeopleList);

        List<People> result = peopleService.getAllPeople();

        assertEquals(mockPeopleList.size(), result.size());
        assertEquals(mockPeopleList, result);
        verify(peopleRepository, times(1)).findAll();
    }

    @Test
    public void getPeopleByIdTest(){
        Long id = 1L;
        People mockPeople = new People(1L, "Nome", "email", new ArrayList<>());
        when(peopleRepository.findById(id)).thenReturn(Optional.of(mockPeople));

        Optional<People> result = peopleService.getPeopleById(id);

        assertTrue(result.isPresent());
        assertEquals(mockPeople, result.get());
        verify(peopleRepository, times(1)).findById(id);
    }

    @Test
    public void createPeopleTest(){
        People mockPeople = new People(1L, "Nome", "email", new ArrayList<>());
        when(peopleRepository.save(any(People.class))).thenReturn(mockPeople);

        People result = peopleService.createPeople(mockPeople);

        assertEquals(mockPeople, result);
        verify(peopleRepository, times(1)).save(mockPeople);
    }

    @Test
    public void updatePeopleTest(){
        Long existingPeopleId = 1L;

        People updetedPeople = new People(existingPeopleId, "Nome1", "email1", new ArrayList<>());

        when(peopleRepository.findById(existingPeopleId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, ()->{
            peopleService.updatePeople(updetedPeople);
        });

        String expectedMessage = "People with id " + existingPeopleId + " not found!";
        String actualMessage = exception.getMessage();


        assertEquals(expectedMessage, actualMessage);

        verify(peopleRepository, never()).save(any(People.class));
    }

    @Test
    public void deletePeopleTest(){
        Long id = 1L;
        peopleService.deletePeople(id);
        verify(peopleRepository, times(1)).deleteById(id);
    }
}
