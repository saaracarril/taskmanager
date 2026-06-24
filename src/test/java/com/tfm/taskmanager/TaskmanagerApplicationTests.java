package com.tfm.taskmanager;

import com.tfm.taskmanager.model.Priority;
import com.tfm.taskmanager.model.Status;
import com.tfm.taskmanager.model.Task;
import com.tfm.taskmanager.repository.TaskRepository;
import com.tfm.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTaskShouldSetPendingStatus() {

        Task task = new Task();
        task.setTitle("Test");
        task.setPriority(Priority.HIGH);

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.create(task);

        assertEquals(Status.PENDING, result.getStatus());
    }

	@Test
	void shouldChangeStatusFromPendingToInProgress() {

		Task task = new Task();
		task.setStatus(Status.PENDING);

		when(taskRepository.findById(1L))
				.thenReturn(Optional.of(task));

		when(taskRepository.save(any(Task.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		Task updated = taskService.updateStatus(1L, Status.IN_PROGRESS);

		assertEquals(Status.IN_PROGRESS, updated.getStatus());
	}

	@Test
	void shouldThrowExceptionForInvalidTransition() {

		Task task = new Task();
		task.setStatus(Status.PENDING);

		when(taskRepository.findById(1L))
				.thenReturn(Optional.of(task));

		assertThrows(
				IllegalStateException.class,
				() -> taskService.updateStatus(1L, Status.COMPLETED)
		);
	}
}
