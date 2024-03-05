package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.repositories.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        if (areaRepository == null){
            throw new IllegalArgumentException("Area cannot be null");
        }
        try {
            List<Area> areas = areaRepository.findAll();
            if (areas.isEmpty()) {
                throw new IllegalArgumentException("Areas cannot be empty");
            }
            return areas;
        }catch (DataAccessException e){
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves an area by ID.
     * @param areaId The ID of the area to retrieve.
     * @return An Optional containing the area if found.
     */
    public Optional<Area> getAreaById(Long areaId){
        if (areaId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return areaRepository.findById(areaId);
    }

    /**
     * Creates a new area.
     * @param area The area to be created.
     * @return The newly created area.
     */
    public Area createArea(Area area){
        if (area == null){
            throw new IllegalArgumentException("Area cannot be null");
        }
        if (area.getName() == null || area.getName().isEmpty()){
            throw new IllegalArgumentException("Area name is required");
        }
        return areaRepository.save(area);
    }

    /**
     * Updates an existing area.
     * @param newArea The new area with updated data.
     * @return The updated area.
     * @throws EntityNotFoundException If the area is not found.
     */

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
        if (areaId == null || areaId <= 0){
            throw new IllegalArgumentException("Invalid id Area");
        }
        areaRepository.deleteById(areaId);
    }

    /**
     * Retrieves areas with the most contributors.
     * @return List of areas with the most contributors.
     */
    public List<Area> getAreasWithMostContributors(){
        try {
            List<Area> areas = areaRepository.findAreasWithMostContributors();
            if (areas == null) {
                throw new IllegalArgumentException("No areas found with contributors");
            }
            if (areas.isEmpty()) {
                return Collections.emptyList();
            }
            return areas;
        }catch (DataAccessException e){
            throw new IllegalArgumentException("Error while retrieving areas with most contributors", e);
        }
    }
}
