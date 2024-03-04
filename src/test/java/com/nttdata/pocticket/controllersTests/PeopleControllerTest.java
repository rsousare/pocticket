package com.nttdata.pocticket.controllersTests;

import com.nttdata.pocticket.controller.PeopleController;
import com.nttdata.pocticket.model.dto.PeopleDTO;
import com.nttdata.pocticket.model.entity.People;
import com.nttdata.pocticket.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PeopleController peopleController;

    @Test
    public void getAllPeopleTest(){
        when(modelMapper.map(any(), eq(PeopleDTO.class))).thenAnswer(invocationOnMock -> {
            People source = invocationOnMock.getArgument(0);
            return new PeopleDTO(source.getId(), source.getName(), source.getEmail());
        });

        People person1 = new People(1L, "Ricardo", "aa@sapo.pt", new ArrayList<>());
        People person2 = new People(2L, "Maria", "mm@sapo.pt", new ArrayList<>());
        List<People> mockPeople = Arrays.asList(person1, person2);
        when(peopleService.getAllPeople()).thenReturn(mockPeople);

        List<PeopleDTO> allPeople = peopleController.getAllPeople();

        verify(peopleService).getAllPeople();

        assertEquals(2, allPeople.size());
        assertEquals("Ricardo", allPeople.get(0).getName());
        assertEquals("mm@sapo.pt", allPeople.get(1).getEmail());

    }

    @Test
    public void getPeopleByIdTest(){
        Long id = 1L;
        People mockPeople = new People();
        mockPeople.setId(id);
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
        when(peopleService.updatePeople(existingPeople)).thenReturn(existingPeople);

        People updatedPeople = peopleController.updatePeople(existingPeople);

        assertEquals(existingPeople, updatedPeople);
        verify(peopleService).updatePeople(existingPeople);
    }

    @Test
    public void deletePeopleTest(){
        Long id = 1L;
        peopleController.deletePeople(id);
        verify(peopleService).deletePeople(id);
    }
}
