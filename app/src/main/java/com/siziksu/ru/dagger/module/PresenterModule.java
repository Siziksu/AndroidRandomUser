package com.siziksu.ru.dagger.module;

import com.siziksu.ru.presenter.main.IMainPresenter;
import com.siziksu.ru.presenter.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public final class PresenterModule {

    public PresenterModule() {}

    @Provides
    IMainPresenter providesMainPresenter(MainPresenter presenter) {
        return presenter;
    }
}
