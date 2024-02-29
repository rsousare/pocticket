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

    @GetMapping
    public List<Area> getAllAreas(){
        return areaService.getAllAreas();
    }

    @GetMapping("/{id}")
    public Area getAreaById(@PathVariable Long id){
        return areaService.getAreaById(id).orElse(null);
    }

    @PostMapping
    public Area createArea(@RequestBody Area area){
        return areaService.createArea(area);
    }

    @PutMapping("/{id}")
    public Area updateArea(@RequestBody Area area){
        return areaService.updateArea(area);
    }

    @DeleteMapping("/{id}")
    public void deleteArea(@PathVariable Long id){
        areaService.deleteArea(id);
    }
}
