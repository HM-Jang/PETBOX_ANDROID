package com.petbox.shop.Fragment.Search;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.petbox.shop.Adapter.List.PopularSearchListAdapter;
import com.petbox.shop.Item.PlanningItemInfo;
import com.petbox.shop.Item.PopularSearchInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PopularSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularSearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PullToRefreshListView listView;
    PopularSearchListAdapter listAdapter;
    ArrayList<PopularSearchInfo> mItemList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularSearchFragment newInstance(String param1, String param2) {
        PopularSearchFragment fragment = new PopularSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PopularSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_popular_search, container, false);

        mItemList = new ArrayList<PopularSearchInfo>();

        PopularSearchInfo info[] = new PopularSearchInfo[20];

        for(int i=0; i<20; i++){
            info[i] = new PopularSearchInfo();

            info[i].ranking = i+1;
            info[i].title = "검색어"+i;

            mItemList.add(info[i]);
        }

        listView = (PullToRefreshListView) v.findViewById(R.id.list_popular_search);
        listAdapter = new PopularSearchListAdapter(getActivity().getApplicationContext(), mItemList);
        listView.setAdapter(listAdapter);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
