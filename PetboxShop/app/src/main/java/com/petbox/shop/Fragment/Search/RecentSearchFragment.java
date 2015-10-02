package com.petbox.shop.Fragment.Search;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.petbox.shop.Adapter.List.RecentSearchListAdapter;
import com.petbox.shop.DB.DBConnector;
import com.petbox.shop.Delegate.RecentSearchDelegate;
import com.petbox.shop.Item.PopularSearchInfo;
import com.petbox.shop.Item.RecentSearchInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RecentSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentSearchFragment extends Fragment implements RecentSearchDelegate {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PullToRefreshListView listView;
    RecentSearchListAdapter listAdapter;
    ArrayList<RecentSearchInfo> mItemList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecentSearchFragment newInstance(String param1, String param2) {
        RecentSearchFragment fragment = new RecentSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RecentSearchFragment() {
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
        View v = inflater.inflate(R.layout.fragment_recent_search, container, false);
        listView = (PullToRefreshListView) v.findViewById(R.id.list_recent_search);

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        refreshAdapater();
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

    @Override
    public void deleteNRefresh(int rowId) {
        //Log.i("RecentSearch FRAGMENT",  " : ++ deleteNRefresh ++");
        new DBConnector(getContext()).deleteRecentSearchInfo(rowId);
        refreshAdapater();
    }

    public void refreshAdapater(){
        mItemList = new ArrayList<RecentSearchInfo>();
        mItemList = new DBConnector(getContext()).returnFromRecentSearch();
        listAdapter = new RecentSearchListAdapter(getActivity().getApplicationContext(), mItemList, this);
        listView.setAdapter(listAdapter);
    }

}
