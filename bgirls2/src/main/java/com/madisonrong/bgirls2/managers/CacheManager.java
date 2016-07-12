package com.madisonrong.bgirls2.managers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.madisonrong.bgirls2.R;
import com.madisonrong.bgirls2.db.BgirlRealm;
import com.madisonrong.bgirls2.models.Girl;
import com.madisonrong.bgirls2.models.GirlImagesRealmObject;
import com.madisonrong.bgirls2.views.adapters.BGirlsPagerAdapter;
import com.madisonrong.bgirls2.views.adapters.BGirlsRecyclerViewAdapter;
import com.madisonrong.bgirls2.views.listeners.BGirlsDataListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.lang.ref.WeakReference;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by MadisonRong on 7/4/16.
 */
public class CacheManager {

    private static final String TAG = "CacheManager";

    private Activity activity;
    private WeakReference<Activity> activityWeakReference;
    private BGirlsRecyclerViewAdapter adapter;
    private BGirlsPagerAdapter pagerAdapter;
    private double pageSize = 10.0;
    private BGirlsDataListener dataListener;

    public CacheManager(Activity activity, BGirlsRecyclerViewAdapter adapter) {
        activityWeakReference = new WeakReference<>(activity);
        this.adapter = adapter;
    }

    public CacheManager(Activity activity, BGirlsPagerAdapter pagerAdapter) {
        activityWeakReference = new WeakReference<>(activity);
        this.pagerAdapter = pagerAdapter;
    }

    public void setDataListener(BGirlsDataListener dataListener) {
        this.dataListener = dataListener;
    }

    /**
     * get total page
     *
     * @return total page of list
     */
    public long getTotalPage() {
        return (long) Math.ceil(BgirlRealm.getInstance().where(Girl.class).count() / pageSize);
    }

    /**
     * get list from cache for displaying data
     *
     * @param page page number
     */
    public void getCacheList(int page) {
        Realm realm = BgirlRealm.getInstance();
        long count = realm.where(Girl.class).count();
        int totalPage = (int) Math.ceil(count / pageSize);
        Log.i(TAG, "execute: count: " + count);
        Log.i(TAG, "execute: ceil total page: " + Math.ceil(count / pageSize));
        Log.i(TAG, "execute: current page: " + page);
        Log.i(TAG, "execute: total cached page: " + totalPage);
        long from = (long) (count - pageSize * page + 1);
        long to = (long) (count - pageSize * (page - 1));
        Log.i(TAG, String.format("execute: from: %d, to: %d", from, to));
        RealmResults<Girl> girls;
        girls = realm.where(Girl.class)
                .between("id", from, to)
                .findAllSorted("id", Sort.DESCENDING);
        for (Girl girl : girls) {
            Log.i(TAG, "execute: girl in Realm: " + girl.toString());
        }
        adapter.addAll(girls);
        realm.close();
        if (dataListener != null) {
            dataListener.onCompleted(page);
        }
    }

    /**
     * check detail of specific girl is cached or not
     *
     * @param url detail url of specific girl
     * @return return true if it's cached, other wise return false
     */
    public boolean hasDetailCached(final String url) {
        Realm realm = BgirlRealm.getInstance();
        Girl girl = realm.where(Girl.class)
                .equalTo("url", url)
                .findFirst();
        boolean result = girl.getGirlImages() != null && girl.getGirlImages().size() > 0;
        realm.close();
        return result;
    }

    /**
     * get detail of specific data from list
     *
     * @param url detail url of  specific data
     */
    public void getCacheDetail(String url) {
        Realm realm = BgirlRealm.getInstance();

        Girl girl = realm.where(Girl.class)
                .equalTo("url", url)
                .findFirst();
        Observable.from(girl.getGirlImages())
                .map(new Func1<GirlImagesRealmObject, String>() {
                    @Override
                    public String call(GirlImagesRealmObject girlImages) {
                        return girlImages.getImage_url();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
                        if (dataListener != null) {
                            dataListener.onCompleted(0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        activity = activityWeakReference.get();
                        if (activity != null) {
                            Toast.makeText(activity, "load failed. try it again later.", Toast.LENGTH_SHORT).show();
                        }
                        if (dataListener != null) {
                            dataListener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(final String imageUrl) {
                        Log.i(TAG, "call: image url: " + imageUrl);
                        // pic
                        activity = activityWeakReference.get();
                        if (activity != null) {
                            PhotoView photoView = new PhotoView(activity);
                            photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                            photoView.enable();
                            photoView.setMaxScale(2f);
                            photoView.setScaleType(ImageView.ScaleType.CENTER);
                            Transformation transformation = new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {

                                    DisplayMetrics displayMetrics = new DisplayMetrics();
                                    activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                                    int targetWidth = displayMetrics.widthPixels;
                                    Log.i(TAG, "source.getHeight()=" + source.getHeight() + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);

                                    if (source.getWidth() == 0) {
                                        Log.i(TAG, "transform: width of source is 0");
                                        return source;
                                    }

                                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                                    int targetHeight = (int) (targetWidth * aspectRatio);
                                    if (targetHeight != 0 && targetWidth != 0) {
                                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);
                                        if (result != source) {
                                            // Same bitmap is returned if sizes are the same
                                            source.recycle();
                                        }
                                        return result;
                                    } else {
                                        return source;
                                    }
                                }

                                @Override
                                public String key() {
                                    return imageUrl;
                                }
                            };
                            Picasso.with(activity)
                                    .load(imageUrl)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                    .placeholder(R.drawable.drawer_loading)
                                    .error(R.drawable.drawer_error)
                                    .config(Bitmap.Config.RGB_565)
                                    .transform(transformation)
                                    .into(photoView);
                            pagerAdapter.add(photoView);
                        }
                    }
                });
        realm.close();
    }

}
