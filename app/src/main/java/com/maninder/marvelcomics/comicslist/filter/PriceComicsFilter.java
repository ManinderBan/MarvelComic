package com.maninder.marvelcomics.comicslist.filter;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.data.remote.model.Comics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * This Class is a Filter and allow to retrieve the List of Comics with a specific budget
 */
public class PriceComicsFilter implements ComicsFilter {

    //Page Count: number of page the the user can read with a specific budget
    private int numberPage = 0;

    @Override
    public List<Comics> filter(@NonNull List<Comics> comicsList, @NonNull double price) {
        numberPage = 0;
        List<Comics> filteredComics = new ArrayList<>();
        double incPrice = 0;
        for (int i = 0; i < comicsList.size(); i++) {
            incPrice = incPrice + comicsList.get(i).prices.price;
            if (incPrice <= price) {
                numberPage = numberPage + comicsList.get(i).pageCount;
                filteredComics.add(comicsList.get(i));
            }
        }
        return filteredComics;
    }

    @Override
    public int getPageCount() {
        return numberPage;
    }
}
