package com.maninder.marvelcomics.comicslist;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.comicslist.usecase.GetComicsList;
import com.maninder.marvelcomics.threading.UseCase;
import com.maninder.marvelcomics.threading.UseCaseHandler;

import javax.inject.Inject;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * The presenter of the Comics List View.
 * This crate a communication between Usecase and View.
 * This class listen the user actions from the UI {@link ComicsListFragment}, retrieves the data from UseCase
 * and updates the UI as required
 */
final public class ComicsListPresenter implements ComicsListContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;
    private final ComicsListContract.View mView;
    private final GetComicsList mGetComicsList;

    private ComicsFilterType mCurrentFiltering = ComicsFilterType.ALL_COMICS;
    private double lastPrice = 0.0;


    /**
     * @param view           Represent the current UI and is required to communicate with UseCase
     * @param useCaseHandler Is required if we want to run the UseCase in different Thread, in this way we don't impact user usability
     * @param getComicsList  Usecase that allow to retrieve the Comics List from {@link com.maninder.marvelcomics.data.ComicsRepository}
     */
    @Inject
    public ComicsListPresenter(@NonNull ComicsListContract.View view, @NonNull UseCaseHandler useCaseHandler,
                               @NonNull GetComicsList getComicsList) {
        mUseCaseHandler = useCaseHandler;
        mGetComicsList = getComicsList;
        mView = view;
    }

    @Inject
    public void setupPresenter() {
        mView.setPresenter(this);
    }

    /**
     * This method is called every time we need to get a list of Comics, and when the user set a new Budget.
     */
    @Override
    public void loadComicsContents() {
        final GetComicsList.RequestValues requestValues = new GetComicsList.RequestValues(mCurrentFiltering, lastPrice);
        mUseCaseHandler.execute(mGetComicsList, requestValues, new UseCase.UseCaseCallback<GetComicsList.ResponseValue>() {
            @Override
            public void onSuccess(GetComicsList.ResponseValue response) {
                if (!mView.isActive()) {
                    return;
                }
                mView.showComicsList(response.getComicsList());
                if (mCurrentFiltering == ComicsFilterType.PRICE_COMICS) {
                    //Show the Price the user choose the price filter
                    mView.showTotalPage(response.getPageCount());
                }
                processUI(false, false);
            }

            @Override
            public void onError() {
                if (!mView.isActive()) {
                    return;
                }
                processUI(false, true);

            }
        });
    }

    /**
     * Allow to set the filter choose by User.
     * We have two type of filter:
     * AllComics: show all the list of comics
     * PriceComics: show only the Comics that the user can buy with a fixed budget
     *
     * @param comicsFilterType Type of filtering
     */
    @Override
    public void setFiltering(ComicsFilterType comicsFilterType) {
        mCurrentFiltering = comicsFilterType;
    }

    @Override
    public ComicsFilterType getFiltering() {
        return mCurrentFiltering;
    }

    /**
     * Update the UI
     *
     * @param indicator enable or disable progressbar
     * @param error     enable and disable error message
     */
    private void processUI(boolean indicator, boolean error) {
        mView.stopRefresh();
        mView.setLoadingIndicator(indicator);
        mView.setLoadingError(error);
    }

    @Override
    public void setPrice(double price) {
        lastPrice = price;
    }

    @Override
    public double getPrice() {
        return lastPrice;
    }
}
