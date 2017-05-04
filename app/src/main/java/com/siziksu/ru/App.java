package com.siziksu.ru;

import android.app.Application;

import com.siziksu.ru.dagger.component.AppComponent;
import com.siziksu.ru.dagger.component.DaggerAppComponent;
import com.siziksu.ru.dagger.module.AppModule;
import com.siziksu.ru.dagger.module.NetModule;

public final class App extends Application {

    private AppComponent appComponent;

    public static App get(Application application) {
        return ((App) application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpDagger();
    }

    private void setUpDagger() {
        appComponent = DaggerAppComponent.builder()
                                         .appModule(new AppModule(this))
                                         .netModule(new NetModule())
                                         .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}