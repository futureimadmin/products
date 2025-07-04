package com.futureim.generative.im.coding.workflow.task;

import com.futureim.generative.im.coding.service.LLMService;
import com.futureim.generative.im.coding.util.CodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class GenerateCodeTask extends BaseTask {
    
    @Autowired
    private LLMService llmService;

    @Autowired
    public GenerateCodeTask(TaskRepository taskRepository) {
        super(taskRepository, "generate_code");
    }

    public CompletableFuture<Void> execute(String requirements, String projectDir) {
        startTask("generate_code");
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Generate code using LLM
                String generatedCode = llmService.generateCode(requirements, projectDir);
                
                // Process and write code to files
                CodeProcessor.processCode(generatedCode, projectDir);
                
                completeTask();
                return null;
            } catch (Exception e) {
                failTask(e);
                return null;
            }
        }, taskExecutor);
    }
}
