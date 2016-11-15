package com.maninder.marvelcomics;

import android.app.Application;

import com.maninder.marvelcomics.data.ComicsRepositoryComponent;
import com.maninder.marvelcomics.data.DaggerComicsRepositoryComponent;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * We create this class to create an singleton reference to the {@link ComicsRepositoryComponent}
 * <p>
 * <p>
 * The Application is made of 3 Dagger Component, as follow:
 * {@link ComicsRepositoryComponent}: the data (it encapsulate db and server data) <BR />
 * {@link com.maninder.marvelcomics.comicslist.injection.DaggerComicsListComponent}: showing the list of Comics <BR />
 * {@link com.maninder.marvelcomics.comicsdetail.injection.DaggerComicsDetailComponent}: showing the Comics Detail <BR />
 */
public class MarvelComicsApplication extends Application {

    private ComicsRepositoryComponent mComicsRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComicsRepositoryComponent = DaggerComicsRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public ComicsRepositoryComponent getComicsRepositoryComponent() {
        return mComicsRepositoryComponent;
    }


}
