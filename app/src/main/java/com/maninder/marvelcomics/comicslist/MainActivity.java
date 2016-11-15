package com.maninder.marvelcomics.comicslist;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.maninder.marvelcomics.MarvelComicsApplication;
import com.maninder.marvelcomics.R;
import com.maninder.marvelcomics.comicslist.injection.ComicsListModule;
import com.maninder.marvelcomics.comicslist.injection.DaggerComicsListComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * For this this application I used the MVP (Model View Presenter) + Clean Architecture, this allow to write a
 * code that is maintainable and testable.
 * <p>
 * This type of approch is used in different type of application. This Application is really a simple example of how we can use use this
 * type of architecture.
 * </p>
 *
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Inject
    ComicsListPresenter mComicsListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        //Create fragment
        ComicsListFragment comicsListFragment = (ComicsListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (comicsListFragment == null) {
            comicsListFragment = ComicsListFragment.getINSTANCE();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, comicsListFragment).commit();
        }

        //Create the Presenter
        DaggerComicsListComponent.builder()
                .comicsListModule(new ComicsListModule(comicsListFragment,
                        ((MarvelComicsApplication) getApplication()).getComicsRepositoryComponent().getComicsRepository()))
                .build()
                .inject(this);
    }


}
