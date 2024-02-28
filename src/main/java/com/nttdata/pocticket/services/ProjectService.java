package com.nttdata.pocticket.services;

import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long projectId){
        return projectRepository.findById(projectId);
    }

    public Project createProject(Project project){
        return projectRepository.save(project);
    }

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

    public void deleteProject(Long projectId){
        projectRepository.deleteById(projectId);
    }

    public List<Project> getProjectsWithMostContributors(){
        return projectRepository.findProjectsWithMostContributors();
    }
}
