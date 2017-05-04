package com.siziksu.ru.common;

import android.content.SharedPreferences;

import javax.inject.Inject;

public final class PreferencesManager {

    @Inject
    SharedPreferences sharedPreferences;

    public PreferencesManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void deleteKey(String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}