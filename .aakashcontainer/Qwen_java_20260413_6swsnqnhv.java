package com.example.bhashatranslator.data.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.example.bhashatranslator.data.model.TranslationResponse;
import com.example.bhashatranslator.data.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslationRepository {
    public MutableLiveData<TranslationResponse> translate(String text, String from, String to) {
        MutableLiveData<TranslationResponse> liveData = new MutableLiveData<>();
        Call<TranslationResponse> call = RetrofitClient.getInstance().translate(text, from, to);
        call.enqueue(new Callback<TranslationResponse>() {
            @Override public void onResponse(Call<TranslationResponse> c, Response<TranslationResponse> r) {
                liveData.setValue(r.isSuccessful() ? r.body() : null);
            }
            @Override public void onFailure(Call<TranslationResponse> c, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}