package com.tfm.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tfm.taskmanager.model.Task;
import com.tfm.taskmanager.model.Status;
import com.tfm.taskmanager.model.Priority;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatusAndPriority(Status status, Priority priority);

    List<Task> findByStatus(Status status);

    List<Task> findByPriority(Priority priority);
}