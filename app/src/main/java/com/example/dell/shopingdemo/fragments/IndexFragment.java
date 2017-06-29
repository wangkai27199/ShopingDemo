package com.example.dell.shopingdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.dell.shopingdemo.R;
import com.example.dell.shopingdemo.adapters.IndexFragmentRecycleViewAdapter;
import com.example.dell.shopingdemo.base.BaseMvpFragment;
import com.example.dell.shopingdemo.bean.IndexBean;
import com.example.dell.shopingdemo.presenter.IndexFragmentPresenter;
import com.example.dell.shopingdemo.view.IndexFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class IndexFragment extends BaseMvpFragment<IndexFragmentView, IndexFragmentPresenter> implements IndexFragmentView {
    private static final String ARG_PARAM1 = "param1";
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private int mParam1;
    @BindView(R.id.recycleview_id)
    RecyclerView recyclerView;

    private IndexFragmentRecycleViewAdapter adapter;

    @Override
    public IndexFragmentPresenter initPresenter() {
        return new IndexFragmentPresenter();
    }

    public IndexFragment() {
    }


    public static IndexFragment newInstance(int param1) {
        IndexFragment fragment = new IndexFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView(view);

        return view;
    }


    public void initView(View view) {

//        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_id);
        adapter = new IndexFragmentRecycleViewAdapter(getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);


        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


        presenter.getData(mParam1);

    }


    @Override
    public void onSuccess(IndexBean indexBean) {

        indexBean.getData().remove(2);
        indexBean.getData().remove(3);

        adapter.setData(indexBean.getData());

    }

    @Override
    public void onFailed(int code) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
