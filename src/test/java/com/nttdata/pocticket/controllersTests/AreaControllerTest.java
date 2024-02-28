package com.nttdata.pocticket.controllersTests;

import com.nttdata.pocticket.controller.AreaController;
import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.services.AreaService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AreaControllerTest {
    @Mock
    private AreaService areaService;
    @InjectMocks
    private AreaController areaController;

    @Test
    public void getAllAreasTest(){
        List<Area> mockAreas = Arrays.asList(new Area(), new Area());
        when(areaService.getAllAreas()).thenReturn(mockAreas);

        List<Area> allAreas = areaController.getAllAreas();

        assertEquals(mockAreas, allAreas);
        verify(areaService).getAllAreas();
    }

    @Test
    public void getAreaByIdTest(){
        Long id = 1L;
        Area mockArea = new Area();
        mockArea.setIdArea(id);
        mockArea.setName("Test Area");
        when(areaService.getAreaById(id)).thenReturn(Optional.of(mockArea));

        Area areaById = areaController.getAreaById(id);

        assertEquals(mockArea, areaById);
        verify(areaService).getAreaById(id);
    }

    @Test
    public void createAreaTest(){
        Area areaToCreate = new Area();
        Area createdArea = new Area(1L, "New Area", "Description", new ArrayList<>());
        when(areaService.createArea(areaToCreate)).thenReturn(createdArea);

        Area returnedArea = areaController.createArea(areaToCreate);

        assertEquals(createdArea, returnedArea);
        verify(areaService).createArea(areaToCreate);
    }
}
