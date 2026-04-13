// TranslationApi.java
package com.example.bhashatranslator.data.remote;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.example.bhashatranslator.data.model.TranslationResponse;
public interface TranslationApi {
    @POST("translate")
    Call<TranslationResponse> translate(
        @Query("q") String q,
        @Query("source") String source,
        @Query("target") String target
    );
}

// RetrofitClient.java
package com.example.bhashatranslator.data.remote;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static final String BASE_URL = "https://libretranslate.de/"; // Change to your instance
    private static RetrofitClient instance;
    private TranslationApi api;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        api = retrofit.create(TranslationApi.class);
    }
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) instance = new RetrofitClient();
        return instance;
    }
    public TranslationApi translate() { return api; }
}

// TranslationResponse.java
package com.example.bhashatranslator.data.model;
public class TranslationResponse {
    @com.google.gson.annotations.SerializedName("translatedText")
    private String translatedText;
    public String getTranslatedText() { return translatedText; }
}