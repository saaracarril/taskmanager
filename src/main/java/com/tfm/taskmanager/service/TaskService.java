package com.tfm.taskmanager.service;

import com.tfm.taskmanager.model.Task;
import com.tfm.taskmanager.model.Status;
import com.tfm.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.tfm.taskmanager.model.Priority;
import java.util.List;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // 1. Crear tarea
    public Task create(Task task) {
        task.setStatus(Status.PENDING);
        return taskRepository.save(task);
    }

    // 2. Listar todas
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    // 3. Borrar tarea
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    // 4. Actualizar tarea
    public Task updateStatus(Long id, Status newStatus) {

    Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task no encontrada"));

    Status current = task.getStatus();

    if (current == Status.PENDING && newStatus != Status.IN_PROGRESS) {
        throw new IllegalStateException("Solo puedes pasar de PENDING a IN_PROGRESS");
    }

    if (current == Status.IN_PROGRESS && newStatus != Status.COMPLETED) {
        throw new IllegalStateException("Solo puedes pasar de IN_PROGRESS a COMPLETED");
    }

    if (current == Status.COMPLETED) {
        throw new IllegalStateException("No puedes cambiar una tarea completada");
    }

    task.setStatus(newStatus);
    return taskRepository.save(task);
    }

    // 5. Filtro
    public List<Task> filter(Status status, Priority priority) {
        return taskRepository.findByStatusAndPriority(status, priority);
    }

    public Task update(Long id, Task updatedTask) {

        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task no encontrada"));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setPriority(updatedTask.getPriority());

        return taskRepository.save(existingTask);
    }

    public List<Task> findByStatus(Status status) {
    return taskRepository.findByStatus(status);
}

    public List<Task> findByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

}
