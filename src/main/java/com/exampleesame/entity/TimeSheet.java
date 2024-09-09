package com.exampleesame.entity;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.exampleesame.entity.constant.BaseEntity;

@Entity
@Table(name = "timesheet")
public class TimeSheet extends  BaseEntity{
    
    @JsonbProperty(value = "project_name")
    @NotBlank
    @Column(name = "project_name", nullable = false)
    private String project;

    @JsonbProperty(value = "description")
    @NotBlank
    @Column(name = "description", nullable = false, length = 255 )
    private String description;

    @JsonbProperty(value = "start_date_time")
    @NotBlank
    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @JsonbProperty(value = "and_date_time")
    @NotBlank
    @Column(name = "and_date_time", nullable = false)
    private LocalDateTime endDateTime;

    public TimeSheet() {
    }

    public TimeSheet(@NotBlank String project, @NotBlank String description, @NotBlank LocalDateTime startDateTime,
            @NotBlank LocalDateTime endDateTime) {
        this.project = project;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    




}
