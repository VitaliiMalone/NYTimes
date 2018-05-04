package com.vitaliimalone.nytimes.ui.newslist;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vitaliimalone.nytimes.R;
import com.vitaliimalone.nytimes.data.News;
import com.vitaliimalone.nytimes.data.NewsDataSource;
import com.vitaliimalone.nytimes.data.NewsRepository;
import com.vitaliimalone.nytimes.data.db.AppDatabase;
import com.vitaliimalone.nytimes.ui.adapters.NewsAdapter;
import com.vitaliimalone.nytimes.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsFragment extends Fragment implements NewsContract.View,
        NewsAdapter.OnItemClickListener, NewsAdapter.OnFavoriteClickListener {

    private static final String TAG = "NewsFragment";
    private static final String ARG_MOST_POPULAR = "mostPopularPath";

    private String mostPopularPath;
    private NewsContract.Presenter presenter;
    private NewsAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public NewsFragment() {
    }

    public static Fragment newInstance(String mostPopularPath) {
        Bundle args = new Bundle();
        args.putString(ARG_MOST_POPULAR, mostPopularPath);

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_MOST_POPULAR)) {
            mostPopularPath = getArguments().getString(ARG_MOST_POPULAR);
        }

        presenter = new NewsPresenter(new NewsRepository(getContext()), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), new ArrayList<>(), this, this);
        recyclerView.setAdapter(adapter);

        if (NetworkUtils.isOnline(getContext())) {
            presenter.loadNews(mostPopularPath, getResources().getString(R.string.api_key));
        } else {
            showNetworkError();
        }

        return view;
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNewsList(List<News> news) {
        adapter.replaceData(news);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getContext(), R.string.error_loading_data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(News clickedNews) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .setToolbarColor(getResources().getColor(R.color.colorPrimary))
                .setStartAnimations(getContext(), R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(getContext(), android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .build();
        customTabsIntent.launchUrl(getContext(), Uri.parse(clickedNews.getUrl()));
    }


    @Override
    public void OnFavoriteClick(News clickedNews) {
        presenter.addOrDeleteFromFavorites(clickedNews);
    }
}
