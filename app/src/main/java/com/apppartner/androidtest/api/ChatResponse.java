package com.apppartner.androidtest.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatResponse {

    @SerializedName("data")
    List<ChatLogMessageModel> data = null;

    public List<ChatLogMessageModel> getData() {
        return data;
    }

    public void setData(List<ChatLogMessageModel> data) {
        this.data = data;
    }
}
