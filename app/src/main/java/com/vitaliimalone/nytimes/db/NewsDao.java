package com.vitaliimalone.nytimes.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.vitaliimalone.nytimes.model.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<News> news);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(News news);

    @Query("SELECT * FROM news")
    List<News> getAll();

    @Query("SELECT * FROM news WHERE isFavorite = 1")
    List<News> getFavorites();

    @Update
    void updateNews(News news);

    @Delete
    void delete(News news);

    @Query("DELETE FROM news WHERE isFavorite = 0")
    void deleteAllNonFavorite();

    @Query("DELETE FROM news")
    void deleteAll();
}
