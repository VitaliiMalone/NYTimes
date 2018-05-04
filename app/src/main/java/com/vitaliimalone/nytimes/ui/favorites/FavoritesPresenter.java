package com.vitaliimalone.nytimes.ui.favorites;

import com.vitaliimalone.nytimes.data.News;
import com.vitaliimalone.nytimes.data.NewsDataSource;

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private final NewsDataSource dataSource;
    private final FavoritesContract.View view;

    public FavoritesPresenter(NewsDataSource dataSource, FavoritesContract.View view) {
        this.dataSource = dataSource;
        this.view = view;
    }

    @Override
    public void loadNews() {
        view.setProgressIndicator(true);
        dataSource.getNewsFromDb(news -> {
            view.setProgressIndicator(false);
            view.showNewsList(news);
        });
    }

    @Override
    public void deleteFromFavorites(News clickedNews) {
        dataSource.deleteNews(clickedNews);
    }
}
