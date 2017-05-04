package com.siziksu.ru.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siziksu.ru.common.model.response.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CollectionsManager {

    private PreferencesManager preferencesManager;

    public CollectionsManager(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    public <T> void removeDuplicates(List<T> objects) {
        Set<T> objectsSet = new HashSet<>();
        objectsSet.addAll(objects);
        objects.clear();
        objects.addAll(objectsSet);
    }

    public void sortUsersByName(List<User> users) {
        Collections.sort(users, (user1, user2) -> user1.name.first.compareToIgnoreCase(user2.name.first));
    }

    public void removeBlockedUsers(List<User> users) {
        String jsonBlockedUsers = preferencesManager.getString(Constants.PREFERENCES_KEY_USERS_BLOCKED, "");
        List<User> blockedUsers = new Gson().fromJson(jsonBlockedUsers, new TypeToken<List<User>>() {}.getType());
        if (blockedUsers == null) {
            blockedUsers = new ArrayList<>();
        }
        users.removeAll(blockedUsers);
    }
}
