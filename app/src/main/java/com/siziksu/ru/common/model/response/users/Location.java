package com.siziksu.ru.common.model.response.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.text.WordUtils;

public class Location {

    @SerializedName("street")
    @Expose
    public String street;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("postcode")
    @Expose
    public Integer postcode;

    public String street() {
        return WordUtils.capitalizeFully(street);
    }

    public String cityAndState() {
        return WordUtils.capitalizeFully(city + ", " + state);
    }
}
