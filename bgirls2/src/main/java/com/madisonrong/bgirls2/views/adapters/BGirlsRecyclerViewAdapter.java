package com.madisonrong.bgirls2.views.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madisonrong.bgirls2.R;
import com.madisonrong.bgirls2.models.Girl;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by MadisonRong on 15/7/30.
 */
public class BGirlsRecyclerViewAdapter extends BaseRecyclerViewAdapter<Girl, BGirlsRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "BGirlsAdapter";

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

        // regular expression for detail page
        // <img src="(.*?)" alt="(.*?)" oncontextmenu="(.*?)"

        Girl girl = this.get(position);
        holder.imageView.setImageBitmap(null);
        final String imageUrl = this.get(position).getImgUrl();

        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {

                DisplayMetrics displayMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int targetWidth = displayMetrics.widthPixels / 2;
                Log.i(TAG, "source.getHeight()=" + source.getHeight() + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);

                if (source.getWidth() == 0) {
                    return source;
                }

                //如果图片小于设置的宽度，则返回原图
                if (source.getWidth() < targetWidth) {
                    return source;
                } else {
                    //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    if (targetHeight != 0 && targetWidth != 0) {
                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    } else {
                        return source;
                    }
                }

            }

            @Override
            public String key() {
                return imageUrl;
            }
        };

        Picasso.with(holder.imageView.getContext())
                .load(imageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .placeholder(R.drawable.drawer_loading)
                .error(R.drawable.drawer_error)
                .config(Bitmap.Config.RGB_565)
                .transform(transformation)
                .into(holder.imageView);
        holder.itemView.setTag(girl);
        Log.i(TAG, "onBindViewHolder: width of image view: " + holder.imageView.getWidth() +
                ", height of image view: " + holder.imageView.getHeight());

        final String url = this.get(position).getUrl();
        Log.i(TAG, "onBindViewHolder: url: " + url);
        // set listener
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_girls_image)
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
