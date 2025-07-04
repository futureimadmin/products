package com.futureim.generative.im.coding.dto;

import lombok.Data;

@Data
public class WorkflowNotification {
    private String workflowId;
    private String status;
    private String timestamp;
    private String projectId;
    private String message;
}
