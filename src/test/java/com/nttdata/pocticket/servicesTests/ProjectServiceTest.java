package com.nttdata.pocticket.servicesTests;

import com.nttdata.pocticket.model.entity.Area;
import com.nttdata.pocticket.model.entity.Project;
import com.nttdata.pocticket.repositories.ProjectRepository;
import com.nttdata.pocticket.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    public void getProjectByIdTest(){
        Long projectId = 1L;
        Project project = new Project(projectId, "Project Name", "02/02/2024", "27/02/2024", new Area(), new ArrayList<>());
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Optional<Project> result = projectService.getProjectById(projectId);

        assertTrue(result.isPresent());
        assertEquals(project, result.get());
    }

    @Test
    public void createProjectTest(){
        Project project = new Project(1L, "Project Name", "02/02/2024", "27/02/2024", new Area(), new ArrayList<>());

        when(projectRepository.save(project)).thenReturn(project);
        Project createdProject = projectService.createProject(project);

        assertNotNull(createdProject);
        assertEquals(project.getName(), createdProject.getName());
    }

    @Test
    public void updateProjectTest(){
        Long projectId = 1L;
        Project existingProject = new Project(projectId, "Project Name", "02/02/2024", "27/02/2024", new Area(), new ArrayList<>());
        Project updatedProject = new Project(projectId, "Project Name1", "02/03/2024", "27/03/2024", new Area(), new ArrayList<>());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(existingProject)).thenReturn(existingProject);

        Project result = projectService.updateProject(projectId, updatedProject);

        assertNotNull(result);
        assertEquals(updatedProject.getName(), result.getName());
    }

    @Test
    public void getProjectsWithMostContributorsTest(){
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1L, "Project Name", "02/02/2024", "27/02/2024", new Area(), new ArrayList<>()));
        projects.add(new Project(2L, "Project Name1", "02/03/2024", "27/03/2024", new Area(), new ArrayList<>()));

        when(projectRepository.findProjectsWithMostContributors()).thenReturn(projects);

        List<Project> result = projectService.getProjectsWithMostContributors();

        assertEquals(projects.size(), result.size());
        assertTrue(result.containsAll(projects));
    }

    @Test
    public void deleteProjectTest(){
        Long projectId = 1L;
        doNothing().when(projectRepository).deleteById(projectId);

        projectService.deleteProject(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }
}
