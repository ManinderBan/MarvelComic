package com.maninder.marvelcomics.comicslist.usecase;

import android.support.annotation.NonNull;

import com.maninder.marvelcomics.comicslist.ComicsFilterType;
import com.maninder.marvelcomics.comicslist.filter.ComicsFilter;
import com.maninder.marvelcomics.comicslist.filter.FilterFactory;
import com.maninder.marvelcomics.data.ComicsDataSource;
import com.maninder.marvelcomics.data.ComicsRepository;
import com.maninder.marvelcomics.data.remote.model.Comics;
import com.maninder.marvelcomics.threading.UseCase;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * This a Use case the run in a thread and allow to get the List of Comics with a specific Filter
 */
public class GetComicsList extends UseCase<GetComicsList.RequestValues, GetComicsList.ResponseValue> {


    private final ComicsRepository mComicsRepository;

    private final FilterFactory mFilterFactory;

    /**
     * @param comicsRepository the repository used to get data from Local, if available, or from Remote
     */
    public GetComicsList(@NonNull ComicsRepository comicsRepository, @NonNull FilterFactory filterFactory) {
        mComicsRepository = checkNotNull(comicsRepository);
        mFilterFactory = filterFactory;
    }

    /**
     * @param requestValues is the request to the {@link ComicsRepository}
     */
    @Override
    protected void executeUseCase(final GetComicsList.RequestValues requestValues) {
        mComicsRepository.getComicsList(new ComicsDataSource.LoadComicsListCallback() {
            @Override
            public void onComicsListLoaded(List<Comics> comicsList) {
                ComicsFilterType currentFiltering = requestValues.getCurrentFiltering();
                ComicsFilter taskFilter = mFilterFactory.create(currentFiltering);

                List<Comics> tasksFiltered = taskFilter.filter(comicsList, requestValues.getPrice());
                GetComicsList.ResponseValue responseValue = new GetComicsList.ResponseValue(tasksFiltered, taskFilter.getPageCount());
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final ComicsFilterType mCurrentFiltering;
        private final double mPrice;

        public RequestValues(@NonNull ComicsFilterType currentFiltering, double price) {
            mCurrentFiltering = checkNotNull(currentFiltering, "currentFiltering cannot be null!");
            mPrice = price;
        }

        public ComicsFilterType getCurrentFiltering() {
            return mCurrentFiltering;
        }

        public double getPrice() {
            return mPrice;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Comics> mComicsList;
        private final int mPageCount;

        public ResponseValue(@NonNull List<Comics> comicsList, int pageCount) {
            mComicsList = checkNotNull(comicsList, "Comics cannot be null!");
            mPageCount = pageCount;
        }

        public List<Comics> getComicsList() {
            return mComicsList;
        }

        public int getPageCount() {
            return mPageCount;
        }
    }
}
