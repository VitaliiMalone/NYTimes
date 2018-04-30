package com.vitaliimalone.nytimes.network;

import com.vitaliimalone.nytimes.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    String BASE_URL = "http://api.nytimes.com/svc/mostpopular/v2/";

    @GET("{mostPath}/all-sections/30")
    Call<List<News>> getMostPopularNews(@Path("mostPath") String mostPath,
                                        @Query("api-key") String apiKey);

}
