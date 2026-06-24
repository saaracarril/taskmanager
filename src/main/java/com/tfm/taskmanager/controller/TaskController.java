package com.tfm.taskmanager.controller;

import com.tfm.taskmanager.dto.TaskCreateDTO;
import com.tfm.taskmanager.dto.TaskUpdateDTO;
import com.tfm.taskmanager.model.Priority;
import com.tfm.taskmanager.model.Status;
import com.tfm.taskmanager.model.Task;
import com.tfm.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // 1. Campo inmutable (final) sin @Autowired directo
    private final TaskService taskService;

    // 2. Inyección explícita a través del constructor (Recomendado por Spring)
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Requisito 1: Creación de tareas usando TaskCreateDTO
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskCreateDTO taskDTO) {
        // Mapeo manual de DTO a Entidad para procesarlo en el servicio
        Task task = new Task(
            taskDTO.getTitle(),
            taskDTO.getDescription(),
            taskDTO.getDueDate(),
            taskDTO.getPriority()
        );
        
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Requisito 2: Actualización general de la tarea (Título, descripción, etc.)
     * PUT http://localhost:8080/api/tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO taskDTO) {
        // Convertimos el DTO en un objeto Task para mantener la firma del servicio
        Task taskDetails = new Task();
        taskDetails.setTitle(taskDTO.getTitle());
        taskDetails.setDescription(taskDTO.getDescription());
        taskDetails.setDueDate(taskDTO.getDueDate());
        taskDetails.setPriority(taskDTO.getPriority());

        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Requisito 2: Actualización específica del ESTADO (Máquina de estados)
     * PATCH http://localhost:8080/api/tasks/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestParam Status status) {
        Task updatedTask = taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Requisito 3: Consulta y filtrado dinámico de tareas
     * GET http://localhost:8080/api/tasks (Trae todas)
     * GET http://localhost:8080/api/tasks?status=PENDIENTE (Filtra por estado)
     * GET http://localhost:8080/api/tasks?status=PENDIENTE&priority=ALTA (Filtra por ambos)
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority) {
        List<Task> tasks = taskService.getAllTasks(status, priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Requisito 4: Eliminación de tareas por ID
     * DELETE http://localhost:8080/api/tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
