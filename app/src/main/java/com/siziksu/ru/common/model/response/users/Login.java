package com.siziksu.ru.common.model.response.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("salt")
    @Expose
    public String salt;
    @SerializedName("md5")
    @Expose
    public String md5;
    @SerializedName("sha1")
    @Expose
    public String sha1;
    @SerializedName("sha256")
    @Expose
    public String sha256;
}
