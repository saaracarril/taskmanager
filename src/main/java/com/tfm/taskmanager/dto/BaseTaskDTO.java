package com.tfm.taskmanager.dto;

import com.tfm.taskmanager.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public abstract class BaseTaskDTO {

    @NotBlank(message = "El título de la tarea no puede estar vacío")
    private String title;

    @NotBlank(message = "La descripción de la tarea no puede estar vacía")
    private String description;

    @NotNull(message = "La fecha límite es obligatoria")
    private LocalDate dueDate;

    @NotNull(message = "El nivel de prioridad es obligatorio")
    private Priority priority;

    // --- GETTERS Y SETTERS COMPARTIDOS ---
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
}