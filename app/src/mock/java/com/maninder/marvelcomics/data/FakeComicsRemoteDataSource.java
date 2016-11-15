package com.maninder.marvelcomics.data;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.maninder.marvelcomics.data.remote.model.Comics;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maninder on 15/11/16.
 */

/**
 * Fake Implementation of the remote data source. This Class is only for Test the Application with Mockito
 * NOTE: Remember to inject this class before to test the Application
 */
public class FakeComicsRemoteDataSource implements ComicsDataSource {

    private static final Map<Integer, Comics> COMICS_MAP_DAT = new LinkedHashMap<>();

    public FakeComicsRemoteDataSource() {
    }

    @Override
    public void saveComicsList(@NonNull List<Comics> comicsList) {

    }

    @Override
    public void getComicsList(@NonNull LoadComicsListCallback loadComicsListCallback) {
        loadComicsListCallback.onComicsListLoaded(Lists.newArrayList(COMICS_MAP_DAT.values()));
    }

    @Override
    public void getComics(@NonNull LoadComicsCallback loadComicsCallback, int id) {
        loadComicsCallback.onComicsLoaded(new Comics());
    }
}
