package com.example.bhashatranslator.util;
import java.util.HashMap;

public class HinglishConverter {
    private static final HashMap<String, String> MAP = new HashMap<>();
    static {
        // Common phonetic mappings (expand for production)
        MAP.put("kaise", "कैसे"); MAP.put("ho", "हो"); MAP.put("tum", "तुम");
        MAP.put("main", "मैं"); MAP.put("theek", "ठीक"); MAP.put("accha", "अच्छा");
        MAP.put("kyun", "क्यों"); MAP.put("nahi", "नहीं"); MAP.put("haan", "हाँ");
        MAP.put("aap", "आप"); MAP.put("kya", "क्या"); MAP.put("hai", "है");
        MAP.put("tha", "था"); MAP.put("the", "थे"); MAP.put("rahe", "रहे");
        // Add 500+ entries or use IndicNLP library for production
    }

    public static String convertToDevanagari(String hinglish) {
        if (hinglish == null) return "";
        String[] words = hinglish.toLowerCase().trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            result.append(MAP.getOrDefault(w, w)).append(" ");
        }
        return result.toString().trim();
    }
}