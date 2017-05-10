package com.siziksu.ru.presenter.main;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.siziksu.ru.R;
import com.siziksu.ru.common.ConnectionManager;
import com.siziksu.ru.common.Constants;
import com.siziksu.ru.common.PreferencesManager;
import com.siziksu.ru.common.functions.Consumer;
import com.siziksu.ru.common.model.response.users.User;
import com.siziksu.ru.common.model.response.users.Users;
import com.siziksu.ru.domain.main.IMainRequests;
import com.siziksu.ru.ui.main.MainBottomSheetFragment;
import com.siziksu.ru.ui.fragment.MessageFragment;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public final class MainPresenter extends IMainPresenter {

    @Inject
    ConnectionManager connectionManager;
    @Inject
    IMainRequests mainRequests;
    @Inject
    PreferencesManager preferencesManager;

    private static final int FIRST_PAGE = 1;

    private Disposable disposable;
    private boolean swipeOn;

    @Inject
    MainPresenter() {}

    @Override
    public void getUsersFromSwipeRefresh() {
        swipeOn = true;
        cancelProgress();
        getUsers(FIRST_PAGE);
    }

    @Override
    public void getUsers() {
        getUsers(FIRST_PAGE);
    }

    @Override
    public void getUsers(int page) {
        cancelSwipeRefresh();
        requestUsers(page);
    }

    private void requestUsers(int page) {
        cancelLastRequest(page);
        doIfThereIsConnection(() -> doIfViewIsRegistered(() -> {
            if (!swipeOn) {
                view.showProgress(true);
            }
            disposable = mainRequests.getUsers(page, Constants.PAGINATION_SIZE)
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .subscribe(this::onUsers,
                                                this::onError,
                                                () -> Log.i(Constants.TAG, "Users request done"));
        }));
    }

    private void cancelLastRequest(int page) {
        if (disposable != null && page == FIRST_PAGE) {
            disposable.dispose();
            disposable = null;
        }
    }

    private void onUsers(Users response) {
        disposable = null;
        doIfViewIsRegistered(() -> view.showUsers(response.users, response.info.page));
        stopProgress();
    }

    private void onError(Throwable throwable) {
        disposable = null;
        Log.e(Constants.TAG, throwable.getMessage(), throwable);
        if (throwable instanceof SocketTimeoutException) {
            doIfViewIsRegistered(() -> view.connectionTimeout());
        }
        stopProgress();
    }

    private void cancelProgress() {
        doIfViewIsRegistered(() -> view.showProgress(false));
    }

    private void cancelSwipeRefresh() {
        if (swipeOn) {
            swipeOn = false;
            doIfViewIsRegistered(() -> view.stopRefreshing());
        }
    }

    private void stopProgress() {
        doIfViewIsRegistered(() -> {
            if (swipeOn) {
                view.stopRefreshing();
                swipeOn = false;
            } else {
                view.showProgress(false);
            }
        });
    }

    private void doIfViewIsRegistered(Consumer consumer) {
        if (isViewRegistered()) {
            consumer.consume();
        }
    }

    private void doIfThereIsConnection(Consumer consumer) {
        boolean isConnected = connectionManager.getConnection().isConnected();
        doIfViewIsRegistered(() -> {
            if (!isConnected) {
                Log.d(Constants.TAG, view.getActivity().getResources().getString(R.string.connection_error));
                stopProgress();
            } else {
                consumer.consume();
            }
            view.showConnected(isConnected);
        });
    }

    @Override
    public void showUserInfo(@NonNull User user) {
        String json = new Gson().toJson(user);
        preferencesManager.setString(Constants.PREFERENCES_KEY_USER, json);
        doIfViewIsRegistered(() -> {
            MainBottomSheetFragment bottomSheetFragment = new MainBottomSheetFragment();
            bottomSheetFragment.show(getFragmentManager(), Constants.FRAGMENT_TAG_MAIN_BOTTOM);
        });
    }

    @Override
    public void showDeleteUserConfirmation(int dialogType, @NonNull Consumer listener) {
        MessageFragment fragment = new MessageFragment();
        int title;
        int message;
        switch (dialogType) {
            case Constants.DIALOG_TYPE_DELETE_USER:
                title = R.string.user_delete;
                message = R.string.are_you_sure;
                break;
            case Constants.DIALOG_TYPE_RESTORE_BLOCKED_USERS:
                title = R.string.restore_blocked_users;
                message = R.string.are_you_sure;
                break;
            default:
                title = R.string.app_name;
                message = R.string.app_name;
                break;
        }
        fragment.setCallback(listener)
                .setTitle(view.getActivity().getString(title))
                .setMessage(view.getActivity().getString(message));
        fragment.show(getFragmentManager(), Constants.FRAGMENT_TAG_DIALOG_YES_NO);
    }

    private FragmentManager getFragmentManager() {
        return ((AppCompatActivity) view.getActivity()).getSupportFragmentManager();
    }
}
