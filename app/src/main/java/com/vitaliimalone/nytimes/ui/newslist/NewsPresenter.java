package com.vitaliimalone.nytimes.ui.newslist;


import com.vitaliimalone.nytimes.data.News;
import com.vitaliimalone.nytimes.data.NewsDataSource;

public class NewsPresenter implements NewsContract.Presenter {

    private final NewsDataSource dataSource;
    private final NewsContract.View view;

    public NewsPresenter(NewsDataSource dataSource, NewsContract.View view) {
        this.dataSource = dataSource;
        this.view = view;
    }


    @Override
    public void loadNews(String mostPopularPath, String apiKey) {
        view.setProgressIndicator(true);
        dataSource.getNews(mostPopularPath, apiKey, news -> {
            view.setProgressIndicator(false);
            view.showNewsList(news);
        });
    }

    @Override
    public void addOrDeleteFromFavorites(News clickedNews) {
        if (clickedNews.isFavorite()) {
            dataSource.saveNews(clickedNews);
        } else {
            dataSource.deleteNews(clickedNews);
        }
    }
}
