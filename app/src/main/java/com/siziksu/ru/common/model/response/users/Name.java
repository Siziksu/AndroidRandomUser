package com.siziksu.ru.common.model.response.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.text.WordUtils;

public class Name {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("first")
    @Expose
    public String first;
    @SerializedName("last")
    @Expose
    public String last;

    public String fullName() {
        return WordUtils.capitalizeFully(first + " " + last);
    }

    public String fullNameInTwoLines() {
        return WordUtils.capitalizeFully(first + "\n" + last);
    }
}
