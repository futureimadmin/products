package com.futureim.generative.im.coding.workflow.task;

import com.futureim.generative.im.coding.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class SetupDatabaseTask extends BaseTask {
    private final LLMService llmService;

    @Autowired
    public SetupDatabaseTask(TaskRepository taskRepository, LLMService codingAgent) {
        super(taskRepository, "setup_database");
        this.llmService = codingAgent;
    }

    public CompletableFuture<Void> execute(String requirements) {
        startTask("setup_database");
        
        return CompletableFuture.runAsync(() -> {
            try {
                llmService.generateDatabaseSetup(requirements, "");
                completeTask();
            } catch (Exception e) {
                failTask(e);
            }
        }, taskExecutor);
    }
}
