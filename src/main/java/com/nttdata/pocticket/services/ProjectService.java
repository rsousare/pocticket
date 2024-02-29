package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return projectRepository.findAll();
    }

    /**
     * Retrieves a project by ID.
     * @param projectId The ID of the project to retrieve.
     * @return An Optional containing the project if found.
     */
    public Optional<Project> getProjectById(Long projectId){
        return projectRepository.findById(projectId);
    }

    /**
     * Creates a new project.
     * @param project The project to be created.
     * @return The newly created project.
     */
    public Project createProject(Project project){
        return projectRepository.save(project);
    }

    /**
     * Updates an existing project.
     * @param projectId The ID of the project to be updated.
     * @param newProject The project with updated data.
     * @return The updated project.
     * @throws EntityNotFoundException If the project is not found.
     */
    public Project updateProject(Long projectId, Project newProject){
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()){
            Project existingProject = projectOptional.get();
            existingProject.setName(newProject.getName());
            existingProject.setStartDate(newProject.getStartDate());
            existingProject.setEndDate(newProject.getEndDate());
            return projectRepository.save(existingProject);
        }else {
            throw new EntityNotFoundException("Project with id " + projectId + " not found!");
        }
    }

    /**
     * Deletes a project by ID.
     * @param projectId The ID of the project to be deleted.
     */
    public void deleteProject(Long projectId){
        projectRepository.deleteById(projectId);
    }

    /**
     * Retrieves projects with the most contributors.
     * @return List of projects with the most contributors.
     */
    public List<Project> getProjectsWithMostContributors(){
        return projectRepository.findProjectsWithMostContributors();
    }
}
