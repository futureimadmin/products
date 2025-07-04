package com.futureim.generative.im.coding.workflow.task;

import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalDateTime;

public abstract class BaseTask {
    protected final ThreadPoolTaskExecutor taskExecutor;
    protected final TaskRepository taskRepository;
    protected final String taskId;

    protected BaseTask(TaskRepository taskRepository, String taskId) {
        this.taskRepository = taskRepository;
        this.taskId = taskId;
        this.taskExecutor = new ThreadPoolTaskExecutor();
        this.taskExecutor.initialize();
    }

    protected void startTask(String taskName) {
        TaskExecution taskExecution = taskRepository.createTaskExecution(taskName);
        taskExecution = taskRepository.startTaskExecution(taskExecution.getExecutionId(), null, LocalDateTime.now(), null, null, null);
        this.taskExecution = taskExecution;
    }

    protected void completeTask() {
        taskRepository.completeTaskExecution(taskExecution.getExecutionId(), 0, LocalDateTime.now(), null);
    }

    protected void failTask(Exception e) {
//        taskRepository.failTaskExecution(taskExecution.getExecutionId(), System.currentTimeMillis(), e.getMessage());
        throw new RuntimeException("Task execution failed", e);
    }

    protected TaskExecution taskExecution;
}
