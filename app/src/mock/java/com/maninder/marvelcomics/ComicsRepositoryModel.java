package com.maninder.marvelcomics;

import android.content.Context;

import com.maninder.marvelcomics.data.ComicsDataSource;
import com.maninder.marvelcomics.data.FakeComicsRemoteDataSource;
import com.maninder.marvelcomics.data.Local;
import com.maninder.marvelcomics.data.Remote;
import com.maninder.marvelcomics.data.local.ComicsLocalDataSource;
import com.maninder.marvelcomics.data.remote.ComicsRemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Maninder on 14/11/16.
 */
@Module
public class ComicsRepositoryModel {

    @Singleton
    @Provides
    @Remote
    ComicsDataSource provideComicsRemoteDataSource() {
//        return new FakeComicsRemoteDataSource();
        return new ComicsRemoteDataSource();
    }

    @Singleton
    @Provides
    @Local
    ComicsDataSource provideComicsLocalDataSource(Context context) {
        return new ComicsLocalDataSource(context);
    }
}
