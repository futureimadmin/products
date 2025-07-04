package com.futureim.generative.im.coding.workflow;

import com.futureim.generative.im.coding.workflow.task.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CodingWorkflow {
    private final SetupEnvironmentTask setupEnvironmentTask;
    private final SetupDatabaseTask setupDatabaseTask;
    private final GenerateCodeTask generateCodeTask;
    private final GenerateTestsTask generateTestsTask;
    private final CheckInCodeTask checkInCodeTask;

    @Autowired
    public CodingWorkflow(SetupEnvironmentTask setupEnvironmentTask,
                         SetupDatabaseTask setupDatabaseTask,
                         GenerateCodeTask generateCodeTask,
                         GenerateTestsTask generateTestsTask,
                         CheckInCodeTask checkInCodeTask) {
        this.setupEnvironmentTask = setupEnvironmentTask;
        this.setupDatabaseTask = setupDatabaseTask;
        this.generateCodeTask = generateCodeTask;
        this.generateTestsTask = generateTestsTask;
        this.checkInCodeTask = checkInCodeTask;
    }

    public CompletableFuture<Void> executeWorkflow(String requirements) {
        // Execute tasks in sequence with CompletableFuture
        return setupEnvironmentTask.execute(requirements)
            .thenCompose(projectDir -> 
                setupDatabaseTask.execute(requirements)
                    .thenCompose(v -> 
                        generateCodeTask.execute(requirements, projectDir)
                            .thenCompose(v2 -> 
                                generateTestsTask.execute(requirements, projectDir)
                                    .thenCompose(v3 -> 
                                        checkInCodeTask.execute(projectDir)))));
    }
}
