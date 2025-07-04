package com.futureim.voicecommerce.domain.service;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class VoiceConversionService {

    public String convertVoiceToText(MultipartFile audioFile) throws IOException {
        try (SpeechClient speechClient = SpeechClient.create()) {
            // Configure audio and recognition settings
            RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setLanguageCode("en-US")
                .setSampleRateHertz(16000)
                .build();

            // Read the audio file
            ByteString audioBytes = ByteString.copyFrom(audioFile.getBytes());
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
            throw e;
        }
    }
}
