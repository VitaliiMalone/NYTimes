package com.vitaliimalone.nytimes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vitaliimalone.nytimes.R;
import com.vitaliimalone.nytimes.adapter.NewsAdapter;
import com.vitaliimalone.nytimes.model.News;
import com.vitaliimalone.nytimes.network.NewsApi;
import com.vitaliimalone.nytimes.network.NewsService;
import com.vitaliimalone.nytimes.util.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private static final String ARG_MOST_POPULAR = "mostPopularPath";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private String mostPopularPath;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        updateUi();

        return view;
    }

    private void updateUi() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (NetworkUtils.isOnline(getContext())) {
            String apiKey = getString(R.string.api_key);

            NewsApi newsApi = NewsService.createService(NewsApi.class);
            Flowable<List<News>> call = newsApi.getMostPopularNews(mostPopularPath, apiKey);

            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(news -> recyclerView.setAdapter(new NewsAdapter(news, getContext())),
                            throwable -> Log.e(TAG, "updateUi: network error", throwable));

        } else {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

}
