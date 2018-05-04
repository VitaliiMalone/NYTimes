package com.vitaliimalone.nytimes.ui.favorites;

import com.vitaliimalone.nytimes.data.News;

import java.util.List;

public interface FavoritesContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showNewsList(List<News> news);
    }

    interface Presenter {

        void loadNews();

        void deleteFromFavorites(News clickedNews);

    }
}
