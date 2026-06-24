package com.tfm.taskmanager.service;

import com.tfm.taskmanager.exception.InvalidStatusTransitionException;
import com.tfm.taskmanager.model.Priority;
import com.tfm.taskmanager.model.Status;
import com.tfm.taskmanager.model.Task;
import com.tfm.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static final String TASK_NOT_FOUND_MSG = "Tarea no encontrada con id: ";

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        task.setStatus(Status.PENDING);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(TASK_NOT_FOUND_MSG + id));
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setPriority(taskDetails.getPriority());
        
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskStatus(Long id, Status newStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(TASK_NOT_FOUND_MSG + id));
        
        Status currentStatus = task.getStatus();

        // CORRECCIÓN: De COMPLETED a COMPLETADA
        if (currentStatus == Status.COMPLETED) {
            throw new InvalidStatusTransitionException("No se puede modificar el estado de una tarea ya COMPLETADA.");
        }
        
        // CORRECCIÓN: De PENDING/COMPLETED a PENDIENTE/COMPLETADA
        if (currentStatus == Status.PENDING && newStatus == Status.COMPLETED) {
             throw new InvalidStatusTransitionException("Una tarea PENDIENTE debe pasar primero por EN_PROGRESO.");
        }

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
    @Override
    public List<Task> getAllTasks(Status status, Priority priority) {
        // Requisito 3: Lógica combinatoria para filtros dinámicos
        if (status != null && priority != null) {
            return taskRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            return taskRepository.findByStatus(status);
        } else if (priority != null) {
            return taskRepository.findByPriority(priority);
        }
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long id) {
        // Requisito 4: Eliminación por ID
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(TASK_NOT_FOUND_MSG + id));
        taskRepository.delete(task);
    }
}