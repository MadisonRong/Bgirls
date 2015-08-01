package com.madisonrong.bgirls.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.models.Girl;

import java.util.Collection;

/**
 * Created by MadisonRong on 15/7/31.
 */
public class BGirlsDetailRecyclerViewAdapter extends BaseRecyclerViewAdapter<Girl, BGirlsDetailRecyclerViewAdapter.ViewHolder> {

    private Activity activity;

    public BGirlsDetailRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
    }

    public BGirlsDetailRecyclerViewAdapter(int cappacity, Activity activity) {
        super(cappacity);
        this.activity = activity;
    }

    public BGirlsDetailRecyclerViewAdapter(Collection<? extends Girl> collection, Activity activity) {
        super(collection);
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.item_girl, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(this.get(position).getPicture());
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
