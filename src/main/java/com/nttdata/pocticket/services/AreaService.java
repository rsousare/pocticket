package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.repositories.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public List<Area> getAllAreas(){
        return areaRepository.findAll();
    }

    public Optional<Area> getAreaById(Long areaId){
        return areaRepository.findById(areaId);
    }

    public Area createArea(Area area){
        return areaRepository.save(area);
    }

    public Area updateArea(Long areaId, Area newArea){
        Optional<Area> areaOptional = areaRepository.findById(areaId);
        if (areaOptional.isPresent()){
            Area existingArea = areaOptional.get();
            existingArea.setName(newArea.getName());
            existingArea.setDescription(newArea.getDescription());
            return areaRepository.save(existingArea);
        }else {
            throw new EntityNotFoundException("Area with ID " + areaId + " not found!");
        }
    }

    public void deleteArea(Long areaId){
        areaRepository.deleteById(areaId);
    }

    public List<Area> getAreasWithMostContributors(){
        return areaRepository.findAreasWithMostContributors();
    }
}
