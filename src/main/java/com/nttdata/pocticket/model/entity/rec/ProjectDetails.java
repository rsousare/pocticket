package com.nttdata.pocticket.model.entity.rec;

import com.nttdata.pocticket.model.entity.Project;

public record ProjectDetails(Long id, String name, String startDate, String endDate) {
        public ProjectDetails(Project project) {
        this(project.getIdProject(), project.getName(), project.getStartDate(), project.getEndDate());
    }
}
