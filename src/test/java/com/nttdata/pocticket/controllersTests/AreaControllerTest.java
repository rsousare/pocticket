package com.nttdata.pocticket.controllersTests;

import com.nttdata.pocticket.controller.AreaController;
import com.nttdata.pocticket.model.dto.AreaDTO;
import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.services.AreaService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AreaControllerTest {
    @Mock
    private AreaService areaService;

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AreaController areaController;

    @Test
    public void getAllAreasTest(){
        when(modelMapper.map(any(), eq(AreaDTO.class))).thenAnswer(invocationOnMock -> {
            Area source = invocationOnMock.getArgument(0);
            return new AreaDTO(source.getId(), source.getName(), source.getDescription());
        });

        Area area1 = new Area(1L, "Area1", "AreaDes", new ArrayList<>());
        Area area2 = new Area(2L, "Area2", "Areades", new ArrayList<>());

        List<Area> mockAreas = Arrays.asList(area1, area2);
        when(areaService.getAllAreas()).thenReturn(mockAreas);

        List<AreaDTO> allAreas = areaController.getAllAreas();
        verify(areaService).getAllAreas();

        assertEquals(2, allAreas.size());
        assertEquals("Area1", allAreas.get(0).getName());
        assertEquals("Areades", allAreas.get(1).getDescription());

    }

    @Test
    public void getAreaByIdTest(){
        Long id = 1L;
        Area mockArea = new Area();
        mockArea.setId(id);
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
