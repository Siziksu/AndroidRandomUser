package com.siziksu.ru.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.siziksu.ru.R;

import butterknife.BindView;
import butterknife.ButterKnife;

final class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.user_card_view)
    View userCardView;
    @BindView(R.id.user_card_delete)
    ImageButton userDelete;
    @BindView(R.id.user_card_picture)
    ImageView userCardPicture;
    @BindView(R.id.user_card_full_name)
    TextView userCardFullName;
    @BindView(R.id.user_card_email)
    TextView userCardEmail;
    @BindView(R.id.user_card_phone)
    TextView userCardPhone;

    private UsersAdapter.ClickListener listener;

    UsersViewHolder(View view, UsersAdapter.ClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        this.listener = listener;
        userCardPicture.setOnClickListener(this);
        userDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
