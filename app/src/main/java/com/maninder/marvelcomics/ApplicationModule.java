package com.maninder.marvelcomics;

/**
 * Created by Maninder on 14/11/16.
 */


import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Used to Pass the Context dependency to the
 * {@link com.maninder.marvelcomics.data.ComicsRepositoryComponent}.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
