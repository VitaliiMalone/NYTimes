package com.vitaliimalone.nytimes.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vitaliimalone.nytimes.R;
import com.vitaliimalone.nytimes.db.AppDatabase;
import com.vitaliimalone.nytimes.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> news;
    private Context context;
    private AppDatabase db;

    public NewsAdapter(List<News> news, Context context) {
        this.news = news;
        this.context = context;
        db = AppDatabase.getAppDatabase(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News currentNews = news.get(position);

        holder.titleTv.setText(currentNews.getTitle());
        holder.descriptionTv.setText(currentNews.getDescription());
        holder.sectionTv.setText(currentNews.getSection());
        holder.dateTv.setText(currentNews.getDate());

        String imageUrl = currentNews.getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageIv);

        String url = currentNews.getUrl();
        holder.itemView.setOnClickListener(v -> {
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(context.getResources().getColor(R.color.colorPrimary))
                    .build();
            customTabsIntent.launchUrl(context, Uri.parse(url));
        });

        if (currentNews.isFavorite()) {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red_24dp);
        }
        holder.favoriteButton.setOnClickListener(v -> {
            if (!currentNews.isFavorite()) {
                currentNews.setFavorite(true);
                db.newsDao().insertNews(currentNews);

                holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red_24dp);
                Toast.makeText(context, "Article added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                currentNews.setFavorite(false);
                db.newsDao().delete(currentNews);

                holder.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                Toast.makeText(context, "Article removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_item)
        ImageView imageIv;
        @BindView(R.id.title_item)
        TextView titleTv;
        @BindView(R.id.description_item)
        TextView descriptionTv;
        @BindView(R.id.section_item)
        TextView sectionTv;
        @BindView(R.id.date_item)
        TextView dateTv;
        @BindView(R.id.favorite_item_button)
        ImageButton favoriteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
