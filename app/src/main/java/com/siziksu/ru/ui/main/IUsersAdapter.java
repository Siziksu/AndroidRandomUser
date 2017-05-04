package com.siziksu.ru.ui.main;

import android.support.v7.widget.RecyclerView;

import com.siziksu.ru.common.functions.Consumer;
import com.siziksu.ru.common.functions.Provider;
import com.siziksu.ru.common.model.response.users.User;

import java.util.List;

public interface IUsersAdapter {

    void init(UsersAdapter.ClickListener listener, Consumer endOfListListener, Provider<Integer> noResultsListener);

    User getItem(int position);

    boolean isEmpty();

    void clearLists();

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.OnScrollListener getOnScrollListener();

    void showUsers(List<User> list, boolean more);

    void filterUsers(String filter);

    RecyclerView.Adapter getAdapter();

    boolean isFiltered();

    void clearFilter();

    void deleteUser(int position);

    void restoreBlockedUsers();
}
