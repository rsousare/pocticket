package com.nttdata.pocticket.controller;

import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.services.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * Retrieves all areas.
     * @return A list of all areas.
     */
    @GetMapping
    public List<Area> getAllAreas(){
        return areaService.getAllAreas();
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
