package com.example.dell.shopingdemo.view;

import com.example.dell.shopingdemo.bean.IndexBean;

/**
 * Created by muhanxi on 17/6/20.
 */

public interface IndexFragmentView {



    public void onSuccess(IndexBean indexBean);

    public void onFailed(int code);

}
