package com.madisonrong.bgirls.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.madisonrong.bgirls.models.Girl;
import com.madisonrong.bgirls.network.retrofit.BGirlsClient;
import com.madisonrong.bgirls.network.retrofit.RetrofitGenerator;
import com.madisonrong.bgirls.network.volley.BGirlsHttpRequest;
import com.madisonrong.bgirls.views.adapters.BGirlsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
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

        textView = (TextView) view.findViewById(android.R.id.empty);
        bGirlsRecyclerViewAdapter = new BGirlsRecyclerViewAdapter(30, getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_home_recyclerview);
        recyclerView.setAdapter(bGirlsRecyclerViewAdapter);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        BGirlsClient client = RetrofitGenerator.getService(BGirlsClient.class, BGirls.HOME_BASE_URL);
        client.getPage("1", new Callback<String>() {
            @Override
            public void success(String string, Response response) {
                Pattern pattern = Pattern.compile("<a class=\"img\" href=\"(.*?)\">[\\s|\\S]*?<img src=\"(.*?)\" />");
                Matcher matcher = pattern.matcher(string);
                Pattern pattern1 = Pattern.compile("<div class=\"text\">[\\s|\\S]*?<p>(.*?)<br />");
                Matcher matcher1 = pattern1.matcher(string);
                while (matcher.find() && matcher1.find()) {
                    final String url = matcher.group(1);
                    final String description = matcher1.group(1);
                    ImageRequest imageRequest = new ImageRequest(matcher.group(2),
                    new com.android.volley.Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            Girl girl = new Girl(description, response, url);
                            bGirlsRecyclerViewAdapter.add(0, girl);
                        }
                    }, 500, 720, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("bgirls.network", error.getMessage());
                            Toast.makeText(getActivity(), R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                        }
                    });
                    BGirlsHttpRequest.getInstance(getActivity().getApplicationContext()).addToRequestsQueue(imageRequest);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

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
