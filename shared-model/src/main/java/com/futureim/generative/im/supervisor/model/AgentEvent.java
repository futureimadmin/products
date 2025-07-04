package com.futureim.generative.im.supervisor.model;

import lombok.Data;

import java.util.Map;

@Data
public class AgentEvent {
    private String eventType;
    private Map<String, Object> payload;
}
