package com.example.dell.shopingdemo.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.dell.shopingdemo.R;


/**
 * 所有的 activity 都继承
 */
public class IActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i);





    }
}
