package com.futureim.generative.im.coding.workflow.task;

import com.futureim.generative.im.coding.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class SetupEnvironmentTask extends BaseTask {
    private final LLMService llmService;

    @Autowired
    public SetupEnvironmentTask(TaskRepository taskRepository, LLMService llmService) {
        super(taskRepository, "setup_environment");
        this.llmService = llmService;
    }

    public CompletableFuture<String> execute(String requirements) {
        startTask("setup_environment");
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                String projectDir = llmService.generateEnvironmentSetup(requirements, "");
                completeTask();
                return projectDir;
            } catch (Exception e) {
                failTask(e);
                return null;
            }
        }, taskExecutor);
    }
}
