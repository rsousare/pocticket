package com.nttdata.pocticket.controller;

import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.model.entity.rec.ProjectDetails;
import com.nttdata.pocticket.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id).orElse(null);
    }

    @GetMapping("/{id}/details")
    public ProjectDetails getProjectDetails(@PathVariable Long id){
        Project project = projectService.getProjectById(id).orElse(null);
        return project != null ? new ProjectDetails(project) : null;
    }

    @PostMapping
    public Project createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public Project updateProject(@RequestBody Project project){
        return projectService.updateProject(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
    }
}
