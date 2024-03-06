package com.nttdata.pocticket.controller;

import com.nttdata.pocticket.model.dto.AreaDTO;
import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.services.AreaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/areas")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all areas.
     * @return A list of all areas.
     */
    @GetMapping
    public List<AreaDTO> getAllAreas(){
        List<Area> areas = areaService.getAllAreas();
        return areas.stream()
                .map(area -> modelMapper.map(area, AreaDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an area by its ID.
     * @param id The ID of the area to retrieve.
     * @return The area with the specified ID, or null if not found.
     */
    @GetMapping("/{id}")
    public Area getAreaById(@PathVariable Long id){
        return areaService.getAreaById(id).orElse(null);
    }

    /**
     * Creates a new area.
     * @param area The area data for creation.
     * @return The newly created area.
     */
    @PostMapping
    public Area createArea(@RequestBody Area area){
        return areaService.createArea(area);
    }

    @PostMapping("/create")
    public String createAreaHtml(@RequestParam ("name") String name, @RequestParam ("description") String description){
        Area area = new Area();
        area.setName(name);
        area.setDescription(description);
        areaService.createArea(area);
        return "Area created successfully";
    }

    /**
     * Updates an existing area.
     * @param area The updated area data.
     * @return The updated area.
     */
    @PutMapping("/{id}")
    public Area updateArea(@RequestBody Area area){
        return areaService.updateArea(area);
    }

    /**
     * Deletes an area by its ID.
     * @param id The ID of the area to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteArea(@PathVariable Long id){
        areaService.deleteArea(id);
    }
}
