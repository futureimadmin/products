package com.futureim.generative.im.coding.workflow.task;

import com.futureim.generative.im.coding.CodingAgent;
import com.futureim.generative.im.coding.service.LLMService;
import com.futureim.generative.im.coding.util.CodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Component
public class CheckInCodeTask extends BaseTask {
    private final CodingAgent codingAgent;
    @Autowired
    private LLMService llmService;

    @Autowired
    public CheckInCodeTask(TaskRepository taskRepository, CodingAgent codingAgent) {
        super(taskRepository, "check_in_code");
        this.codingAgent = codingAgent;
    }

    public CompletableFuture<Void> execute(String projectDir) {
        startTask("check_in_code");
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Read all files from project directory
                Path projectPath = Paths.get(projectDir);
                if (!Files.exists(projectPath)) {
                    throw new IOException("Project directory does not exist: " + projectDir);
                }
                
                // Analyze code dependencies using LLM
                String codeAnalysis = llmService.generateCode(
                    "Analyze the codebase and provide a structured list of dependencies and their versions.",
                    projectDir
                );
                
                // Process code analysis results
                CodeProcessor.analyzeCodeDependencies(codeAnalysis);
                
                // TODO: Implement actual code check-in logic
                // This would typically involve:
                // 1. Creating a git repository if it doesn't exist
                // 2. Adding files
                // 3. Committing changes
                // 4. Pushing to remote repository
                
//                codingAgent.checkInCode(projectDir);
                completeTask();
                return null;
            } catch (Exception e) {
                failTask(e);
                throw new RuntimeException("Failed to check in code", e);
            }
        }, taskExecutor).thenRunAsync(() -> {}, taskExecutor);
    }
}
