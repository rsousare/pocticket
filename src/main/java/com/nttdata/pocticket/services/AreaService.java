package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.repositories.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to handle operations related to areas.
 */
@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    /**
     * Retrieves all areas.
     * @return List of all areas.
     */
    public List<Area> getAllAreas(){
        return areaRepository.findAll();
    }

    /**
     * Retrieves an area by ID.
     * @param areaId The ID of the area to retrieve.
     * @return An Optional containing the area if found.
     */
    public Optional<Area> getAreaById(Long areaId){
        return areaRepository.findById(areaId);
    }

    /**
     * Creates a new area.
     * @param area The area to be created.
     * @return The newly created area.
     */
    public Area createArea(Area area){
        return areaRepository.save(area);
    }

    /**
     * Updates an existing area.
     * @param newArea The new area with updated data.
     * @return The updated area.
     * @throws EntityNotFoundException If the area is not found.
     */
//    public Area updateArea(Area newArea){
//        Optional<Area> areaOptional = areaRepository.findById(newArea.getId());
//        if (areaOptional.isPresent()){
//            Area existingArea = areaOptional.get();
//            existingArea.setName(newArea.getName());
//            existingArea.setDescription(newArea.getDescription());
//            return areaRepository.save(existingArea);
//        }else {
//            throw new EntityNotFoundException("Area with ID " + newArea.getId() + " not found!");
//        }
//    }

    public Area updateArea(Area newArea){
        if (newArea == null || newArea.getId() == 0){
            throw new IllegalArgumentException("The provided Area or Id cannot be null or 0.");
        }

        Optional<Area> areaOptional = areaRepository.findById(newArea.getId());
        if (areaOptional.isEmpty()){
            throw new EntityNotFoundException("Area with Id " + newArea.getId() + " not found");
        }

        Area existingArea  = areaOptional.get();

        if (newArea.getName() == null || newArea.getName().isEmpty()){
            throw new IllegalArgumentException("Area name cannot be null or empty");
        }
        existingArea.setName(newArea.getName());
        existingArea.setDescription(newArea.getDescription());

        return areaRepository.save(existingArea);
    }

    /**
     * Deletes an area by ID.
     * @param areaId The ID of the area to be deleted.
     */
    public void deleteArea(Long areaId){
        areaRepository.deleteById(areaId);
    }

    /**
     * Retrieves areas with the most contributors.
     * @return List of areas with the most contributors.
     */
    public List<Area> getAreasWithMostContributors(){
        return areaRepository.findAreasWithMostContributors();
    }
}
