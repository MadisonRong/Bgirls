package com.madisonrong.bgirls.views.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.activities.DetailActivity;
import com.madisonrong.bgirls.models.Girl;

import java.util.Collection;

/**
 * Created by MadisonRong on 15/7/30.
 */
public class BGirlsRecyclerViewAdapter extends BaseRecyclerViewAdapter<Girl, BGirlsRecyclerViewAdapter.ViewHloder> {

    private Activity activity;
    private View.OnClickListener listener;

    public BGirlsRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
    }

    public BGirlsRecyclerViewAdapter(int cappacity, Activity activity) {
        super(cappacity);
        this.activity = activity;
    }

    public BGirlsRecyclerViewAdapter(Collection<? extends Girl> collection, Activity activity) {
        super(collection);
        this.activity = activity;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHloder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHloder(activity.getLayoutInflater().inflate(R.layout.item_girls, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHloder holder, final int position) {
        Bitmap bitmap = this.get(position).getPicture();
        //设置图片
        holder.imageView.setImageBitmap(bitmap);
        //设置监听器
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = BGirlsRecyclerViewAdapter.this.get(position).getUrl();
                DetailActivity.actionStart(v.getContext(), url);
            }
        });
    }

    public class ViewHloder extends RecyclerView.ViewHolder {
        private View container;
        private ImageView imageView;

        public ViewHloder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_girls_container);
            imageView = (ImageView) itemView.findViewById(R.id.item_girls_image);
        }
    }
}
