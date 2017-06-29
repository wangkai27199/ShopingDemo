package com.example.dell.shopingdemo.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.shopingdemo.R;
import com.example.dell.shopingdemo.adapters.FirstFragmentAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first, container, false);

        initView(view);

        return view ;
    }

    private void initView(View view) {


       TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout_id);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.first_viewpager);


        FirstFragmentAdapter adapters = new FirstFragmentAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapters);


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

}
