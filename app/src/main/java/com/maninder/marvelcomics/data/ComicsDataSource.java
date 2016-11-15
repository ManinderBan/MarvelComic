package com.maninder.marvelcomics.data;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.data.remote.model.Comics;

import java.util.List;

/**
 * Created by Maninder on 14/11/16.
 */

public interface ComicsDataSource {

    interface LoadComicsListCallback {
        void onComicsListLoaded(List<Comics> comicsList);

        void onDataNotAvailable();
    }

    interface LoadComicsCallback {

        void onComicsLoaded(Comics comics);

        void onDataNotAvailable();
    }

    void saveComicsList(@NonNull List<Comics> comicsList);

    void getComicsList(@NonNull LoadComicsListCallback loadComicsListCallback);

    void getComics(@NonNull LoadComicsCallback loadComicsCallback, int id);

}
