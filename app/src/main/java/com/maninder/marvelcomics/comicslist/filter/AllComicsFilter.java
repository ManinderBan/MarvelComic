package com.maninder.marvelcomics.comicslist.filter;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.data.remote.model.Comics;

import java.util.List;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * Show all the The list of Comics
 */
public class AllComicsFilter implements ComicsFilter {

    @Override
    public List<Comics> filter(@NonNull List<Comics> comicsList, @NonNull double price) {
        return comicsList;
    }

    @Override
    public int getPageCount() {
        return 0;
    }
}
