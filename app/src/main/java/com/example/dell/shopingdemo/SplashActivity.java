package com.example.dell.shopingdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.dell.shopingdemo.activitys.PermissionActivity;
import com.example.dell.shopingdemo.activitys.TabActivity;
import com.example.dell.shopingdemo.base.BaseMvpActivity;
import com.example.dell.shopingdemo.presenter.SplashPresenter;
import com.example.dell.shopingdemo.view.SplashView;

public class SplashActivity extends BaseMvpActivity<SplashView,SplashPresenter> {

    @Override
    public SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.getData();

        startActivity(new Intent(this, TabActivity.class));

    }
}
