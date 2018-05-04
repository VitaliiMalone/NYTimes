package com.vitaliimalone.nytimes.data.network;

import com.vitaliimalone.nytimes.data.News;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("{mostPath}/all-sections/30")
    Observable<List<News>> getMostPopularNews(@Path("mostPath") String mostPath,
                                              @Query("api-key") String apiKey);

}
