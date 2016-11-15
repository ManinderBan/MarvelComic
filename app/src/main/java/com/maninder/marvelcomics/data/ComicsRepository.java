package com.maninder.marvelcomics.data;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.data.remote.model.Comics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * Implementation of the Comics repository that maintain Comics Map
 * <p>
 * Maintains reference to both the repository {@link com.maninder.marvelcomics.data.remote.ComicsRemoteDataSource} and {@link com.maninder.marvelcomics.data.local.ComicsLocalDataSource}
 */
@Singleton
public class ComicsRepository implements ComicsDataSource {

    Map<Integer, Comics> mComicsMap;

    private final ComicsDataSource mComicsLocalDataSource;

    private final ComicsDataSource mComicsRemoteDataSource;

    /**
     * @param comicsLocalDataSource  allow to save data in Local and retrieve when the device is offline
     * @param comicsRemoteDataSource Allow to get data from server
     */
    @Inject
    public ComicsRepository(@Local @NonNull ComicsDataSource comicsLocalDataSource,
                            @Remote @NonNull ComicsDataSource comicsRemoteDataSource) {
        mComicsLocalDataSource = comicsLocalDataSource;
        mComicsRemoteDataSource = comicsRemoteDataSource;
        mComicsMap = new LinkedHashMap<>();
    }

    /**
     * Call the remote Data source, if the device is offline than try to get Data from local repository
     *
     * @param loadComicsListCallback
     */
    @Override
    public void getComicsList(@NonNull final LoadComicsListCallback loadComicsListCallback) {
        if (mComicsMap != null && mComicsMap.size() > 0) {
            loadComicsListCallback.onComicsListLoaded(new ArrayList<Comics>(mComicsMap.values()));
            return;
        }
        mComicsRemoteDataSource.getComicsList(new LoadComicsListCallback() {
            @Override
            public void onComicsListLoaded(List<Comics> comicsList) {
                loadComicsListCallback.onComicsListLoaded(comicsList);
                refreshLocalDataSource(comicsList);
                refreshSetMap(comicsList);
            }

            @Override
            public void onDataNotAvailable() {
                getDataFromLocal(loadComicsListCallback);
            }
        });
    }

    /**
     * Get data from Local if backend don't respond
     *
     * @param loadComicsListCallback
     */
    private void getDataFromLocal(@NonNull final LoadComicsListCallback loadComicsListCallback) {
        mComicsLocalDataSource.getComicsList(new LoadComicsListCallback() {
            @Override
            public void onComicsListLoaded(List<Comics> comicsList) {
                loadComicsListCallback.onComicsListLoaded(comicsList);
                //Populate the HashMap when we have the data
                refreshSetMap(comicsList);
            }

            @Override
            public void onDataNotAvailable() {
                loadComicsListCallback.onDataNotAvailable();
            }
        });

    }

    /**
     * Get a single Comics from HashMap that maintains all the Comics list by ComicsID as a KEY
     *
     * @param loadComicsCallback
     * @param id Comics ID
     */
    @Override
    public void getComics(@NonNull LoadComicsCallback loadComicsCallback, int id) {
        if (mComicsMap.containsKey(id)) {
            loadComicsCallback.onComicsLoaded(mComicsMap.get(id));
        } else {
            loadComicsCallback.onDataNotAvailable();
        }

    }

    @Override
    public void saveComicsList(@NonNull List<Comics> comicsList) {

    }

    /**
     * Update the data with new data from server
     *
     * @param comicsList data retrieved from server
     */
    private void refreshSetMap(@NonNull List<Comics> comicsList) {
        if (mComicsMap == null) {
            mComicsMap = new LinkedHashMap<>();
        }
        mComicsMap.clear();
        for (Comics comics : comicsList) {
            mComicsMap.put(comics.id, comics);
        }
    }

    /**
     * Save Data in local when we get new data from server
     * @param comicsList List of Comics to save in Local Repository
     */
    private void refreshLocalDataSource(@NonNull List<Comics> comicsList) {
        mComicsLocalDataSource.saveComicsList(comicsList);
    }
}
