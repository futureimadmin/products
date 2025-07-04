package com.futureim.voicecommerce.cart.domain.service;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TextToSpeechService {

    public byte[] convertTextToSpeech(String text) {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder()
                .setText(text)
                .build();

            // Build the voice parameters
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                .setLanguageCode("en-US")
                .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                .build();

            // Select the type of audio file to return
            AudioConfig audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.LINEAR16)
                .build();

            // Perform the text-to-speech request
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();
            
            log.info("Successfully converted text to speech");
            return audioContents.toByteArray();
        } catch (Exception e) {
            log.error("Error during text-to-speech conversion", e);
            throw new RuntimeException("Failed to convert text to speech", e);
        }
    }
}
