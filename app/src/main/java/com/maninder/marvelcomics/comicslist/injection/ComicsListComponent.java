package com.maninder.marvelcomics.comicslist.injection;

import com.maninder.marvelcomics.comicslist.MainActivity;
import com.maninder.marvelcomics.util.FragmentScoped;

import dagger.Component;

/**
 * Created by Maninder on 14/11/16.
 */
@FragmentScoped
@Component(modules = ComicsListModule.class)
public interface ComicsListComponent {
    void inject(MainActivity activity);
}
