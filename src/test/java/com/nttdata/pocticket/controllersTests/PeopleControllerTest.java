package com.nttdata.pocticket.controllersTests;

import com.nttdata.pocticket.controller.PeopleController;
import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleControllerTest {
    @Mock
    private PeopleService peopleService;

    @InjectMocks
    private PeopleController peopleController;

    @Test
    public void getAllPeopleTest(){
        List<People> mockPeople = Arrays.asList(new People(), new People());
        when(peopleService.getAllPeople()).thenReturn(mockPeople);

        List<People> allPeople = peopleController.getAllPeople();

        assertEquals(mockPeople, allPeople);
        verify(peopleService).getAllPeople();
    }

    @Test
    public void getPeopleByIdTest(){
        Long id = 1L;
        People mockPeople = new People();
        mockPeople.setIdPeople(id);
        mockPeople.setName("People name");
        when(peopleService.getPeopleById(id)).thenReturn(Optional.of(mockPeople));

        People peopleById = peopleController.getPeopleById(id);

        assertEquals(mockPeople, peopleById);
        verify(peopleService).getPeopleById(id);
    }

    @Test
    public void createPeopleTest(){
        People peopleToCreate = new People();
        People createdPeople = new People(1L, "New Person", "cc@gmail.com", new ArrayList<>());
        when(peopleService.createPeople(peopleToCreate)).thenReturn(createdPeople);

        People returnedPeople = peopleController.createPeople(peopleToCreate);

        assertEquals(createdPeople, returnedPeople);
        verify(peopleService).createPeople(peopleToCreate);
    }

    @Test
    public void updatePeopleTest(){
        Long id = 1L;
        People existingPeople = mock(People.class);
        when(peopleService.updatePeople(id, existingPeople)).thenReturn(existingPeople);

        People updatedPeople = peopleController.updatePeople(id, existingPeople);

        assertEquals(existingPeople, updatedPeople);
        verify(peopleService).updatePeople(id, existingPeople);
    }

    @Test
    public void deletePeopleTest(){
        Long id = 1L;
        peopleController.deletePeople(id);
        verify(peopleService).deletePeople(id);
    }
}
