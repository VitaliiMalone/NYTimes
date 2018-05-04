package com.vitaliimalone.nytimes.data;

import java.util.List;

public interface NewsDataSource {

    interface GetNewsCallback {

        void onNewsLoaded(List<News> news);

    }

    void getNews(String mostPopularPath, String apiKey, GetNewsCallback callback);

    void getNewsFromDb(GetNewsCallback callback);

    void saveNews(News savedNews);

    void deleteNews(News deletedNews);

}
