package com.madisonrong.bgirls2.views.adapters;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator;
import com.madisonrong.bgirls2.R;
import com.madisonrong.bgirls2.models.Girl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by MadisonRong on 15/7/30.
 */
public class BGirlsRecyclerViewAdapter extends BaseRecyclerViewAdapter<Girl, BGirlsRecyclerViewAdapter.ViewHolder>
implements GreedoLayoutSizeCalculator.SizeCalculatorDelegate{

    private Activity activity;
    private List<Double> mImageAspectRatios = new ArrayList<>();

    public BGirlsRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
    }

    public BGirlsRecyclerViewAdapter(int capacity, Activity activity) {
        super(capacity);
        this.activity = activity;
    }

    public BGirlsRecyclerViewAdapter(Collection<? extends Girl> collection, Activity activity) {
        super(collection);
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.item_girls, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //设置图片
        holder.imageView.setImageBitmap(this.get(position).getPicture());

        Girl girl = this.get(position);
        holder.imageView.setImageBitmap(null);
        String imageUrl = this.get(position).getImgUrl().replace("thumbnail=500x0&quality=96", "thumbnail=550x0&quality=40");
//        Picasso.with(holder.imageView.getContext()).cancelRequest(holder.imageView);
        Picasso picasso = Picasso.with(holder.imageView.getContext());
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        picasso.load(imageUrl)
                .placeholder(R.drawable.drawer_loading)
                .error(R.drawable.drawer_error)
                .into(holder.imageView);
        holder.itemView.setTag(girl);
        calculateImageAspectRatios(position);

        final String url = this.get(position).getUrl();
        final String description = this.get(position).getDescription().replaceAll("&nbsp;", " ");
        //设置监听器
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DetailActivity.actionStart(v.getContext(), url, description);
            }
        });
    }

    @Override
    public double aspectRatioForIndex(int index) {
        if (index >= getItemCount()) return 1.0;
        return mImageAspectRatios.get(index);
    }

    private void calculateImageAspectRatios(int position) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(activity.getResources(), )

//        for (int i = 0; i < mImageResIds.length; i++) {
//            BitmapFactory.decodeResource(mContext.getResources(), mImageResIds[i], options);
//
//        }
        mImageAspectRatios.add(options.outWidth / (double) options.outHeight);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_girls_image)
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
