package com.maninder.marvelcomics.comicslist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.maninder.marvelcomics.R;
import com.maninder.marvelcomics.comicsdetail.ComicsDetailActivity;
import com.maninder.marvelcomics.comicslist.adapter.ComicsListAdapter;
import com.maninder.marvelcomics.data.remote.model.Comics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * Show the list of comics
 */
public class ComicsListFragment extends Fragment implements ComicsListContract.View {


    private static ComicsListFragment INSTANCE = null;
    /**
     * Presenter: Handle events from the UI
     */
    private ComicsListContract.Presenter mPresenter;
    private ComicsListAdapter mComicsListAdapter;


    @BindView(R.id.comicsListRecyclerView)
    RecyclerView mSetContentsRecyclerView;

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    @BindView(R.id.errorText)
    TextView errorText;

    @BindView(R.id.swipeRefreshContent)
    SwipeRefreshLayout mSwipeRefreshContent;

    public static ComicsListFragment getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ComicsListFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comics_list_fragment, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        mSwipeRefreshContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadComicsContents();
            }
        });

        mComicsListAdapter = new ComicsListAdapter(this, getContext(), new ArrayList<Comics>(0));
        mSetContentsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mSetContentsRecyclerView.setAdapter(mComicsListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Request the comics list to Presenter
        mPresenter.loadComicsContents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filtering:
                showDialogPrice();
                break;
        }
        return true;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void setPresenter(ComicsListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * This method is called when we get the List of comics from Remote, or from Local when the device is offline
     *
     * @param comicsList
     */
    @Override
    public void showComicsList(@NonNull List<Comics> comicsList) {
        mComicsListAdapter.replaceData(comicsList);
    }

    @Override
    public void showCoimcs(int id) {
        Intent intent = new Intent(getActivity(), ComicsDetailActivity.class);
        intent.putExtra(ComicsDetailActivity.COMICS_ID, id);
        getActivity().startActivity(intent);
    }


    /**
     * Show the number of Pages that the user can read with the specific budget
     *
     * @param numberPage Max number of pages that the user can read
     */
    @Override
    public void showTotalPage(int numberPage) {
        if (getView() != null) {
            String stringPage = "Tot number of pages: " + Integer.toString(numberPage);
            Snackbar snackbar = Snackbar
                    .make(getView(), stringPage, Snackbar.LENGTH_INDEFINITE)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            snackbar.show();
        }
    }

    @Override
    public void stopRefresh() {
        mSwipeRefreshContent.setRefreshing(false);
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


    /**
     * This Dialog allow user to set a Budget.
     * <p>
     * SeeKBar: from 0 to 10000
     * </p>
     */
    public void showDialogPrice() {
        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_price);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView titlePrice = (TextView) dialog.findViewById(R.id.titlePrice);
        final TextView priceSet = (TextView) dialog.findViewById(R.id.priceSet);
        final EditText insertPrice = (EditText) dialog.findViewById(R.id.insertPrice);
        TextView okButton = (TextView) dialog.findViewById(R.id.okButton);
        TextView noPriceButton = (TextView) dialog.findViewById(R.id.noPriceButton);
        final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekBar);
        String price = Double.toString(mPresenter.getPrice());
        insertPrice.setText(price);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setFiltering(ComicsFilterType.PRICE_COMICS);
                double parse = Double.parseDouble(insertPrice.getText().toString());
                mPresenter.setPrice(parse);
                mPresenter.loadComicsContents();
                dialog.dismiss();
            }
        });
        noPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setFiltering(ComicsFilterType.ALL_COMICS);
                mPresenter.setPrice(0.0);
                mPresenter.loadComicsContents();
                dialog.dismiss();

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String newPrice = Integer.toString(i);
                insertPrice.setText(newPrice);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dialog.show();
    }

}

