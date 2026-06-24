package com.tfm.taskmanager.service;

import com.tfm.taskmanager.model.Priority;
import com.tfm.taskmanager.model.Status;
import com.tfm.taskmanager.model.Task;
import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task updateTask(Long id, Task taskDetails);
    Task updateTaskStatus(Long id, Status newStatus); // El corazón del Requisito 2
    List<Task> getAllTasks(Status status, Priority priority); // Requisito 3
    void deleteTask(Long id); // Requisito 4
}