package com.siziksu.ru.domain.main;

import com.siziksu.ru.common.model.response.users.Users;

import io.reactivex.Observable;

public interface IMainRequests {

    Observable<Users> getUsers(int page, int results);
}
