package com.nttdata.pocticket.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @NotBlank(message = "The name cannot be empty")
    @Size(max = 50, message = "Max 50 characters")
    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String startDate;

    @Column(length = 20, nullable = false)
    private String endDate;


    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false)
    @JsonBackReference
    private Area area;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ticket> tickets;
}
