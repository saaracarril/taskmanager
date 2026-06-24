package com.tfm.taskmanager.controller;

import com.tfm.taskmanager.model.Priority;
import com.tfm.taskmanager.model.Status;
import com.tfm.taskmanager.model.Task;
import com.tfm.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Crear tarea
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.create(task);
    }

    // Todas las tareas
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAll();
    }

    // Actualizar estado
    @PatchMapping("/{id}/status")
    public Task updateStatus(
            @PathVariable Long id,
            @RequestParam Status status) {

        return taskService.updateStatus(id, status);
    }

    // Filtrar
    @GetMapping("/filter")
    public List<Task> filterTasks(
            @RequestParam Status status,
            @RequestParam Priority priority) {

        return taskService.filter(status, priority);
    }

    // Eliminar tarea
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {

        return taskService.update(id, task);
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable Status status) {
        return taskService.findByStatus(status);
    }

    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable Priority priority) {
        return taskService.findByPriority(priority);
    }
}