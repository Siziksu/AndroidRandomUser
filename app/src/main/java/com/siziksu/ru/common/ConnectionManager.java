package com.siziksu.ru.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.siziksu.ru.common.model.Connection;

public final class ConnectionManager {

    private Context context;

    public ConnectionManager(Context context) {
        this.context = context;
    }

    public Connection getConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Network[] networks = connectivityManager.getAllNetworks();
                for (Network mNetwork : networks) {
                    networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                    if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        break;
                    }
                }
            } else {
                NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
                if (networkInfos != null) {
                    for (NetworkInfo info : networkInfos) {
                        if (info.getState() == NetworkInfo.State.CONNECTED) {
                            networkInfo = info;
                            break;
                        }
                    }
                }
            }
        }
        if (networkInfo != null
            && networkInfo.getDetailedState() != NetworkInfo.DetailedState.DISCONNECTED
            && networkInfo.getState() != NetworkInfo.State.DISCONNECTED
            && networkInfo.getDetailedState() != NetworkInfo.DetailedState.CONNECTING
            && networkInfo.getState() != NetworkInfo.State.CONNECTING) {
            return new Connection(true, networkInfo.getTypeName());
        } else {
            return new Connection(false, null);
        }
    }
}
