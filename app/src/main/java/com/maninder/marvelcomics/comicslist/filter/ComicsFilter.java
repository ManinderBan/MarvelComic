package com.maninder.marvelcomics.comicslist.filter;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.data.remote.model.Comics;

import java.util.List;

/**
 * Created by Maninder on 14/11/16.
 */

public interface ComicsFilter {

    List<Comics> filter(@NonNull List<Comics> comicsList, @NonNull double price);

    int getPageCount();
}
