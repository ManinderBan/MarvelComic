package com.maninder.marvelcomics.comicsdetail.injection;

import com.maninder.marvelcomics.comicsdetail.ComicsDetailActivity;
import com.maninder.marvelcomics.util.FragmentScoped;

import dagger.Component;

/**
 * Created by Maninder on 15/11/16.
 */

@FragmentScoped
@Component(modules = ComicsDetailModule.class)
public interface ComicsDetailComponent {
    void inject(ComicsDetailActivity activity);
}
