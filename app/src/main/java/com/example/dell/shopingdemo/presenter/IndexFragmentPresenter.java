package com.example.dell.shopingdemo.presenter;


import com.example.dell.shopingdemo.base.BasePresenter;
import com.example.dell.shopingdemo.bean.IndexBean;
import com.example.dell.shopingdemo.model.IndexFragmentModel;
import com.example.dell.shopingdemo.model.IndexFragmentModelImpl;
import com.example.dell.shopingdemo.view.IndexFragmentView;

/**
 * Created by muhanxi on 17/6/20.
 */

public class IndexFragmentPresenter extends BasePresenter<IndexFragmentView> {


    private IndexFragmentModelImpl indexFragmentModel ;

    public IndexFragmentPresenter(){
        indexFragmentModel = new IndexFragmentModelImpl();
    }




    //获取数据
    public void getData(int pos){

        indexFragmentModel.getData(pos, new IndexFragmentModel.IndexFragmentModelListener() {
            @Override
            public void onSuccess(IndexBean indexBean) {

                view.onSuccess(indexBean);
            }

            @Override
            public void onFailed(int code) {

                view.onFailed(code);
            }
        });


    }


}
