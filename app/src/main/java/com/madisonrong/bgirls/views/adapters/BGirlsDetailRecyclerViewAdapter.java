package com.madisonrong.bgirls.views.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.models.Girl;
import com.squareup.picasso.Picasso;

import java.util.Collection;

import ch.halcyon.squareprogressbar.SquareProgressBar;

import static android.R.color.holo_blue_bright;
import static android.R.color.white;

/**
 * Created by MadisonRong on 15/7/31.
 */
public class BGirlsDetailRecyclerViewAdapter extends BaseRecyclerViewAdapter<Girl, BGirlsDetailRecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private ImageView imageView;
    private CardView mCardView;

    public BGirlsDetailRecyclerViewAdapter(Activity activity, ImageView imageView) {
        this.activity = activity;
        this.imageView = imageView;
    }

    public BGirlsDetailRecyclerViewAdapter(int cappacity, Activity activity) {
        super(cappacity);
        this.activity = activity;
    }

    public BGirlsDetailRecyclerViewAdapter(Collection<? extends Girl> collection, Activity activity) {
        super(collection);
        this.activity = activity;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.item_girl, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Girl girl = this.get(position);
//        holder.squareProgressBar.setAlwaysDrawnWithCacheEnabled(true);
//        holder.squareProgressBar.drawCenterline(false);
//        holder.squareProgressBar.setWidth(5);
//        ImageView spbImageView = holder.squareProgressBar.getImageView();
        ImageView holderImageView = holder.imageView;
        String imageUrl = this.get(position).getImgUrl().replace("thumbnail=1680x0&quality=96", "thumbnail=100x0&quality=100");
        holderImageView.setImageBitmap(null);
        Picasso picasso = Picasso.with(holderImageView.getContext());
//        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        picasso.load(imageUrl)
                .placeholder(R.drawable.drawer_loading)
                .error(R.drawable.drawer_error)
                .resize(100, 100)
                .centerCrop()
                .into(holderImageView);
        holder.itemView.setTag(girl);

        if (position == 0 && this.size() > 0) {
            mCardView = holder.container;
            Picasso.with(imageView.getContext())
                    .load(this.get(0).getImgUrl())
                    .placeholder(R.drawable.drawer_loading)
                    .error(R.drawable.drawer_error)
                    .into(imageView);
        }

        holderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCardView != null) {
//                    mCardView.setBackgroundColor(white);
                }
                mCardView = holder.container;
                holder.container.setBackgroundColor(holo_blue_bright);
                Picasso.with(imageView.getContext())
                        .load(BGirlsDetailRecyclerViewAdapter.this.get(position).getImgUrl())
                        .placeholder(R.drawable.drawer_loading)
                        .error(R.drawable.drawer_error)
                        .into(imageView);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView container;
        private ImageView imageView;
//        private SquareProgressBar squareProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            container = (CardView) itemView.findViewById(R.id.item_girl_container);
            imageView = (ImageView) itemView.findViewById(R.id.item_girl_image);
//            squareProgressBar = (SquareProgressBar) itemView.findViewById(R.id.item_girl_image);
        }
    }
}
