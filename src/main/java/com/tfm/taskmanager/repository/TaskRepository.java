package com.tfm.taskmanager.repository;

import com.tfm.taskmanager.model.Priority;
import com.tfm.taskmanager.model.Status;
import com.tfm.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Requisito 3: Permite segmentar las tareas en función de su estado.
     */
    List<Task> findByStatus(Status status);

    /**
     * Requisito 3: Permite segmentar las tareas en función de su nivel de prioridad.
     */
    List<Task> findByPriority(Priority priority);

    /**
     * Requisito 3: Permite segmentar las tareas combinando ambos criterios (Estado y Prioridad).
     */
    List<Task> findByStatusAndPriority(Status status, Priority priority);
}