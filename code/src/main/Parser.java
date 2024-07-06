package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Parser {

    private static final String yamlFilePath = "./src/resources/file.yaml";
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(yamlFilePath))) {
            List<Map<String, Object>> documents = new ArrayList<>();
            Map<String, Object> currentMapping = new LinkedHashMap<>();
            List<Object> currentSequence = null;
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }

                int indentation = calculateIndentation(line);

                if (indentation == 0) {
                    // Top-level key-value pair
                    if (!currentMapping.isEmpty()) {
                        documents.add(currentMapping);
                        currentMapping = new LinkedHashMap<>();
                    }
                    String[] parts = line.split(":");
                    String key = parts[0].trim(); // Extract key
                    currentMapping.put(key, parseValue(parts.length > 1 ? parts[1].trim() : null)); // Extract value if present
                } else if (indentation == 1) {
                    // Nested key-value pair or sequence item
                    if (line.startsWith("-")) {
                        // Sequence item
                        if (currentMapping.containsKey("sequence")) {
                            currentSequence = (List<Object>) currentMapping.get("sequence");
                        } else {
                            currentSequence = new ArrayList<>();
                            currentMapping.put("sequence", currentSequence);
                        }
                        String value = line.substring(1).trim();
                        currentSequence.add(parseValue(value));
                    } else {
                        // Nested key-value pair
                        String[] parts = line.split(":");
                        String key = parts[0].trim();
                        String value = parts.length > 1 ? parts[1].trim() : "";
                        currentMapping.put(key, parseValue(value));
                    }
                } else {
                    continue;
                }
            }

            if (!currentMapping.isEmpty()) {
                documents.add(currentMapping);
            }

            System.out.println(documents);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object parseValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return value;
            }
        }
    }

    private static int calculateIndentation(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == ' ') {
            count++;
        }
        return count / 2; // Assuming 2 spaces per indentation level
    }
}