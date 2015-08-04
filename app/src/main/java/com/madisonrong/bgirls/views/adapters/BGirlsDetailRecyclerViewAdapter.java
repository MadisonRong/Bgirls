package com.madisonrong.bgirls.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.models.Girl;
import com.squareup.picasso.Picasso;

import java.util.Collection;

/**
 * Created by MadisonRong on 15/7/31.
 */
public class BGirlsDetailRecyclerViewAdapter extends BaseRecyclerViewAdapter<Girl, BGirlsDetailRecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private ImageView imageView;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Girl girl = this.get(position);
        holder.imageView.setImageBitmap(null);
        Picasso picasso = Picasso.with(holder.imageView.getContext());
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        picasso.load(this.get(position).getImgUrl())
                .placeholder(R.drawable.drawer_loading)
                .error(R.drawable.drawer_shadow)
                .resize(100, 100)
                .centerCrop()
                .into(holder.imageView);
        holder.itemView.setTag(girl);

        if (position == 0 && this.size() > 0) {
            Picasso.with(imageView.getContext())
                    .load(this.get(0).getImgUrl())
                    .placeholder(R.drawable.drawer_loading)
                    .error(R.drawable.drawer_error)
                    .into(imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(imageView.getContext())
                        .load(BGirlsDetailRecyclerViewAdapter.this.get(position).getImgUrl())
                        .placeholder(R.drawable.drawer_loading)
                        .error(R.drawable.drawer_error)
                        .into(imageView);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View container;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_girl_container);
            imageView = (ImageView) itemView.findViewById(R.id.item_girl_image);
        }
    }
}
