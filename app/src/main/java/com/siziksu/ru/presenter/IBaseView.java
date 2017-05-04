package com.siziksu.ru.presenter;

import android.app.Activity;

public interface IBaseView {

    Activity getActivity();

    void showProgress(boolean value);
}
