package com.maninder.marvelcomics.comicsdetail;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.comicsdetail.usecase.GetComicsDetail;
import com.maninder.marvelcomics.threading.UseCase;
import com.maninder.marvelcomics.threading.UseCaseHandler;

import javax.inject.Inject;

/**
 * Created by Maninder on 15/11/16.
 */

/**
 * The presenter of the Comics List View.
 * This crate a communication between Usecase and View.
 * This class listen the user actions from the UI {@link ComicsDetailFragment}, retrieves the data from UseCase
 * and updates the UI as required
 */
final public class ComicsDetailPresenter implements ComicsDetailContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;
    private final ComicsDetailContract.View mView;
    private final GetComicsDetail mGetComicsDetail;

    private int comicsID;

    /**
     * @param useCaseHandler  Is required if we want to run the UseCase in different Thread, in this way we don't impact user usability
     * @param view            Represent the current UI and is required to communicate with UseCase
     * @param getComicsDetail Usecase that allow to retrieve the Comics information from {@link com.maninder.marvelcomics.data.ComicsRepository}
     */
    @Inject
    public ComicsDetailPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull ComicsDetailContract.View view,
                                 @NonNull GetComicsDetail getComicsDetail) {
        mView = view;
        mUseCaseHandler = useCaseHandler;
        mGetComicsDetail = getComicsDetail;
        comicsID = 0;
    }

    @Inject
    public void setupPresenter() {
        mView.setPresenter(this);
    }

    /**
     * Load the Comics from Repository and send to the View
     */
    @Override
    public void loadComics() {
        GetComicsDetail.RequestValues requestValues = new GetComicsDetail.RequestValues(comicsID);
        mUseCaseHandler.execute(mGetComicsDetail, requestValues, new UseCase.UseCaseCallback<GetComicsDetail.ResponseValue>() {
            @Override
            public void onSuccess(GetComicsDetail.ResponseValue response) {
                if (!mView.isActive()) {
                    return;
                }
                mView.showComics(response.getComics());
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


    @Override
    public void setComicsID(int comicsID) {
        this.comicsID = comicsID;
    }

    @Override
    public int getComicsID() {
        return comicsID;
    }

    private void processUI(boolean indicator, boolean error) {
        mView.setLoadingIndicator(indicator);
        mView.setLoadingError(error);
    }
}
