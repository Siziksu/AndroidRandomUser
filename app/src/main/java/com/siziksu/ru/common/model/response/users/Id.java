package com.siziksu.ru.common.model.response.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Id {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("value")
    @Expose
    public String value;

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public boolean equals(Object obj) {
        Objects.requireNonNull(obj);
        return obj instanceof Id
               && name.equalsIgnoreCase(((Id) obj).name)
               && value.equalsIgnoreCase(((Id) obj).value);
    }

    @Override
    public String toString() {
        return "Id {name: '" + name + "', value: '" + value + "'}";
    }
}
