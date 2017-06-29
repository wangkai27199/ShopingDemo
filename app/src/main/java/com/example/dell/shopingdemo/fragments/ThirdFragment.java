package com.example.dell.shopingdemo.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.shopingdemo.R;
import com.example.dell.shopingdemo.adapters.ThirdFragmentAdapter;
import com.example.dell.shopingdemo.base.BaseMvpFragment;
import com.example.dell.shopingdemo.bean.ShopBean;
import com.example.dell.shopingdemo.presenter.ThirdFragmentPresenter;
import com.example.dell.shopingdemo.utils.StringUtils;
import com.example.dell.shopingdemo.view.ThirdFragmentView;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends BaseMvpFragment<ThirdFragmentView,ThirdFragmentPresenter> {

    @BindView(R.id.third_recyclerview)
    RecyclerView thirdRecyclerview;
    @BindView(R.id.third_allselect)
    TextView thirdAllselect;
    @BindView(R.id.third_totalprice)
    TextView thirdTotalprice;
    @BindView(R.id.third_totalnum)
    TextView thirdTotalnum;
    @BindView(R.id.third_submit)
    TextView thirdSubmit;
    @BindView(R.id.third_pay_linear)
    LinearLayout thirdPayLinear;

    Unbinder unbinder;
    private InputStream inputStream;
    private ThirdFragmentAdapter adapter;

    @Override
    public ThirdFragmentPresenter initPresenter() {
        return new ThirdFragmentPresenter();
    }

    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_third, container, false);

        unbinder = ButterKnife.bind(this,view);
        showData();
        return view;
    }

    private List<ShopBean.OrderDataBean.CartlistBean> mAllOrderList = new ArrayList<ShopBean.OrderDataBean.CartlistBean>();
    private void showData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        adapter = new ThirdFragmentAdapter(getActivity());
        thirdRecyclerview.setAdapter(adapter);
        thirdRecyclerview.setLayoutManager(linearLayoutManager);

        try {

            InputStream inputStream = getActivity().getAssets().open("shop.json");
            String data = StringUtils.convertStreamToString(inputStream);
            System.out.println("data = " + data);
            Gson gson = new Gson();
            ShopBean shopBean = gson.fromJson(data, ShopBean.class);

            for (int i = 0;i < shopBean.getOrderData().size();i++){
                int length = shopBean.getOrderData().get(i).getCartlist().size();
                for (int j = 0;j<length;j++){
                    mAllOrderList.add(shopBean.getOrderData().get(i).getCartlist().get(j));
                }
            }

            setFitstState(mAllOrderList);
            adapter.setData(mAllOrderList);

        }catch (Exception e){
            e.printStackTrace();
        }

        //删除数据回调
        adapter.setOnDeleteClickListener(new ThirdFragmentAdapter.OnDeleteClickListener(){

            @Override
            public void onDeleteClick(View view, int position, int cartid) {

            }
        });

        adapter.setOnRefershListener(new ThirdFragmentAdapter.OnRefershListener() {
            @Override
            public void onRefersh(boolean isSelect, List<ShopBean.OrderDataBean.CartlistBean> list) {
                //标记底部的全选按钮
                if (isSelect){
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    thirdAllselect.setCompoundDrawablesWithIntrinsicBounds(left,null,null,null);
                }else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    thirdAllselect.setCompoundDrawablesWithIntrinsicBounds(left,null,null,null);
                }

                //总价
                float mTotlaPrice = 0f;
                int mTotlaNum = 0;
                for (int i = 0;i<list.size();i++){
                    if (list.get(i).isSelect()){
                        mTotlaPrice += list.get(i).getPrice() * list.get(i).getCount();
                        mTotlaNum += list.get(i).getCount();
                    }
                }

                System.out.println("mTotlaPrice = " + mTotlaPrice);
                thirdTotalprice.setText("总价："+mTotlaPrice);
                thirdTotalnum.setText("共"+mTotlaNum+"件商品");
            }
        });


    }

    public static void setFitstState(List<ShopBean.OrderDataBean.CartlistBean> list){

        if (list.size() > 0){
            list.get(0).setIsFirst(1);
            for (int i = 1;i<list.size();i++){
                if (list.get(i).getShopId() == list.get(i-1).getShopId()){
                    list.get(i).setIsFirst(2);
                }else {
                    list.get(i).setIsFirst(1);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
