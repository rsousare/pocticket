package com.nttdata.pocticket.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @NotBlank(message = "The name cannot be empty")
    @Size(max = 50, message = "Max 50 characters")
    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String description;

    @OneToMany(mappedBy = "area")
    @JsonManagedReference
    private List<Project> projects;
}
