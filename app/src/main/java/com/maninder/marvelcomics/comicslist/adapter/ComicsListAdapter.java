package com.maninder.marvelcomics.comicslist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maninder.marvelcomics.R;
import com.maninder.marvelcomics.comicslist.ComicsListContract;
import com.maninder.marvelcomics.comicslist.listener.ComicsViewHolderListener;
import com.maninder.marvelcomics.data.remote.model.Comics;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Maninder on 14/11/16.
 */

public class ComicsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ComicsViewHolderListener {

    private List<Comics> mComicsList;
    private ComicsListContract.View mView;
    private Context mContext;

    public ComicsListAdapter(@NonNull ComicsListContract.View view, @NonNull Context context, List<Comics> comicsList) {
        mView = view;
        mContext = context;
        mComicsList = comicsList;

    }

    @Override
    public void onClickItem(int id) {
        mView.showCoimcs(id);
    }

    /**
     * Populate the Adapter with this new List
     *
     * @param comicsList new Comics list
     */
    public void replaceData(List<Comics> comicsList) {
        mComicsList = checkNotNull(comicsList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comics_list_item, parent, false);
        return new ComicsViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ComicsViewHolder) holder).setup(mComicsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mComicsList.size();
    }

    /**
     * view holder to hold the Comics information
     */
    public class ComicsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ComicsViewHolderListener mListener;

        @BindView(R.id.imageComics)
        ImageView imageSet;
        @BindView(R.id.titleComics)
        TextView titleSet;
        @BindView(R.id.priceComics)
        TextView priceComics;

        public ComicsViewHolder(View itemView, ComicsViewHolderListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClickItem(mComicsList.get(getAdapterPosition()).id);
        }

        public void setup(Comics comics) {

            checkNotNull(comics.title);
            titleSet.setText(comics.title);
            priceComics.setText(Double.toString(comics.prices.price));

            if (comics.thumbnail != null && !comics.thumbnail.path.isEmpty()) {
                String urlImage = comics.thumbnail.path + "." + comics.thumbnail.extension;
                Picasso.with(mContext).load(urlImage).fit().centerCrop().into(imageSet);
            }
        }
    }
}
