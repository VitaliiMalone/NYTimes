package com.vitaliimalone.nytimes.ui.newslist;

import com.vitaliimalone.nytimes.data.News;

import java.util.List;

public interface NewsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showNewsList(List<News> news);

        void showNetworkError();

    }

    interface Presenter {

        void loadNews(String mostPopularPath, String apiKey);

        void addOrDeleteFromFavorites(News clickedNews);

    }
}
