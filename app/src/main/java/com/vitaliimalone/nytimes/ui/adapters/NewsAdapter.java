package com.vitaliimalone.nytimes.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vitaliimalone.nytimes.R;
import com.vitaliimalone.nytimes.data.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<News> news;
    private OnItemClickListener listener;
    private OnFavoriteClickListener favListener;

    public interface OnItemClickListener {
        void onItemClick(News clickedNews);
    }

    public interface OnFavoriteClickListener {
        void OnFavoriteClick(News clickedNews);
    }

    public NewsAdapter(Context context, List<News> news, OnItemClickListener listener, OnFavoriteClickListener favListener) {
        this.context = context;
        this.news = news;
        this.listener = listener;
        this.favListener = favListener;
    }

    public void replaceData(List<News> news) {
        this.news = news;
        notifyDataSetChanged();
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

        if (currentNews.isFavorite()) {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red_24dp);
        }

        holder.favoriteButton.setOnClickListener(v -> {
            if (!currentNews.isFavorite()){
                holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red_24dp);
                currentNews.setFavorite(true);
            } else {
                holder.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                currentNews.setFavorite(false);
            }
            favListener.OnFavoriteClick(currentNews);
        });

        holder.itemView.setOnClickListener(v -> listener.onItemClick(currentNews));
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
