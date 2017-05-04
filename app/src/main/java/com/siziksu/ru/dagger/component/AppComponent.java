package com.siziksu.ru.dagger.component;

import com.siziksu.ru.dagger.module.AppModule;
import com.siziksu.ru.dagger.module.NetModule;
import com.siziksu.ru.dagger.module.PresenterModule;
import com.siziksu.ru.dagger.module.RequestModule;
import com.siziksu.ru.dagger.scope.AppScope;
import com.siziksu.ru.ui.main.MainActivity;
import com.siziksu.ru.ui.main.MainBottomSheetFragment;

import dagger.Component;

@AppScope
@Component(
        modules = {
                AppModule.class,
                PresenterModule.class,
                RequestModule.class,
                NetModule.class
        }
)
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(MainBottomSheetFragment fragment);
}
