package com.example.dell.shopingdemo.model;

import com.example.dell.shopingdemo.network.BaseObserver;
import com.example.dell.shopingdemo.network.RetrofitFactory;

/**
 * Created by muhanxi on 17/6/19.
 */

public class SplashModelImpl {







    public void getData(){




        RetrofitFactory.get("http://qhb.2dyt.com/Bwei/login?username=11111111111&password=1&postkey=1503d",new BaseObserver() {
            @Override
            public void onSuccess(String result) {


                System.out.println("result = " + result);

            }

            @Override
            public void onFailed(int code) {


            }
        });


    }


}
