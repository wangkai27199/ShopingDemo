package com.example.dell.shopingdemo.network;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by muhanxi on 17/6/19.
 */

public interface ApiService {





    @GET
    public Observable<String> get(@Url String url);


    @GET
    public Observable<String> get(@Url String url, @QueryMap Map<String, String> map);



    @FormUrlEncoded
    @POST
    public Observable<String> post(@Url String url, @FieldMap Map<String, String> map);




}
