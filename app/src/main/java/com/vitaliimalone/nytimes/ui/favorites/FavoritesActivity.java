package com.vitaliimalone.nytimes.ui.favorites;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.vitaliimalone.nytimes.R;
import com.vitaliimalone.nytimes.data.NewsDataSource;
import com.vitaliimalone.nytimes.data.NewsRepository;
import com.vitaliimalone.nytimes.data.db.AppDatabase;
import com.vitaliimalone.nytimes.data.News;
import com.vitaliimalone.nytimes.ui.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity implements FavoritesContract.View,
        NewsAdapter.OnItemClickListener, NewsAdapter.OnFavoriteClickListener{

    private FavoritesContract.Presenter presenter;
    private NewsAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar_favorites)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppDatabase db = AppDatabase.getAppDatabase(this);
        NewsDataSource dataSource = new NewsRepository(db);
        presenter = new FavoritesPresenter(dataSource, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(this, new ArrayList<>(), this, this);
        recyclerView.setAdapter(adapter);

        presenter.loadNews();
    }


    @Override
    public void setProgressIndicator(boolean active) {
        if (active) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNewsList(List<News> news) {
        adapter.replaceData(news);
    }

    @Override
    public void onItemClick(News clickedNews) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .setToolbarColor(getResources().getColor(R.color.colorPrimary))
                .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .build();
        customTabsIntent.launchUrl(this, Uri.parse(clickedNews.getUrl()));
    }

    @Override
    public void OnFavoriteClick(News clickedNews) {
        presenter.deleteFromFavorites(clickedNews);
        presenter.loadNews();
    }
}
