package com.maninder.marvelcomics.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maninder.marvelcomics.data.ComicsDataSource;
import com.maninder.marvelcomics.data.remote.model.Comics;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 14/11/16.
 */
@Singleton
public class ComicsLocalDataSource implements ComicsDataSource {

    private final static String COMICS = "comics";
    private Context mContext;

    public ComicsLocalDataSource(@NonNull Context context) {
        mContext = checkNotNull(context);
    }

    /**
     * save the Comics list in {@link SharedPreferences} by converting in JSON format
     * NOTE: Comics is a Parcelable
     *
     * @param comicsList List of Comics to save
     */
    @Override
    public void saveComicsList(@NonNull List<Comics> comicsList) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(comicsList);
        editor.putString(COMICS, json);
        editor.apply();
    }

    /**
     * Get the Set List saved in {@link SharedPreferences}, otherwise get return {@link LoadComicsListCallback#onDataNotAvailable()}
     *
     * @param loadComicsListCallback Callback to get the list of comics or error
     */
    @Override
    public void getComicsList(@NonNull LoadComicsListCallback loadComicsListCallback) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (sharedPreferences.contains(COMICS)) {
            String json = sharedPreferences.getString(COMICS, "");
            Type type = new TypeToken<List<Comics>>() {
            }.getType();
            List<Comics> setLists = new Gson().fromJson(json, type);
            loadComicsListCallback.onComicsListLoaded(setLists);
        } else {
            loadComicsListCallback.onDataNotAvailable();
        }
    }

    @Override
    public void getComics(@NonNull LoadComicsCallback loadComicsCallback, int id) {

    }
}