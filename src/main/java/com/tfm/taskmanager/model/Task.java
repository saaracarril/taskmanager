package com.tfm.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título de la tarea no puede estar vacío")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "La descripción de la tarea no puede estar vacía")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull(message = "La fecha límite es obligatoria")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @NotNull(message = "El nivel de prioridad es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // --- CONSTRUCTORES ---

    /**
     * Constructor vacío requerido por JPA/Hibernate para instanciar el objeto
     * al recuperar datos de la BD H2.
     * Aquí garantizamos el Requisito 1: el estado inicial por defecto es PENDIENTE.
     */
    public Task() {
        this.status = Status.PENDING;
    }

    /**
     * Constructor parametrizado para la creación manual de nuevas tareas.
     * No incluye el ID (ya que lo genera la base de datos de forma autoincremental)
     * ni el estado (se asigna automáticamente como PENDIENTE).
     */
    public Task(String title, String description, LocalDate dueDate, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = Status.PENDING; // Cumplimiento estricto del Requisito 1
    }

    // --- GETTERS Y SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}