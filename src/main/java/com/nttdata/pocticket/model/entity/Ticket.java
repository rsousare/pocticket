package com.nttdata.pocticket.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nttdata.pocticket.model.enums.TicketPriority;
import com.nttdata.pocticket.model.enums.TicketStatus;
import com.nttdata.pocticket.model.enums.TicketType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @NotBlank(message = "The title cannot be empty")
    @Size(max = 50, message = "Max 50 characters")
    @Column(length = 50, nullable = false)
    private String title;

    @NotBlank(message = "The title cannot be empty")
    @Size(max = 200, message = "Max 200 characters")
    @Column(length = 200, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TicketType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TicketPriority priority;

    @Column(nullable = false)
    private int progress;

    @Column(nullable = false)
    private int estimate;

    @CreationTimestamp
    @Column(name = "created_At", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id", nullable = false)
    private People assignedTo;

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false)
    private People createdBy;

    @Column(nullable = true)
    private LocalDateTime resolvedAt;

    @ManyToOne
    @JoinColumn(name = "resolved_by_id", nullable = true)
    private People resolvedBy;
}
