package com.siziksu.ru.common.model.response.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.siziksu.ru.common.DatesManager;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Objects;

public class User {

    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("name")
    @Expose
    public Name name;
    @SerializedName("location")
    @Expose
    public Location location;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("login")
    @Expose
    public Login login;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("registered")
    @Expose
    public String registered;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("cell")
    @Expose
    public String cell;
    @SerializedName("id")
    @Expose
    public Id id;
    @SerializedName("picture")
    @Expose
    public Picture picture;
    @SerializedName("nat")
    @Expose
    public String nat;

    public String gender() {
        return WordUtils.capitalizeFully(gender);
    }

    public String registered() {
        return DatesManager.getRegisteredDate(registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name.first, name.last);
    }

    @Override
    public boolean equals(Object obj) {
        Objects.requireNonNull(obj);
        return obj instanceof User
               && id.equals(((User) obj).id)
               && name.first.equalsIgnoreCase(((User) obj).name.first)
               && name.last.equalsIgnoreCase(((User) obj).name.last);
    }

    @Override
    public String toString() {
        return "User {" + id + ", name: '" + name.first + "', name: '" + name.last + "'}";
    }
}
