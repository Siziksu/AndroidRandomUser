package com.siziksu.ru.common.model.response.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {

    @SerializedName("results")
    @Expose
    public List<User> users = null;
    @SerializedName("info")
    @Expose
    public Info info;
}
