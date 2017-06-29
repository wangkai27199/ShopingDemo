package com.example.dell.shopingdemo.model;


import com.example.dell.shopingdemo.bean.IndexBean;
import com.example.dell.shopingdemo.network.BaseObserver;
import com.example.dell.shopingdemo.network.RetrofitFactory;
import com.google.gson.Gson;

/**
 * Created by muhanxi on 17/6/20.
 */

public class IndexFragmentModelImpl implements IndexFragmentModel  {




    private String [] url = {"http://lib.suning.com/app/redbaby/80000_8.2.0-155.json",
            "http://lib.suning.com/app/redbaby/babydiapers-64.json",
                            "http://lib.suning.com/app/redbaby/babymilk-41.json",
                            "http://lib.suning.com/app/redbaby/BabyBottles-56.json",
    "http://lib.suning.com/app/redbaby/babytoys-50.json",
    "http://lib.suning.com/app/redbaby/babyclothes-49.json",
    "http://lib.suning.com/app/redbaby/babybooks-39.json"} ;



    public void getData(int pos, final IndexFragmentModelListener listener){


        RetrofitFactory.get(url[pos], new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                IndexBean indexBean =  gson.fromJson(result, IndexBean.class);

                listener.onSuccess(indexBean);
            }

            @Override
            public void onFailed(int code) {
                listener.onFailed(code);

            }
        });




    }



}
