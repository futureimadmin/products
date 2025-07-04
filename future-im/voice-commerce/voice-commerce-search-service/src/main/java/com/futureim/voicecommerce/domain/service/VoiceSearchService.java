package com.futureim.voicecommerce.domain.service;

import com.futureim.voicecommerce.domain.event.VoiceSearchEvent;
import com.futureim.voicecommerce.domain.model.Product;
import com.futureim.voicecommerce.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoiceSearchService {

    private final ProductRepository productRepository;
    
    @Value("${voice.search.similarity.threshold}")
    private double similarityThreshold;
    
    @Value("${voice.search.max.results}")
    private int maxResults;

    public List<Product> searchProducts(String voiceCommand) {
        // Create and publish search event
        VoiceSearchEvent event = new VoiceSearchEvent(
            voiceCommand,
            LocalDateTime.now(),
            "anonymous", // In real app, get from security context
            VoiceSearchEvent.SearchStatus.INITIATED
        );

        try {
            // Process voice command
            List<String> keywords = processVoiceCommand(voiceCommand);
            
            // Search products
            List<Product> results = productRepository.findByKeywords(keywords)
                .stream()
                .limit(maxResults)
                .collect(Collectors.toList());

            // Update event status
            event.setStatus(VoiceSearchEvent.SearchStatus.COMPLETED);
            
            return results;
        } catch (Exception e) {
            event.setStatus(VoiceSearchEvent.SearchStatus.FAILED);
            throw e;
        }
    }

    private List<String> processVoiceCommand(String voiceCommand) {
        // In a real implementation, this would use NLP to extract relevant keywords
        // For now, we'll do simple word splitting and filtering
        return Arrays.stream(voiceCommand.toLowerCase().split("\\s+"))
            .filter(word -> word.length() > 2) // Filter out short words
            .collect(Collectors.toList());
    }
}
