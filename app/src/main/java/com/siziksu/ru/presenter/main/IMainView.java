package com.siziksu.ru.presenter.main;

import com.siziksu.ru.common.model.response.users.User;
import com.siziksu.ru.presenter.IBaseView;

import java.util.List;

public interface IMainView extends IBaseView {

    void showUsers(List<User> users, int page);

    void showConnected(boolean value);

    void stopRefreshing();
}
