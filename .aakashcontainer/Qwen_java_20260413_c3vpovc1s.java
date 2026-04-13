package com.example.bhashatranslator.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.bhashatranslator.data.repository.TranslationRepository;
import java.util.ArrayList;
import java.util.Locale;

public class TranslationViewModel extends AndroidViewModel implements TextToSpeech.OnInitListener {
    public MutableLiveData<String> inputText = new MutableLiveData<>("");
    public MutableLiveData<String> outputText = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    public MutableLiveData<String> detectedLang = new MutableLiveData<>("Unknown");
    public MutableLiveData<String> error = new MutableLiveData<>(null);

    private TextToSpeech tts;
    private SpeechRecognizer speechRecognizer;
    private final TranslationRepository repository;
    private SharedPreferences prefs;

    public TranslationViewModel(Application app) {
        super(app);
        repository = new TranslationRepository(app);
        prefs = app.getSharedPreferences("app_prefs", 0);
        tts = new TextToSpeech(app, this);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(app);
    }

    @Override public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) tts.setLanguage(new Locale("hi"));
    }

    public void translate(String input, String fromCode, String toCode) {
        boolean isHinglish = prefs.getBoolean("hinglish_mode", false);
        if ("hi".equals(toCode) && isHinglish) {
            input = HinglishConverter.convertToDevanagari(input);
            inputText.setValue(input);
        }

        isLoading.setValue(true);
        repository.translate(input, fromCode, toCode)
            .observeForever(res -> {
                isLoading.setValue(false);
                if (res != null) outputText.setValue(res.getTranslatedText());
                else error.setValue("Translation failed");
            });
    }

    public void speakText(String text, String langCode) {
        if (tts != null && text != null) {
            Locale locale = new Locale(langCode);
            tts.setLanguage(locale);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS");
        }
    }

    public void startListening() {
        if (SpeechRecognizer.isRecognitionAvailable(getApplication())) {
            ArrayList<String> result = speechRecognizer.getResults();
            if (result != null && !result.isEmpty()) {
                inputText.setValue(result.get(0));
                translate(result.get(0), "auto", prefs.getString("to_code", "hi"));
            }
        }
    }

    @Override protected void onCleared() {
        super.onCleared();
        if (tts != null) tts.shutdown();
        if (speechRecognizer != null) speechRecognizer.destroy();
    }
}