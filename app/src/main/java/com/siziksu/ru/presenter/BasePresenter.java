package com.siziksu.ru.presenter;

public abstract class BasePresenter<V extends IBaseView> {

    protected V view;

    public void register(final V view) {
        this.view = view;
    }

    public void unregister() {
        this.view = null;
    }

    protected boolean isViewRegistered() {
        return view != null;
    }
}
