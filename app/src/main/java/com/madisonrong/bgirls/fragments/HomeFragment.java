package com.madisonrong.bgirls.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.madisonrong.bgirls.R;

import com.madisonrong.bgirls.activities.MainActivity;
import com.madisonrong.bgirls.constant.BGirls;
import com.madisonrong.bgirls.fragments.dummy.DummyContent;
import com.madisonrong.bgirls.managers.BGirlsListManager;
import com.madisonrong.bgirls.models.Girl;
import com.madisonrong.bgirls.network.retrofit.BGirlsClient;
import com.madisonrong.bgirls.network.retrofit.RetrofitGenerator;
import com.madisonrong.bgirls.network.volley.BGirlsHttpRequest;
import com.madisonrong.bgirls.views.adapters.BGirlsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;

    private TextView textView;
    private RecyclerView recyclerView;
    private BGirlsRecyclerViewAdapter bGirlsRecyclerViewAdapter;
    private int page = 1;
    private ReentrantLock lock = new ReentrantLock();
    private boolean canLoad = true;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.e("bgirls.fragment", "home fragment create view");

        textView = (TextView) view.findViewById(android.R.id.empty);
        bGirlsRecyclerViewAdapter = new BGirlsRecyclerViewAdapter(30, getActivity());
        final BGirlsListManager bGirlsListManager = new BGirlsListManager(getActivity(), bGirlsRecyclerViewAdapter);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_recyclerview);
        recyclerView.setAdapter(bGirlsRecyclerViewAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
//                if (gridLayoutManager.findLastVisibleItemPosition() == bGirlsRecyclerViewAdapter.size() - 1 ) {
//                    Log.e("bgirls.test", "last one has been showed.");
//                    page += 1;
//                    bGirlsListManager.getList(page);
//                }

                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisibleItems = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                int[] lastCompletelyVisibleItems = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
                Log.e("bgirls.test", "last visible items is : " + Arrays.toString(lastVisibleItems));
                Log.e("bgirls.test", "last completely visible items is : " + Arrays.toString(lastCompletelyVisibleItems));
                Log.e("bgirls.test", "adapter count is : " + bGirlsRecyclerViewAdapter.getItemCount());
                int lastItem = Math.max(lastVisibleItems[0], lastVisibleItems[1]);

                //先判断是否已经到底了
                if (dy > 0 && lastItem > bGirlsRecyclerViewAdapter.getItemCount() - 5) {
                    //然后再来判断是否正在加载
                    if (canLoad) {
                        canLoad = false;
                        page += 1;
                        Log.e("bgirls.test", "page is : " + page);
                        bGirlsListManager.getList(page);
                    }
                } else {
                    //加载完成后，上面的条件会判断失败，此时就可以修改canLoad条件
                    canLoad = true;
                }

                ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
                if (dy > 10) {
                    // 上拉收起actionBar
                    actionBarActivity.getSupportActionBar().hide();
                } else if (dy < -10) {
                    // 下拉显示actionBar
                    actionBarActivity.getSupportActionBar().show();
                }
            }
        });

        bGirlsListManager.getList(page);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("bgirls.fragment", "home fragment destroy view");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
