package com.maninder.marvelcomics.comicslist;

import com.google.common.collect.Lists;
import com.maninder.marvelcomics.TestUseCaseScheduler;
import com.maninder.marvelcomics.comicslist.filter.FilterFactory;
import com.maninder.marvelcomics.comicslist.usecase.GetComicsList;
import com.maninder.marvelcomics.data.ComicsDataSource;
import com.maninder.marvelcomics.data.ComicsRepository;
import com.maninder.marvelcomics.data.remote.model.Comics;
import com.maninder.marvelcomics.threading.UseCaseHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maninder on 15/11/16.
 */

public class ComicsListPresenterTest {

    private static List<Comics> COMICS;

    @Mock
    private ComicsRepository mComicsRepository;

    @Mock
    private ComicsListFragment mComicsListFragment;

    @Captor
    private ArgumentCaptor<ComicsDataSource.LoadComicsListCallback> mLoadComicsListCallbackArgumentCaptor;

    private ComicsListPresenter mComicsListPresenter;

    @Before
    public void setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mComicsListPresenter = givenComicsListPresenter();

        // The presenter won't update the view unless it's active.
        when(mComicsListFragment.isActive()).thenReturn(true);

        // Crate 3 new Comics Object
        COMICS = Lists.newArrayList(
                new Comics(),
                new Comics(),
                new Comics());
    }

    private ComicsListPresenter givenComicsListPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetComicsList getComicsList = new GetComicsList(mComicsRepository, new FilterFactory());
        return new ComicsListPresenter(mComicsListFragment, useCaseHandler, getComicsList);
    }


    @Test
    public void loadAllComicsFromRepositoryAndLoadIntoView() {
        // Loading Comics
        mComicsListPresenter.loadComicsContents();

        // Callback is captured and invoked with stubbed Comics
        verify(mComicsRepository).getComicsList(mLoadComicsListCallbackArgumentCaptor.capture());
        mLoadComicsListCallbackArgumentCaptor.getValue().onComicsListLoaded(COMICS);

        // Then progress indicator is hidden and all comics are shown in UI
        verify(mComicsListFragment).setLoadingIndicator(false);
        ArgumentCaptor<List> showTasksArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mComicsListFragment).showComicsList(showTasksArgumentCaptor.capture());
        assertTrue(showTasksArgumentCaptor.getValue().size() == 3);
    }
}
