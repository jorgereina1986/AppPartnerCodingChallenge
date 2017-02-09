package com.apppartner.androidtest.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LogInService {

    //http://dev3.apppartner.com/AppPartnerDeveloperTest/scripts/login.php
    @FormUrlEncoded
    @POST("AppPartnerDeveloperTest/scripts/login.php")
    Call<ResponseBody> updateUser(@Field("username") String username, @Field("password") String passwrod);
}
