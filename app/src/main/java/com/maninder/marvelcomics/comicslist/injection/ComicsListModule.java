package com.maninder.marvelcomics.comicslist.injection;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.comicslist.ComicsListContract;
import com.maninder.marvelcomics.comicslist.filter.FilterFactory;
import com.maninder.marvelcomics.comicslist.usecase.GetComicsList;
import com.maninder.marvelcomics.data.ComicsRepository;
import com.maninder.marvelcomics.threading.UseCaseHandler;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maninder on 14/11/16.
 */

@Module
public class ComicsListModule {

    private final ComicsListContract.View mView;
    private final ComicsRepository mComicsRepository;

    public ComicsListModule(@NonNull ComicsListContract.View view, @NonNull ComicsRepository comicsRepository) {
        mView = view;
        mComicsRepository = comicsRepository;
    }

    @Provides
    ComicsListContract.View provideSetContentsView() {
        return mView;
    }

    @Provides
    UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    @Provides
    GetComicsList provideGetComicsList() {
        return new GetComicsList(mComicsRepository, new FilterFactory());
    }

}
