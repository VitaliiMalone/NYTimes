package com.vitaliimalone.nytimes.network;

import com.vitaliimalone.nytimes.model.News;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("{mostPath}/all-sections/30")
    Flowable<List<News>> getMostPopularNews(@Path("mostPath") String mostPath,
                                            @Query("api-key") String apiKey);

}
