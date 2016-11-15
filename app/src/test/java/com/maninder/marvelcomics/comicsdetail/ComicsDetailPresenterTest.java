package com.maninder.marvelcomics.comicsdetail;

import com.maninder.marvelcomics.TestUseCaseScheduler;
import com.maninder.marvelcomics.comicsdetail.usecase.GetComicsDetail;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Maninder on 15/11/16.
 */

public class ComicsDetailPresenterTest {
    private Comics comics;

    @Mock
    private ComicsRepository mComicsRepository;

    @Mock
    private ComicsDetailFragment mComicsDetailFragment;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<ComicsDataSource.LoadComicsCallback> mLoadComicsCallbackArgumentCaptor;

    private ComicsDetailPresenter mComicsDetailPresenter;

    private int comicsId;

    @Before
    public void setupTasksPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        mComicsDetailFragment.comicsID = 123;
        // Get a reference to the class under test
        mComicsDetailPresenter = givenComicsDetailPresenter();

        // The presenter won't update the view unless it's active.
        when(mComicsDetailFragment.isActive()).thenReturn(true);

        comicsId = 123;
        comics = new Comics();
        comics.id = 123;
        comics.title = "Superman vs Batman";
        comics.pageCount = 100;
    }

    private ComicsDetailPresenter givenComicsDetailPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetComicsDetail getComicsDetail = new GetComicsDetail(mComicsRepository);

        return new ComicsDetailPresenter(useCaseHandler, mComicsDetailFragment, getComicsDetail);
    }


    @Test
    public void loadComicsFromRepositoryAndLoadIntoView() {
        // When loading of episode is requested
        mComicsDetailPresenter.setComicsID(comicsId);
        mComicsDetailPresenter.loadComics();

        // Callback is captured and invoked with stubbed Comics
        verify(mComicsRepository).getComics(mLoadComicsCallbackArgumentCaptor.capture(), eq(comicsId));
        mLoadComicsCallbackArgumentCaptor.getValue().onComicsLoaded(comics);

        // Then progress indicator is hidden and the Comics information are shown in UI
        ArgumentCaptor<Comics> showEpisodeArgumentCaptor = ArgumentCaptor.forClass(Comics.class);
        verify(mComicsDetailFragment).showComics(showEpisodeArgumentCaptor.capture());
        assertTrue(showEpisodeArgumentCaptor.getValue().id == comicsId);
    }
}
