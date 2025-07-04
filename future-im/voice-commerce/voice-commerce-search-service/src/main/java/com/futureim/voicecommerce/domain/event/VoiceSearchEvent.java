package com.futureim.voicecommerce.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceSearchEvent {
    private String searchQuery;
    private LocalDateTime timestamp;
    private String userId;
    private SearchStatus status;

    public enum SearchStatus {
        INITIATED,
        PROCESSED,
        COMPLETED,
        FAILED
    }
}
