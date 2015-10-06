package com.petbox.shop.Fragment.Home;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petbox.shop.Adapter.Pager.IntegrationPlanningPagerAdapter;
import com.petbox.shop.CustomView.NonSwipeableViewPager;
import com.petbox.shop.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntegrationPlanningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntegrationPlanningFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentManager fragmentManager;
    NonSwipeableViewPager mViewPager;
    private IntegrationPlanningPagerAdapter pagerAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntegrationPlanningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntegrationPlanningFragment newInstance(String param1, String param2) {
        IntegrationPlanningFragment fragment = new IntegrationPlanningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public IntegrationPlanningFragment() {
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
        View v = inflater.inflate(R.layout.fragment_integration_planning, container, false);

        fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        mViewPager = (NonSwipeableViewPager) v.findViewById(R.id.pager_integration_planning);
        pagerAdapter = new IntegrationPlanningPagerAdapter(fragmentManager);
        mViewPager.setSwipeEnabled(false);
        mViewPager.setAdapter(pagerAdapter);

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();

        mViewPager.setCurrentItem(0);
    }



}
