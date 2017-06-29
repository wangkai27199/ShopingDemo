package com.example.dell.shopingdemo.presenter;

import com.example.dell.shopingdemo.base.BasePresenter;
import com.example.dell.shopingdemo.model.SplashModelImpl;
import com.example.dell.shopingdemo.view.SplashView;


/**
 * Created by muhanxi on 17/6/19.
 */

public class SplashPresenter extends BasePresenter<SplashView> {


    private SplashModelImpl splashModel ;


    public SplashPresenter(){

        splashModel = new SplashModelImpl();



    }



    public void getData(){

        System.out.println("view = " + view);

        splashModel.getData();
    }


}
