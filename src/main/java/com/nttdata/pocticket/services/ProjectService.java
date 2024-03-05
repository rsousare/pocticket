package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service to handle operations related to projects.
 */
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Retrieves all projects.
     * @return List of all projects.
     */
    public List<Project> getAllProjects(){
        if (projectRepository == null){
            throw new IllegalArgumentException("ProjectRepository cannot be null");
        }
        try {
            List<Project> projects = projectRepository.findAll();
            if (projects.isEmpty()) {
                throw new IllegalArgumentException("Project cannot be empty");
            }
            return projects;
        }catch (DataAccessException e){
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves a project by ID.
     * @param projectId The ID of the project to retrieve.
     * @return An Optional containing the project if found.
     */
    public Optional<Project> getProjectById(Long projectId){
        if (projectId == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return projectRepository.findById(projectId);
    }

    /**
     * Creates a new project.
     * @param project The project to be created.
     * @return The newly created project.
     */
    public Project createProject(Project project){
        if (project == null){
            throw new IllegalArgumentException("Project cannot be null");
        }
        if (project.getName() == null || project.getName().isEmpty()){
            throw new IllegalArgumentException("Project name is required");
        }
        return projectRepository.save(project);
    }

    /**
     * Updates an existing project.
     * @param updateProject The project with updated data.
     * @return The updated project.
     * @throws EntityNotFoundException If the project is not found.
     */
    public Project updateProject(Project updateProject){
        if (updateProject == null){
            throw new IllegalArgumentException("Update cannot be null");
        }
        Optional<Project> projectOptional = projectRepository.findById(updateProject.getId());

        if (projectOptional.isEmpty()){
            throw new EntityNotFoundException("Project with id " + updateProject.getId() + " not found!");
        }

            Project existingProject = projectOptional.get();
            existingProject.setName(updateProject.getName());
            existingProject.setStartDate(updateProject.getStartDate());
            existingProject.setEndDate(updateProject.getEndDate());

            return projectRepository.save(existingProject);
    }

    /**
     * Deletes a project by ID.
     * @param projectId The ID of the project to be deleted.
     */
    public void deleteProject(Long projectId){
        if (projectId == null || projectId <= 0){
            throw new IllegalArgumentException("Invalid id project");
        }
        projectRepository.deleteById(projectId);
    }

    /**
     * Retrieves projects with the most contributors.
     * @return List of projects with the most contributors.
     */
    public List<Project> getProjectsWithMostContributors(){
        if (projectRepository == null){
            throw new IllegalArgumentException("project repository is not initialized");
        }
        try {
            return projectRepository.findProjectsWithMostContributors();
        }catch (Exception e){
            throw new RuntimeException("Failed to retrieved projects with most contributors", e);
        }
    }
}
