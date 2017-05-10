package com.siziksu.ru.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.siziksu.ru.App;
import com.siziksu.ru.common.CollectionsManager;
import com.siziksu.ru.common.ConnectionManager;
import com.siziksu.ru.common.PreferencesManager;
import com.siziksu.ru.dagger.scope.AppScope;
import com.siziksu.ru.ui.main.IUsersAdapter;
import com.siziksu.ru.ui.main.MainPagination;
import com.siziksu.ru.ui.main.UsersAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    @AppScope
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @AppScope
    PreferencesManager providesPreferencesManager(SharedPreferences sharedPreferences) {
        return new PreferencesManager(sharedPreferences);
    }

    @Provides
    CollectionsManager providesCollectionsManager(PreferencesManager preferencesManager) {
        return new CollectionsManager(preferencesManager);
    }

    @Provides
    ConnectionManager providesConnectionManager(Context context) {
        return new ConnectionManager(context);
    }

    @Provides
    MainPagination providesMainPagination() {
        return new MainPagination();
    }

    @Provides
    IUsersAdapter providesUsersAdapter(UsersAdapter adapter) {
        return adapter;
    }
}
