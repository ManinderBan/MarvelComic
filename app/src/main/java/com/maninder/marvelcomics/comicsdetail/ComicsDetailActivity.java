package com.maninder.marvelcomics.comicsdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.maninder.marvelcomics.MarvelComicsApplication;
import com.maninder.marvelcomics.R;
import com.maninder.marvelcomics.comicsdetail.injection.ComicsDetailModule;
import com.maninder.marvelcomics.comicsdetail.injection.DaggerComicsDetailComponent;

import javax.inject.Inject;

/**
 * Created by Maninder on 14/11/16.
 */

public class ComicsDetailActivity extends AppCompatActivity {

    public final static String COMICS_ID = "comics_id";
    private int comicsID;

    @Inject
    ComicsDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comics_detail_activity);
        if (getIntent().hasExtra(COMICS_ID)) {
            comicsID = getIntent().getIntExtra(COMICS_ID, 0);
        } else {
            comicsID = 0;
        }

        ComicsDetailFragment comicsDetailFragment = (ComicsDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (comicsDetailFragment == null) {
            comicsDetailFragment = ComicsDetailFragment.getINSTANCE(comicsID);
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, comicsDetailFragment).commit();
        }

        DaggerComicsDetailComponent.builder()
                .comicsDetailModule(new ComicsDetailModule(comicsDetailFragment,
                        ((MarvelComicsApplication) getApplication()).getComicsRepositoryComponent().getComicsRepository()))
                .build()
                .inject(this);

    }
}
