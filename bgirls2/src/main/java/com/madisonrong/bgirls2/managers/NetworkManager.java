package com.madisonrong.bgirls2.managers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.google.gson.Gson;
import com.madisonrong.bgirls2.R;
import com.madisonrong.bgirls2.db.BgirlRealm;
import com.madisonrong.bgirls2.models.Girl;
import com.madisonrong.bgirls2.models.GirlImages;
import com.madisonrong.bgirls2.models.GirlImagesRealmObject;
import com.madisonrong.bgirls2.models.GirlInfo;
import com.madisonrong.bgirls2.network.BGirlsClient;
import com.madisonrong.bgirls2.network.RetrofitGenerator;
import com.madisonrong.bgirls2.views.adapters.BGirlsPagerAdapter;
import com.madisonrong.bgirls2.views.adapters.BGirlsRecyclerViewAdapter;
import com.madisonrong.bgirls2.views.listeners.BGirlsDataListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MadisonRong on 5/5/16.
 */
public class NetworkManager {

    private static final String TAG = "NetworkManager";

    private Activity activity;
    private WeakReference<Activity> activityWeakReference;
    private BGirlsRecyclerViewAdapter adapter;
    private BGirlsPagerAdapter pagerAdapter;
    private BGirlsDataListener dataListener;

    public NetworkManager(Activity activity, BGirlsRecyclerViewAdapter adapter) {
        this.activityWeakReference = new WeakReference<>(activity);
        this.adapter = adapter;
    }

    public NetworkManager(Activity activity, BGirlsPagerAdapter pagerAdapter) {
        this.activityWeakReference = new WeakReference<>(activity);
        this.pagerAdapter = pagerAdapter;
    }

    public void setDataListener(BGirlsDataListener dataListener) {
        this.dataListener = dataListener;
    }

    /**
     * get total page online
     */
    public void getTotalPage() {
        // regular expression for getting total page
        // (\d*?)</a></li> <li class="next">
    }

    /**
     * get list data online for displaying
     * @param page page number
     */
    public void getGirlList(final int page) {
        BGirlsClient client = RetrofitGenerator.getService(BGirlsClient.class);
        String myPage = page == 1 ? "index" : Integer.toString(page);
        client.getPage(myPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted() called with: " + "");
                        activity = activityWeakReference.get();
                        if (activity != null) {
                            Toast.makeText(activity, String.format("page %s loaded.", page), Toast.LENGTH_LONG).show();
                        }
                        if (dataListener != null) {
                            dataListener.onCompleted(page);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (dataListener != null) {
                            dataListener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String responseText = responseBody.string();
                            Pattern pattern = Pattern.compile("<a target=\"_blank\" href=\"(.*?)\" title=\"(.*?)\"");
                            Matcher matcher = pattern.matcher(responseText);
                            Pattern pattern1 = Pattern.compile("<img src=\"(.*?)\" data-original=\"(.*?)\"");
                            Matcher matcher1 = pattern1.matcher(responseText);
                            while (matcher.find() && matcher1.find()) {
                                String url = matcher.group(1);
                                String description = matcher.group(2);
                                String img = matcher1.group(2);

                                Girl girl = new Girl();
                                girl.setUrl(url);
                                girl.setDescription(description);
                                girl.setImgUrl(img);

                                Realm realm = BgirlRealm.getInstance();
                                realm.beginTransaction();
                                Girl testGirl = realm.where(Girl.class).equalTo("url", girl.getUrl()).findFirst();
                                if (testGirl == null) {
                                    // if not exist in Realm, then insert it
                                    girl.setId((int) (realm.where(Girl.class).count() + 1));
                                    realm.insert(girl);
                                } else {
                                    // other wise, update it
                                    Log.i(TAG, "execute: duplicate, skip it");
                                }
                                realm.commitTransaction();
                                realm.close();

                                adapter.add(0, girl);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * get specific data online from list
     * @param url detail url of specific data
     */
    public void getDetail(final String url) {
        int position = url.lastIndexOf("/");
        String id = url.substring(position + 1).split(".html")[0];
        Log.e("bgils.id", id);
        final BGirlsClient client = RetrofitGenerator.getService(BGirlsClient.class);
        client.getGirl(id)
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody responseBody) {
                        Log.d(TAG, "onNext() called with: " + "responseBody = [" + responseBody + "]");
                        String id = null;
                        try {
                            String responseString = responseBody.string();
                            Pattern pattern = Pattern.compile("var id=(\\d*);");
                            Matcher matcher = pattern.matcher(responseString);
                            while (matcher.find()) {
                                id = matcher.group(1);
                                Log.i(TAG, "girl id: " + id);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(id);
                    }
                })
                .flatMap(new Func1<String, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(String id) {
                        // the fucking pic api
                        // http://www.fydzv.com/api.php?op=get_imgs&id=
                        return client.getGirlPic("get_imgs", id);
                    }
                })
                .flatMap(new Func1<ResponseBody, Observable<GirlImages>>() {
                    @Override
                    public Observable<GirlImages> call(ResponseBody responseBody) {
                        try {
                            String responseJsonString = responseBody.string().split("=")[1].trim();
                            Gson gson = new Gson();
                            GirlInfo girlInfo = gson.fromJson(responseJsonString, GirlInfo.class);
                            return Observable.from(girlInfo.getImages());
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .filter(new Func1<GirlImages, Boolean>() {
                    @Override
                    public Boolean call(final GirlImages girlImages) {
                        Realm realm = BgirlRealm.getInstance();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                GirlImagesRealmObject images = realm.where(GirlImagesRealmObject.class)
                                        .equalTo("image_url", girlImages.getImage_url())
                                        .findFirst();
                                if (images == null) {
                                    // data is not exist in Realm, insert it
                                    Girl girl = realm.where(Girl.class)
                                            .equalTo("url", url)
                                            .findFirst();
                                    GirlImagesRealmObject imagesRealmObject = new GirlImagesRealmObject(girlImages);
                                    girl.getGirlImages().add(imagesRealmObject);
                                } else {
                                    Log.i(TAG, String.format("get detail online, %s has cached, skip...", girlImages.getImage_url()));
                                }
                            }
                        });
                        realm.close();
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GirlImages>() {
                    @Override
                    public void call(final GirlImages girlImages) {
                        Log.i(TAG, "call: image url: " + girlImages.getImage_url());
                        activity = activityWeakReference.get();
                        if (activity != null) {
                            // pic
                            PhotoView photoView = new PhotoView(activity);
                            photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                            photoView.enable();
                            photoView.setMaxScale(3f);
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
                                    return girlImages.getImage_url();
                                }
                            };
                            Picasso.with(activity)
                                    .load(girlImages.getImage_url())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                    .placeholder(R.drawable.drawer_loading)
                                    .error(R.drawable.drawer_error)
                                    .config(Bitmap.Config.RGB_565)
                                    .transform(transformation)
                                    .into(photoView);
                            pagerAdapter.add(photoView);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (dataListener != null) {
                            dataListener.onError(throwable);
                        }
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        // callback for completed
                        if (dataListener != null) {
                            dataListener.onCompleted(0);
                        }
                    }
                });
    }

//    private OnFinishedListener onFinishedListener;
//
//    public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
//        this.onFinishedListener = onFinishedListener;
//    }
//
//    public interface OnFinishedListener {
//        void onFinished(int page);
//    }
}
