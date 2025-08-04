package com.aome.todoist_mp_api.converter;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TaskParameterParser {
    private final Map<String, String> params;

    public TaskParameterParser(String content, String description) {
        if (containsMarker(content)) {
            params = parseParams(content);
        }else if (containsMarker(description)) {
            params = parseParams(description);
        } else {
            params = new HashMap<>();
        }
    }

    private Map<String, String> parseParams(String input) {
        Pattern outerPattern = Pattern.compile("\\$\\{([^}]+)}"); // ищет ${...}
        Matcher outerMatcher = outerPattern.matcher(input);

        Map<String, String> result = new HashMap<>();

        if (outerMatcher.find()) {
            String paramsString = outerMatcher.group(1); // всё внутри ${...}
            String[] params = paramsString.split(",");

            for (String param : params) {
                String[] keyValue = param.trim().split("=", 2);
                if (keyValue.length == 2) {
                    result.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        }

        return result;
    }

    private boolean containsMarker(String input) {
        Pattern pattern = Pattern.compile("\\$\\{[^}]+}"); // ищет ${...}
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public int getMp() {
        String mpValue = params.get("mp");
        if (mpValue != null) {
            try {
                return Integer.parseInt(mpValue);
            } catch (NumberFormatException e) {
                log.warn("Invalid MP value: {}", mpValue, e);
            }
        }
        return 0;
    }
}
