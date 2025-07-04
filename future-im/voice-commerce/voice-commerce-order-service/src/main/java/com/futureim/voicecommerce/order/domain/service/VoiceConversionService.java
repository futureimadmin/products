package com.futureim.voicecommerce.order.domain.service;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class VoiceConversionService {

    public String convertVoiceToText(byte[] audioData) {
        try (SpeechClient speechClient = SpeechClient.create()) {
            // Configure audio and recognition settings
            RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setLanguageCode("en-US")
                .setSampleRateHertz(16000)
                .build();

            // Prepare the audio data
            ByteString audioBytes = ByteString.copyFrom(audioData);
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                .setContent(audioBytes)
                .build();

            // Perform the transcription
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            if (results.isEmpty()) {
                throw new RuntimeException("No speech recognition results found");
            }

            // Build the complete transcription
            StringBuilder transcription = new StringBuilder();
            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                transcription.append(alternative.getTranscript());
            }

            log.info("Voice command transcribed: {}", transcription);
            return transcription.toString();
        } catch (IOException e) {
            log.error("Error during voice-to-text conversion", e);
            throw new RuntimeException("Failed to process voice command", e);
        }
    }
}
