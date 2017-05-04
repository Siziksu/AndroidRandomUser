package com.siziksu.ru.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siziksu.ru.R;
import com.siziksu.ru.common.Constants;
import com.siziksu.ru.common.functions.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MessageFragment extends AppCompatDialogFragment {

    @BindView(R.id.dialog_yes_no_message)
    TextView textView;

    private Consumer listener;
    private String title;
    private String message;
    private boolean hideTitle;

    public MessageFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_yes_no, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(TextUtils.isEmpty(title) ? Constants.TAG : title);
        return dialog;
    }

    @Override
    public int getTheme() {
        if (hideTitle) {
            return R.style.AppTheme_AppCompatDialogStyle_NoTitle;
        } else {
            return R.style.AppTheme_AppCompatDialogStyle;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView.setText(TextUtils.isEmpty(message) ? Constants.TAG : message);
    }

    public MessageFragment setCallback(Consumer listener) {
        this.listener = listener;
        return this;
    }

    public MessageFragment hideTitle() {
        hideTitle = true;
        return this;
    }

    public MessageFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public MessageFragment setMessage(String message) {
        this.message = message;
        return this;
    }

    @OnClick(R.id.dialog_yes_no_cancel)
    public void onCancelButtonClick() {
        dismiss();
    }

    @OnClick(R.id.dialog_yes_no_yes)
    public void onSubmitButtonClick() {
        if (listener != null) {
            listener.consume();
        }
        dismiss();
    }
}
