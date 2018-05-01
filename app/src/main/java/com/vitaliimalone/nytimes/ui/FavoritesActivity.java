package com.vitaliimalone.nytimes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.vitaliimalone.nytimes.R;
import com.vitaliimalone.nytimes.adapter.NewsAdapter;
import com.vitaliimalone.nytimes.db.AppDatabase;
import com.vitaliimalone.nytimes.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_favorites)
    Toolbar toolbar;

    private AppDatabase db;
    private List<News> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = AppDatabase.getAppDatabase(this);

        updateUi();
    }

    private void updateUi() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        news = db.newsDao().getFavorites();
        recyclerView.setAdapter(new NewsAdapter(news, this));
    }

}
