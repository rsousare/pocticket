package com.nttdata.pocticket.controllersTests;

import com.nttdata.pocticket.controller.ProjectController;
import com.nttdata.pocticket.model.dto.ProjectDTO;
import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProjectController projectController;

    @Test
    public void getAllProjects(){
        when(modelMapper.map(any(), eq(ProjectDTO.class))).thenAnswer(invocationOnMock -> {
           Project source = invocationOnMock.getArgument(0);
           return new ProjectDTO(source.getId(), source.getName(), source.getStartDate(), source.getEndDate());
        });

        Project project1 = new Project(1L, "Project1", "02/02/2024", "27/02/2024", new Area(), new ArrayList<>());
        Project project2 = new Project(2L, "Project2", "02/02/2024", "27/02/2024", new Area(), new ArrayList<>());

        List<Project> mockProjects = Arrays.asList(project1, project2);
        when(projectService.getAllProjects()).thenReturn(mockProjects);

        List<ProjectDTO> allProjects = projectController.getAllProjects();
        verify(projectService).getAllProjects();

        assertEquals(2, allProjects.size());
        assertEquals("Project1", allProjects.get(0).getName());

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
