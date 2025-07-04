package com.futureim.generative.im.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentStatus {
    private String status;
    private double progress;
    private long lastUpdated;
    private List<TaskRecord> tasks = new ArrayList<>();
    
    public void setStatus(String status) {
        this.status = status;
        this.lastUpdated = System.currentTimeMillis();
    }
    
    public void setProgress(double progress) {
        this.progress = progress;
        this.lastUpdated = System.currentTimeMillis();
    }
    
    public double getProgress() {
        return progress;
    }
    
    public String getStatus() {
        return status;
    }
    
    public long getLastUpdated() {
        return lastUpdated;
    }
    
    public void addTask(String taskId, Map<String, Object> parameters) {
        tasks.add(new TaskRecord(taskId, parameters));
    }
    
    public List<TaskRecord> getTasks() {
        return new ArrayList<>(tasks);
    }
    
    public static class TaskRecord {
        private final String taskId;
        private final Map<String, Object> parameters;
        private final long timestamp;
        
        public TaskRecord(String taskId, Map<String, Object> parameters) {
            this.taskId = taskId;
            this.parameters = new HashMap<>(parameters);
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getTaskId() {
            return taskId;
        }
        
        public Map<String, Object> getParameters() {
            return new HashMap<>(parameters);
        }
        
        public long getTimestamp() {
            return timestamp;
        }
    }
}
