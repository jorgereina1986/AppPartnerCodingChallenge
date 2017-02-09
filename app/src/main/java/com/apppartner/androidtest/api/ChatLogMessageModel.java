package com.apppartner.androidtest.api;

import com.google.gson.annotations.SerializedName;

/**
 * A data model that represents a chat log message fetched from the AppPartner Web Server.
 * <p/>
 * Created on 8/27/16.
 *
 * @author Thomas Colligan
 */

public class ChatLogMessageModel
{
    @SerializedName("user_id")
    public int userId;
    @SerializedName("avatar_url")
    public String avatarUrl;
    @SerializedName("username")
    public String username;
    @SerializedName("message")
    public String message;
}
