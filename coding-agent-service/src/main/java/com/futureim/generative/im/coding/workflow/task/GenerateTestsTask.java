package com.futureim.generative.im.coding.workflow.task;

import com.futureim.generative.im.coding.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class GenerateTestsTask extends BaseTask {
    @Autowired
    private LLMService llmService;

    @Autowired
    public GenerateTestsTask(TaskRepository taskRepository, LLMService llmService) {
        super(taskRepository, "generate_tests");
        this.llmService = llmService;
    }

    public CompletableFuture<Void> execute(String requirements, String projectDir) {
        startTask("generate_tests");
        
        return CompletableFuture.runAsync(() -> {
            try {
                llmService.generateTests(requirements, projectDir);
                completeTask();
            } catch (Exception e) {
                failTask(e);
            }
        }, taskExecutor);
    }
}
