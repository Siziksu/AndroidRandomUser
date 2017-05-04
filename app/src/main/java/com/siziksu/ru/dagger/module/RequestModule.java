package com.siziksu.ru.dagger.module;

import com.siziksu.ru.domain.main.IMainRequests;
import com.siziksu.ru.domain.main.MainRequests;

import dagger.Module;
import dagger.Provides;

@Module
public final class RequestModule {

    public RequestModule() {}

    @Provides
    IMainRequests providesMainRequests(MainRequests request) {
        return request;
    }
}
