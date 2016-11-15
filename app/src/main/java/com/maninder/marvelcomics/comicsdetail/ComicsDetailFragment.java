package com.maninder.marvelcomics.comicsdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maninder.marvelcomics.R;
import com.maninder.marvelcomics.data.remote.model.Comics;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 15/11/16.
 */

/**
 * This allow to show the Comics detail to the user.
 */
public class ComicsDetailFragment extends Fragment implements ComicsDetailContract.View {

    private static final String COMICSID = "comicsID";
    @BindView(R.id.toolbarComicsDetail)
    Toolbar toolbarComicsDetail;

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    @BindView(R.id.errorText)
    TextView errorText;

    @BindView(R.id.comicsImage)
    ImageView comicsImage;

    @BindView(R.id.titleComics)
    TextView titleComics;

    @BindView(R.id.authorComics)
    TextView authorComics;

    @BindView(R.id.pageCount)
    TextView pageCount;

    @BindView(R.id.priceComics)
    TextView priceComics;

    @BindView(R.id.descriptionComics)
    TextView descriptionComics;

    private static ComicsDetailFragment INSTANCE = null;

    /**
     * @param comicsID is the reference of the Comics that we need to get from {@link com.maninder.marvelcomics.data.ComicsRepository}
     * @return Instance of the {@link ComicsDetailFragment} fragment
     */
    public static ComicsDetailFragment getINSTANCE(int comicsID) {
        if (INSTANCE == null) {
            INSTANCE = new ComicsDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(COMICSID, comicsID);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    private ComicsDetailContract.Presenter mPresenter;

    public int comicsID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comics_detail_fragment, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            comicsID = getArguments().getInt(COMICSID);
        } else {
            comicsID = 0;
        }
        ((ComicsDetailActivity) getActivity()).setSupportActionBar(toolbarComicsDetail);
        toolbarComicsDetail.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbarComicsDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setComicsID(comicsID);
        mPresenter.loadComics();
    }

    @Override
    public void setPresenter(ComicsDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showComics(@NonNull Comics comics) {
        populateView(comics);
    }

    /**
     * This Method populate the View to show the information about the Comics
     *
     * @param comics the comics requested
     */
    public void populateView(Comics comics) {
        if (comics.title != null && !comics.title.isEmpty()) {
            titleComics.setText(comics.title);
        }
        if (comics.author != null && !comics.author.isEmpty()) {
            authorComics.setText(comics.author);
        }
        String price = getResources().getString(R.string.price) + ": " + Double.toString(comics.prices.price);
        priceComics.setText(price);

        String page = getResources().getString(R.string.pageCount) + ": " + Integer.toString(comics.pageCount);
        pageCount.setText(page);

        if (comics.description != null && !comics.description.isEmpty()) {
            descriptionComics.setText(comics.description);
        }

        if (comics.images.path != null && !comics.images.path.isEmpty()) {
            loadImage(comics.images.path, comics.images.extension);
        }
    }

    /**
     * Load the Image
     *
     * @param path
     * @param extension
     */
    public void loadImage(String path, String extension) {
        checkNotNull(path);
        String urlImage = path + "." + extension;
        Picasso.with(getContext()).load(urlImage).fit().centerCrop().into(comicsImage);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setLoadingError(boolean active) {
        if (getView() == null) {
            return;
        }

        if (active) {
            errorText.setVisibility(View.VISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
