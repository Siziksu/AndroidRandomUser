package com.siziksu.ru.presenter.main;

import android.support.annotation.NonNull;

import com.siziksu.ru.common.functions.Consumer;
import com.siziksu.ru.common.model.response.users.User;
import com.siziksu.ru.presenter.BasePresenter;

public abstract class IMainPresenter extends BasePresenter<IMainView> {

    public abstract void getUsersFromSwipeRefresh();

    public abstract void getUsers();

    public abstract void getUsers(int page);

    public abstract void showUserInfo(@NonNull User user);

    public abstract void showDeleteUserConfirmation(int dialogType, @NonNull Consumer listener);
}
