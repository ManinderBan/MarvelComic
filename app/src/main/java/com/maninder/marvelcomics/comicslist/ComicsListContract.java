package com.maninder.marvelcomics.comicslist;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.BasePresenter;
import com.maninder.marvelcomics.BaseView;
import com.maninder.marvelcomics.data.remote.model.Comics;

import java.util.List;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * This is the Contract between View and Presenter.
 */
public interface ComicsListContract {

    interface View extends BaseView<Presenter> {
        void showComicsList(@NonNull List<Comics> comicsList);

        void showCoimcs(int id);

        void showTotalPage(int numberPage);

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void setLoadingError(boolean active);

        void stopRefresh();
    }

    interface Presenter extends BasePresenter {
        void loadComicsContents();

        void setFiltering(ComicsFilterType comicsFilterType);

        ComicsFilterType getFiltering();

        void setPrice(double price);

        double getPrice();
    }
}
