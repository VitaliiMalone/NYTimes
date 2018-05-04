package com.vitaliimalone.nytimes.data;

import com.vitaliimalone.nytimes.data.db.AppDatabase;
import com.vitaliimalone.nytimes.data.network.NewsApi;
import com.vitaliimalone.nytimes.data.network.NewsService;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsRepository implements NewsDataSource {

    private AppDatabase db;
    private List<News> cachedNews;

    public NewsRepository(AppDatabase db) {
        this.db = db;
    }


    @Override
    public void getNews(String mostPopularPath, String apiKey, GetNewsCallback callback) {
        NewsApi newsApi = NewsService.createService(NewsApi.class);
        Observable<List<News>> call = newsApi.getMostPopularNews(mostPopularPath, apiKey);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onNewsLoaded);


    }

    @Override
    public void getNewsFromDb(GetNewsCallback callback) {
        callback.onNewsLoaded(db.newsDao().getAll());
    }

    @Override
    public void saveNews(News savedNews) {
        db.newsDao().insertNews(savedNews);
    }

    @Override
    public void deleteNews(News deletedNews) {
        db.newsDao().delete(deletedNews);
    }
}
