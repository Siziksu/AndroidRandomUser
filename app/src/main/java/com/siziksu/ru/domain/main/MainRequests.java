package com.siziksu.ru.domain.main;

import com.siziksu.ru.common.model.response.users.Users;
import com.siziksu.ru.data.client.IRandomUserClientService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public final class MainRequests implements IMainRequests {

    @Inject
    IRandomUserClientService service;

    @Inject
    MainRequests() {}

    @Override
    public Observable<Users> getUsers(int page, int results) {
        return service.getUsers(page, results)
                      .subscribeOn(Schedulers.io());
    }
}
