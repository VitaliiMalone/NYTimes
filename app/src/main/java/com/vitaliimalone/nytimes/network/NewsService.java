package com.vitaliimalone.nytimes.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vitaliimalone.nytimes.model.News;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsService {

    public static final  String BASE_URL = "http://api.nytimes.com/svc/mostpopular/v2/";

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(
                    new TypeToken<List<News>>(){}.getType(),
                    new NewsDeserializer())
            .create();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit retrofit = builder.build();

    private NewsService() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
