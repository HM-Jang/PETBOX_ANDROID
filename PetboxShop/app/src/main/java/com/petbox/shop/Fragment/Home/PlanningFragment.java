package com.petbox.shop.Fragment.Home;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.petbox.shop.Adapter.List.PlanningListAdapter;
import com.petbox.shop.Item.PlanningItemInfo;
import com.petbox.shop.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlanningFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlanningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanningFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int SELECT_DOG = 0;
    private static final int SELECT_CAT = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button btn_dog, btn_cat;
    Spinner spin_category;
    PullToRefreshListView listView;
    PlanningListAdapter listAdapter_dog;
    PlanningListAdapter listAdapter_cat;

    ArrayList<PlanningItemInfo> mItemList_dog;
    ArrayList<PlanningItemInfo> mItemList_cat;

    int selected = SELECT_DOG;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanningFragment newInstance(String param1, String param2) {
        PlanningFragment fragment = new PlanningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlanningFragment() {
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
        View v = inflater.inflate(R.layout.fragment_planning, container, false);

        mItemList_dog = new ArrayList<PlanningItemInfo>();

        PlanningItemInfo info[] = new PlanningItemInfo[5];

        for(int i=0; i<5; i++){
            info[i] = new PlanningItemInfo();

            info[i].name = "강아지 기획전 이름"+(i+1);

            mItemList_dog.add(info[i]);
        }

        mItemList_cat = new ArrayList<PlanningItemInfo>();

        PlanningItemInfo info2[] = new PlanningItemInfo[7];

        for(int i=0; i<7; i++){
            info2[i] = new PlanningItemInfo();

            info2[i].name = "고양이 기획전 이름"+(i+1);

            mItemList_cat.add(info2[i]);
        }

        btn_dog = (Button) v.findViewById(R.id.btn_planning_dog);
        btn_dog.setOnClickListener(this);

        btn_cat = (Button) v.findViewById(R.id.btn_planning_cat);
        btn_cat.setOnClickListener(this);

        spin_category = (Spinner) v.findViewById(R.id.spin_planning_category);

        listView = (PullToRefreshListView) v.findViewById(R.id.list_planning);
        listAdapter_dog = new PlanningListAdapter(getActivity().getApplicationContext(), mItemList_dog);
        listAdapter_cat = new PlanningListAdapter(getActivity().getApplicationContext(), mItemList_cat);

        listView.setAdapter(listAdapter_dog);

        return v;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public void setAdapter(int selected){
        if(selected == SELECT_DOG){
            listView.setAdapter(listAdapter_dog);

        }else if(selected == SELECT_CAT){
            listView.setAdapter(listAdapter_cat);

        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_planning_dog :
                selected = SELECT_DOG;
                setAdapter(selected);
                break;

            case R.id.btn_planning_cat :
                selected = SELECT_CAT;
                setAdapter(selected);
                break;
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
