package com.aicompanion.emergency.service;

import com.aicompanion.emergency.dto.IntentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class IntentParser {

    private static final List<String> EMERGENCY_KEYWORDS = List.of(
            "help", "fall", "fell", "fallen", "sos", "emergency",
            "accident", "injury", "injured", "pain", "chest pain",
            "heart attack", "stroke", "unconscious"
    );

    public IntentResponse classify(String text) {
        long start = System.nanoTime();

        if (text == null || text.isBlank()) {
            return buildResult("ROUTINE", 0.0, null, start);
        }

        String normalized = text.toLowerCase(Locale.ROOT);

        for (String keyword : EMERGENCY_KEYWORDS) {
            if (containsWord(normalized, keyword)) {
                return buildResult("EMERGENCY", 0.99, keyword, start);
            }
        }

        return buildResult("ROUTINE", 0.90, null, start);
    }

    private boolean containsWord(String text, String keyword) {
        return text.matches(".*\\b" + java.util.regex.Pattern.quote(keyword) + "\\b.*");
    }

    private IntentResponse buildResult(String intent, double precision, String keyword, long startNanos) {
        long latencyMs = (System.nanoTime() - startNanos) / 1_000_000;
        return IntentResponse.builder()
                .intent(intent)
                .confidence(precision)
                .matchedKeyword(keyword)
                .latencyMs(latencyMs)
                .build();
    }
}
