package com.nttdata.pocticket.controller;

import com.nttdata.pocticket.model.dto.ProjectDTO;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.model.entity.rec.ProjectDetails;
import com.nttdata.pocticket.services.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all projects.
     * @return A list of all projects.
     */
    @GetMapping
    public List<ProjectDTO> getAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a project by its ID.
     * @param id The ID of the project to retrieve.
     * @return The project with the specified ID, or null if not found.
     */
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id).orElse(null);
    }

    @GetMapping("/projects/{id}")
    public String getProjectByIdHtml(@PathVariable Long id, Model model){
        Project project = projectService.getProjectById(id).orElse(null);
        if (project != null){
            model.addAttribute("project", project);
        }
            return "project-details";
    }

    /**
     * Retrieves detailed information about a project by its ID.
     * @param id The ID of the project to retrieve details for.
     * @return Detailed information about the project, or null if the project is not found.
     */
    @GetMapping("/{id}/details")
    public ProjectDetails getProjectDetails(@PathVariable Long id){
        Project project = projectService.getProjectById(id).orElse(null);
        return project != null ? new ProjectDetails(project) : null;
    }

    /**
     * Creates a new project.
     * @param project The project data for creation.
     * @return The newly created project.
     */
    @PostMapping
    public Project createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }

    /**
     * Updates an existing project.
     * @param project The updated project data.
     * @return The updated project.
     */
    @PutMapping("/{id}")
    public Project updateProject(@RequestBody Project project){
        return projectService.updateProject(project);
    }

    /**
     * Deletes a project by its ID.
     * @param id The ID of the project to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
    }
}
