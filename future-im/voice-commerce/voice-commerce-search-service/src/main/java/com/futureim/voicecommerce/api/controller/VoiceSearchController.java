package com.futureim.voicecommerce.api.controller;

import com.futureim.voicecommerce.api.dto.ProductResponse;
import com.futureim.voicecommerce.api.dto.VoiceSearchRequest;
import com.futureim.voicecommerce.domain.service.VoiceConversionService;
import com.futureim.voicecommerce.domain.service.VoiceSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/voice-search")
@RequiredArgsConstructor
@Slf4j
public class VoiceSearchController {

    private final VoiceSearchService voiceSearchService;
    private final VoiceConversionService voiceConversionService;

    @PostMapping(value = "/products/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ProductResponse>> searchProductsByVoice(
            @RequestParam("audioFile") MultipartFile audioFile) {
        
        try {
            // Convert voice to text
            String voiceCommand = voiceConversionService.convertVoiceToText(audioFile);
            log.info("Converted voice command: {}", voiceCommand);

            // Search products using the converted text
            var products = voiceSearchService.searchProducts(voiceCommand);
            
            var response = products.stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            log.error("Error processing voice command", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/products/text")
    public ResponseEntity<List<ProductResponse>> searchProductsByText(
            @Valid @RequestBody VoiceSearchRequest request) {
        
        var products = voiceSearchService.searchProducts(request.getVoiceCommand());
        
        var response = products.stream()
            .map(ProductResponse::fromProduct)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
}
