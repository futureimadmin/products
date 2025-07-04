package com.futureim.generative.im.claude.model;

import java.util.List;

public class Conversation {
    private List<ClaudeMessage> messages;

    public List<ClaudeMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ClaudeMessage> messages) {
        this.messages = messages;
    }
}
