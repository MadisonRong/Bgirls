package com.madisonrong.bgirls.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.madisonrong.bgirls.R;
import com.madisonrong.bgirls.views.adapters.BGirlsPagerAdapter;
import com.madisonrong.bgirls.views.widgets.ZoomImageView;

/**
 * Created by MadisonRong on 15/8/11.
 */
public class DetailFragment extends Fragment {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private BGirlsPagerAdapter mBGirlsPagerAdapter;
    private FragmentManager fragmentManager;

    public static String POS = "position";

    public static DetailFragment newInstance(int position) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DetailFragment.POS, position);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public void setupAdapter(BGirlsPagerAdapter bGirlsPagerAdapter) {
        if (bGirlsPagerAdapter != null && bGirlsPagerAdapter.size() != 0) {
            mBGirlsPagerAdapter = new BGirlsPagerAdapter();
            for (View view : bGirlsPagerAdapter) {
                ImageView imageView = (ImageView) view;
                ZoomImageView zoomImageView = new ZoomImageView(imageView.getContext(), null);
                zoomImageView.setImageBitmap(imageView.getDrawingCache());
                mBGirlsPagerAdapter.add(zoomImageView);
            }
        }
    }

    public DetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.fragment_detail_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .remove(DetailFragment.this)
                        .commit();
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.fragment_detail_view_pager);
        viewPager.setAdapter(mBGirlsPagerAdapter);
        int position = getArguments().getInt(DetailFragment.POS);
        viewPager.setCurrentItem(position);

        return view;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

}
