package com.futureim.generative.im.coding.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeProcessor {
    
    public static void processCode(String code, String projectDir) throws IOException {
        // Create project directory if it doesn't exist
        Path projectPath = Paths.get(projectDir);
        if (!Files.exists(projectPath)) {
            Files.createDirectories(projectPath);
        }
        
        // Split code into files
        String[] files = code.split("(?m)^//\s*FILE:\s*(\\S+)");
        
        for (String fileContent : files) {
            if (fileContent.trim().isEmpty()) continue;
            
            // Extract filename from first line
            String filename = extractFilename(fileContent);
            if (filename == null) continue;
            
            // Get file content
            String content = extractContent(fileContent);
            if (content == null) continue;
            
            // Write to file
            Path filePath = projectPath.resolve(filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, content.getBytes());
        }
    }
    
    private static String extractFilename(String content) {
        Pattern pattern = Pattern.compile("//\s*FILE:\s*(\\S+)");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    
    private static String extractContent(String content) {
        int start = content.indexOf("\n") + 1;
        return content.substring(start).trim();
    }
    
    public static Map<String, String> analyzeCodeDependencies(String code) {
        // TODO: Implement dependency analysis
        return null;
    }
}
