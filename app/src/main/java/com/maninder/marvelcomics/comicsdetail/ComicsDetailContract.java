package com.maninder.marvelcomics.comicsdetail;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.BasePresenter;
import com.maninder.marvelcomics.BaseView;
import com.maninder.marvelcomics.data.remote.model.Comics;

/**
 * Created by Maninder on 15/11/16.
 */

public interface ComicsDetailContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void setLoadingError(boolean active);

        boolean isActive();

        void showComics(@NonNull Comics comics);

    }

    interface Presenter extends BasePresenter {

        void loadComics();

        void setComicsID(int comicsID);

        int getComicsID();
    }
}

