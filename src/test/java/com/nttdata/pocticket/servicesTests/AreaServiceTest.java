package com.nttdata.pocticket.servicesTests;

import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.repositories.AreaRepository;
import com.nttdata.pocticket.services.AreaService;
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
public class AreaServiceTest {
    @Mock
    private AreaRepository areaRepository;

    @InjectMocks
    private AreaService areaService;


    @Test
    public void getAllAreasTest(){
        List<Area> mockAreas = new ArrayList<>();
        mockAreas.add(new Area());
        mockAreas.add(new Area());
        when(areaRepository.findAll()).thenReturn(mockAreas);

        List<Area> areas = areaService.getAllAreas();

        assertEquals(2, areas.size());
        verify(areaRepository, times(1)).findAll();
    }

    @Test
    public void getAreaByIdTest(){
        Long id = 1L;
        Area mockArea = new Area(id, "Area name", "Area description", new ArrayList<>());
        when(areaRepository.findById(id)).thenReturn(Optional.of(mockArea));

        Optional<Area> area = areaService.getAreaById(id);

        assertTrue(area.isPresent());
        assertEquals(mockArea, area.get());
        verify(areaRepository, times(1)).findById(id);
    }

    @Test
    public void createAreaTest(){
        Area areaToCreate = new Area();
        areaToCreate.setName("Area name");

        when(areaRepository.save(any(Area.class))).thenReturn(areaToCreate);

        Area createdArea = areaService.createArea(areaToCreate);

        assertEquals(areaToCreate, createdArea);
        verify(areaRepository,times(1)).save(areaToCreate);
    }

    @Test
    public void updateAreaTest(){
        Long existingAreaId = 1L;
        Area updatedArea = new Area(existingAreaId, "Update name", "UpdateDescription", new ArrayList<>());
        when(areaRepository.findById(existingAreaId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, ()->{
            areaService.updateArea(existingAreaId,updatedArea);
        });

        String expectedMessage = "Area with ID " + existingAreaId + " not found!";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        verify(areaRepository, never()).save(any(Area.class));
    }

    @Test
    public void deleteAreaTest(){
        Long existingId = 1L;
        areaService.deleteArea(existingId);
        verify(areaRepository, times(1)).deleteById(existingId);
    }
}
