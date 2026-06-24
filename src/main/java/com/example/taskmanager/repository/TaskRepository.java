package com.example.taskmanager.repository;

import com.example.taskmanager.domain.Priority;
import com.example.taskmanager.domain.Status;
import com.example.taskmanager.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Status status);

    List<Task> findByPriority(Priority priority);

    List<Task> findByStatusAndPriority(Status status, Priority priority);
}