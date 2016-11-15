package com.maninder.marvelcomics.comicslist.filter;

import com.maninder.marvelcomics.comicslist.ComicsFilterType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * This Class maintain the reference of the two Filer.
 */
public class FilterFactory {
    private static final Map<ComicsFilterType, ComicsFilter> mFilters = new HashMap<>();

    public FilterFactory() {
        mFilters.put(ComicsFilterType.ALL_COMICS, new AllComicsFilter());
        mFilters.put(ComicsFilterType.PRICE_COMICS, new PriceComicsFilter());
    }

    public ComicsFilter create(ComicsFilterType filterType) {
        return mFilters.get(filterType);
    }
}
