package com.vitaliimalone.nytimes.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.vitaliimalone.nytimes.data.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(News news);

    @Query("SELECT * FROM news")
    List<News> getAll();

    @Query("SELECT * FROM news WHERE isFavorite = 1")
    List<News> getFavorites();

    @Delete
    void delete(News news);
}
