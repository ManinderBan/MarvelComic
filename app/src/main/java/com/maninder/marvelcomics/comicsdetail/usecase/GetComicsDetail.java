package com.maninder.marvelcomics.comicsdetail.usecase;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.data.ComicsDataSource;
import com.maninder.marvelcomics.data.ComicsRepository;
import com.maninder.marvelcomics.data.remote.model.Comics;
import com.maninder.marvelcomics.threading.UseCase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 15/11/16.
 */

public class GetComicsDetail extends UseCase<GetComicsDetail.RequestValues, GetComicsDetail.ResponseValue> {


    private final ComicsRepository mComicsRepository;

    /**
     * @param comicsRepository the repository used to get data from Local, if available, or from Remote
     */
    public GetComicsDetail(@NonNull ComicsRepository comicsRepository) {
        mComicsRepository = checkNotNull(comicsRepository);
    }

    /**
     * @param requestValues is the request to the {@link ComicsRepository}
     */
    @Override
    protected void executeUseCase(final GetComicsDetail.RequestValues requestValues) {
        mComicsRepository.getComics(new ComicsDataSource.LoadComicsCallback() {
            @Override
            public void onComicsLoaded(Comics comics) {
                ResponseValue responseValue = new ResponseValue(comics);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        }, requestValues.getComicsID());
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final int mComicsID;

        public RequestValues(int comicsID) {
            mComicsID = comicsID;
        }

        public int getComicsID() {
            return mComicsID;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Comics mComics;

        public ResponseValue(@NonNull Comics comics) {
            mComics = comics;
        }

        public Comics getComics() {
            return mComics;
        }
    }
}
