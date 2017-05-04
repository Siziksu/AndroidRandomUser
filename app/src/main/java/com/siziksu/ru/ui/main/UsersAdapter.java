package com.siziksu.ru.ui.main;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.ru.R;
import com.siziksu.ru.common.functions.Consumer;
import com.siziksu.ru.common.functions.Provider;
import com.siziksu.ru.common.model.response.users.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public final class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IUsersAdapter {

    @Inject
    Context context;
    @Inject
    UsersManager usersManager;

    private ClickListener listener;
    private Consumer endOfListListener;
    private Provider<Integer> noResultsListener;
    private OnScrollListener onScrollListener;
    private GridLayoutManager layoutManager;

    @Inject
    UsersAdapter() {}

    public void init(ClickListener listener, Consumer endOfListListener, Provider<Integer> noResultsListener) {
        onScrollListener = new OnScrollListener();
        this.listener = listener;
        int spanCount = context.getResources().getInteger(R.integer.users_columns);
        layoutManager = new GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false);
        this.endOfListListener = endOfListListener;
        this.noResultsListener = noResultsListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UsersViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UsersViewHolder) {
            UsersViewHolder localHolder = (UsersViewHolder) holder;
            User user = usersManager.getUsersFiltered().get(position);
            localHolder.userCardFullName.setText(user.name.fullNameInTwoLines());
            localHolder.userCardEmail.setText(user.email);
            localHolder.userCardPhone.setText(user.phone);
            Picasso.with(context)
                   .load(user.picture.large)
                   .placeholder(R.drawable.user_placeholder_black)
                   .into(localHolder.userCardPicture);
        }
    }

    @Override
    public int getItemCount() {
        return usersManager.getUsersFiltered().size();
    }

    @Override
    public User getItem(int position) {
        return usersManager.getItem(position);
    }

    @Override
    public boolean isEmpty() {
        return usersManager.getUsersFiltered().isEmpty();
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public RecyclerView.OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    @Override
    public void showUsers(List<User> list, boolean more) {
        usersManager.showUsers(this, list, more);
    }

    @Override
    public void filterUsers(String filter) {
        usersManager.filter(this, filter);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void clearLists() {
        usersManager.clearLists(this);
    }

    @Override
    public void clearFilter() {
        usersManager.clearFilter(this);
    }

    @Override
    public void deleteUser(int position) {
        usersManager.deleteUser(this, position);
    }

    @Override
    public void restoreBlockedUsers() {
        usersManager.restoreBlockedUsers();
    }

    @Override
    public boolean isFiltered() {
        return usersManager.isFiltered();
    }

    public Provider<Integer> getNoResultsListener() {
        return noResultsListener;
    }

    private final class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!usersManager.getUsersFiltered().isEmpty() && (layoutManager.findLastCompletelyVisibleItemPosition() + 1) == usersManager.getUsersFiltered().size()) {
                if (endOfListListener != null) {
                    endOfListListener.consume();
                }
            }
        }
    }

    interface ClickListener {

        void onClick(View view, int position);
    }
}
