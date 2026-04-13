package com.example.bhashatranslator.util;

public class LanguageUtils {
    public static final String[] LANGUAGE_NAMES = {
        "English", "Hindi", "Bengali", "Telugu", "Marathi", "Tamil", "Urdu",
        "Gujarati", "Kannada", "Odia", "Malayalam", "Punjabi", "Assamese",
        "Maithili", "Santali", "Kashmiri", "Nepali", "Konkani", "Sindhi",
        "Dogri", "Manipuri", "Bodo", "Tulu", "Bhojpuri", "Rajasthani",
        "Haryanvi", "Chhattisgarhi", "Garhwali"
    };
    public static final String[] LANGUAGE_CODES = {
        "en", "hi", "bn", "te", "mr", "ta", "ur", "gu", "kn", "or", "ml",
        "pa", "as", "mai", "sat", "ks", "ne", "gom", "sd", "doi", "mni",
        "brx", "tcy", "bho", "raj", "hi", "hi", "hi" // Note: LibreTranslate may fallback unsupported codes to 'hi'/'en'
    };
    public static final String AUTO_DETECT_CODE = "auto";
}