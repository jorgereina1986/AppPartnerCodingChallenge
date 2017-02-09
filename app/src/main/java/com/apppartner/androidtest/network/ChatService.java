package com.apppartner.androidtest.network;

import com.apppartner.androidtest.api.ChatResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChatService {

    //http://dev3.apppartner.com/AppPartnerDeveloperTest/scripts/chat_log.php
    @GET("AppPartnerDeveloperTest/scripts/chat_log.php")
    Call<ChatResponse> listMessages();
}
