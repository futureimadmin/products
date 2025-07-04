package com.futureim.generative.im.model.event;

import com.futureim.generative.im.model.AgentType;
import lombok.Data;

import java.time.Instant;

@Data
public class AgentEvent {
    private String eventId;
    private AgentType agentType;
    private String eventType;
    private Instant timestamp;
    private String payload;
}
