package com.maninder.marvelcomics.comicsdetail.injection;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.comicsdetail.ComicsDetailContract;
import com.maninder.marvelcomics.comicsdetail.usecase.GetComicsDetail;
import com.maninder.marvelcomics.data.ComicsRepository;
import com.maninder.marvelcomics.threading.UseCaseHandler;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maninder on 15/11/16.
 */
@Module
public class ComicsDetailModule {


    private final ComicsDetailContract.View mView;
    private final ComicsRepository mComicsRepository;

    public ComicsDetailModule(@NonNull ComicsDetailContract.View view, @NonNull ComicsRepository comicsRepository) {
        mView = view;
        mComicsRepository = comicsRepository;
    }

    @Provides
    ComicsDetailContract.View provideComicsDeatilView() {
        return mView;
    }

    @Provides
    UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    @Provides
    GetComicsDetail provideGetComicsDetail() {
        return new GetComicsDetail(mComicsRepository);
    }
}
