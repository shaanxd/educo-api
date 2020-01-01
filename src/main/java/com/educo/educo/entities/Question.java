package com.educo.educo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id", updatable = false, nullable = false)
    private String id;
    @NotBlank(message = "Question title is required.")
    private String title;
    @NotBlank(message = "Question description is required")
    private String description;

    @CreationTimestamp
    @Transient
    private Date createdAt;
    @Transient
    @UpdateTimestamp
    private Date updatedAt;
}
