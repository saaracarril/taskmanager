package com.example.taskmanager.service;

import com.example.taskmanager.domain.Priority;
import com.example.taskmanager.domain.Status;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.exception.InvalidStatusTransitionException;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse createTask(TaskRequest request) {
        Task task = TaskMapper.toEntity(request);
        task.setStatus(Status.PENDING);
        Task saved = taskRepository.save(task);
        return TaskMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long id) {
        Task task = findTaskOrThrow(id);
        return TaskMapper.toResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(Status status, Priority priority) {
        List<Task> tasks;
        if (status != null && priority != null) {
            tasks = taskRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority);
        } else {
            tasks = taskRepository.findAll();
        }
        return tasks.stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task existing = findTaskOrThrow(id);
        Status currentStatus = existing.getStatus();
        Status requestedStatus = request.getStatus() != null ? request.getStatus() : currentStatus;

        validateStatusTransition(currentStatus, requestedStatus);

        TaskMapper.updateEntity(existing, request);
        existing.setStatus(requestedStatus);

        Task saved = taskRepository.save(existing);
        return TaskMapper.toResponse(saved);
    }

    @Override
    public void deleteTask(Long id) {
        Task existing = findTaskOrThrow(id);
        taskRepository.delete(existing);
    }

    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private void validateStatusTransition(Status from, Status to) {
        if (from == to) {
            return;
        }
        if (from == Status.PENDING && to == Status.IN_PROGRESS) {
            return;
        }
        if (from == Status.IN_PROGRESS && to == Status.COMPLETED) {
            return;
        }
        throw new InvalidStatusTransitionException(from, to);
    }
}