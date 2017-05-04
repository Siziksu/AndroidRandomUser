package com.siziksu.ru.ui.main;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siziksu.ru.R;
import com.siziksu.ru.common.CollectionsManager;
import com.siziksu.ru.common.Constants;
import com.siziksu.ru.common.PreferencesManager;
import com.siziksu.ru.common.model.response.users.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public final class UsersManager {

    @Inject
    PreferencesManager preferencesManager;
    @Inject
    CollectionsManager collectionsManager;

    private boolean filtered;
    private String filter;
    private List<User> users;
    private List<User> usersFiltered;

    @Inject
    UsersManager() {
        users = new ArrayList<>();
        usersFiltered = new ArrayList<>();
    }

    void filter(UsersAdapter usersAdapter, String filter) {
        this.filter = filter;
        Log.i(Constants.TAG, "Filter submitted: " + filter);
        filter = filter.trim().toLowerCase();
        resetUsersFiltered();
        if (!TextUtils.isEmpty(filter)) {
            for (User user : users) {
                if (!user.name.first.toLowerCase().contains(filter)) {
                    int index = usersFiltered.indexOf(user);
                    usersFiltered.remove(user);
                    usersAdapter.notifyItemRemoved(index);
                    filtered = true;
                }
            }
            if (filtered && usersFiltered.isEmpty() && usersAdapter.getNoResultsListener() != null) {
                usersAdapter.getNoResultsListener().provide(R.string.no_results_for_filter);
            }
        } else {
            filtered = false;
            usersAdapter.notifyDataSetChanged();
        }
        Log.i(Constants.TAG, "Total users filtered: " + usersFiltered.size());
    }

    void showUsers(UsersAdapter usersAdapter, List<User> list, boolean more) {
        if (!more) {
            users.clear();
            usersFiltered.clear();
        }
        showAllUsers(usersAdapter, list);
    }

    private void showAllUsers(UsersAdapter usersAdapter, List<User> list) {
        users.addAll(list);
        collectionsManager.removeDuplicates(users);
        collectionsManager.removeBlockedUsers(users);
        collectionsManager.sortUsersByName(users);
        Log.i(Constants.TAG, "Total users: " + users.size());
        resetUsersFiltered();
        if (!TextUtils.isEmpty(filter)) {
            filter(usersAdapter, filter);
        } else {
            usersAdapter.notifyDataSetChanged();
        }
    }

    void deleteUser(UsersAdapter usersAdapter, int position) {
        int string;
        User userToRemove = usersFiltered.get(position);
        blockUser(userToRemove);
        if (filtered) {
            usersFiltered.remove(position);
            usersAdapter.notifyItemRemoved(position);
            users.remove(userToRemove);
            string = R.string.no_results_for_filter;
        } else {
            users.remove(position);
            usersFiltered.remove(position);
            usersAdapter.notifyItemRemoved(position);
            string = R.string.no_data_available;
        }
        if (usersFiltered.isEmpty()) {
            usersAdapter.getNoResultsListener().provide(string);
        }
    }

    private void blockUser(User userToRemove) {
        List<User> blockedUsers = getBlockedUsers();
        blockedUsers.add(userToRemove);
        blockUsers(blockedUsers);
    }

    @NonNull
    private List<User> getBlockedUsers() {
        String jsonBlockedUsers = preferencesManager.getString(Constants.PREFERENCES_KEY_USERS_BLOCKED, "");
        List<User> blockedUsers = new Gson().fromJson(jsonBlockedUsers, new TypeToken<List<User>>() {}.getType());
        if (blockedUsers == null) {
            blockedUsers = new ArrayList<>();
        }
        return blockedUsers;
    }

    private void blockUsers(List<User> blockedUsers) {
        String jsonBlockedUsers = new Gson().toJson(blockedUsers);
        preferencesManager.setString(Constants.PREFERENCES_KEY_USERS_BLOCKED, jsonBlockedUsers);
    }

    void restoreBlockedUsers() {
        preferencesManager.deleteKey(Constants.PREFERENCES_KEY_USERS_BLOCKED);
    }

    private void resetUsersFiltered() {
        usersFiltered.clear();
        usersFiltered.addAll(users);
    }

    void clearLists(UsersAdapter usersAdapter) {
        users.clear();
        clearFilterList();
        usersAdapter.notifyDataSetChanged();
    }

    void clearFilter(UsersAdapter usersAdapter) {
        clearFilterList();
        usersFiltered.addAll(users);
        usersAdapter.notifyDataSetChanged();
    }

    private void clearFilterList() {
        usersFiltered.clear();
        filter = null;
        filtered = false;
    }

    User getItem(int position) {
        if (!usersFiltered.isEmpty() && position < usersFiltered.size()) {
            return usersFiltered.get(position);
        } else {
            return null;
        }
    }

    boolean isFiltered() {
        return filtered;
    }

    List<User> getUsersFiltered() {
        return usersFiltered;
    }
}
