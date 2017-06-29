package com.example.dell.shopingdemo.network;

import com.example.dell.shopingdemo.network.cookie.CookiesManager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by muhanxi on 17/6/19.
 */

public class RetrofitFactory {




    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookiesManager())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS)
            .addInterceptor(new LoggingInterceptor())
            .build();


    public  static ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://qbh.2dyt.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            //把 以前的 call 转化成 Observable
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ApiService.class);





    public  static void get(String url,Observer<String> observer){
         apiService.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }




    public static void get(String url, Map<String,String> map, Observer<String> observer){

         apiService.get(url,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(observer);
    }





    public static void post(String url,Map<String,String> map, Observer<String> observer){
          apiService.post(url,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(observer);
    }





}
