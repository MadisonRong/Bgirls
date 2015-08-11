package com.madisonrong.bgirls.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.managers.BGirlsNetWorkManager;
import com.madisonrong.bgirls.views.adapters.BGirlsRecyclerViewAdapter;

public class HomeFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnHomeFragmentItemClickListener mListener;

    private RecyclerView recyclerView;
    private BGirlsRecyclerViewAdapter bGirlsRecyclerViewAdapter;
    private int page = 1;
    private boolean canLoad = true;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

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

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_home_swipe_refresh_layout);

        bGirlsRecyclerViewAdapter = new BGirlsRecyclerViewAdapter(30, getActivity());
        final BGirlsNetWorkManager bGirlsNetWorkManager = new BGirlsNetWorkManager(getActivity(), bGirlsRecyclerViewAdapter);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_recyclerview);
        recyclerView.setAdapter(bGirlsRecyclerViewAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisibleItems = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                int lastItem = Math.max(lastVisibleItems[0], lastVisibleItems[1]);

                //先判断是否已经到底了
                if (dy > 0 && lastItem > bGirlsRecyclerViewAdapter.getItemCount() - 5) {
                    //然后再来判断是否正在加载
                    if (canLoad) {
                        canLoad = false;
                        page += 1;
                        bGirlsNetWorkManager.getList(page);
                    }
                } else {
                    //加载完成后，上面的条件会判断失败，此时就可以修改canLoad条件
                    canLoad = true;
                }

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        bGirlsNetWorkManager.getList(page);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHomeFragmentItemClickListener) activity;
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
//            mListener.onHomeFragmentItemClick(bGirlsRecyclerViewAdapter.get(position).getUrl());
        }
    }

    public interface OnHomeFragmentItemClickListener {
        public void onHomeFragmentItemClick(String url);
    }

}
