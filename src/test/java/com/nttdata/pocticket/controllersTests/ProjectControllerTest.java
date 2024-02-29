package com.nttdata.pocticket.controllersTests;

import com.nttdata.pocticket.controller.ProjectController;
import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {
    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    public void getAllProjects(){
        List<Project> mockProjects = Arrays.asList(new Project(), new Project());
        when(projectService.getAllProjects()).thenReturn(mockProjects);

        List<Project> allProjects = projectController.getAllProjects();

        assertEquals(mockProjects, allProjects);
        verify(projectService).getAllProjects();
    }

    @Test
    public void getProjectByIdTest(){
        Long id = 1L;
        Project mockProject = new Project();
        mockProject.setId(id);
        mockProject.setName("New project");
        when(projectService.getProjectById(id)).thenReturn(Optional.of(mockProject));

        Project projectById = projectController.getProjectById(id);

        assertEquals(mockProject, projectById);
        verify(projectService).getProjectById(id);
    }

    @Test
    public void createProjectTest(){
        Project projectToCreate = new Project();
        Project createdProject = new Project(1L, "New project", "02/02/2024", "26/02/2024", new Area(), new ArrayList<>());
        when(projectService.createProject(projectToCreate)).thenReturn(createdProject);

        Project returnedProject = projectController.createProject(projectToCreate);

        assertEquals(createdProject, returnedProject);
        verify(projectService).createProject(projectToCreate);
    }

    @Test
    public void updateProjectTest(){
        Long id = 1L;
        Project existingProject = mock(Project.class);
        when(projectService.updateProject(existingProject)).thenReturn(existingProject);

        Project updatedProject = projectController.updateProject(existingProject);
        assertEquals(existingProject, updatedProject);
        verify(projectService).updateProject(existingProject);
    }

    @Test
    public void deleteProjectTest(){
        Long id = 1L;
        projectController.deleteProject(id);
        verify(projectService).deleteProject(id);
    }
}
