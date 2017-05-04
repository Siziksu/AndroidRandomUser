package com.siziksu.ru.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.siziksu.ru.App;
import com.siziksu.ru.R;
import com.siziksu.ru.common.Constants;
import com.siziksu.ru.common.PreferencesManager;
import com.siziksu.ru.common.model.response.users.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MainBottomSheetFragment extends BottomSheetDialogFragment {

    @Inject
    PreferencesManager preferencesManager;

    @BindView(R.id.mbs_user_picture)
    ImageView userPicture;
    @BindView(R.id.mbs_user_full_name)
    TextView userFullName;
    @BindView(R.id.mbs_user_gender)
    TextView userGender;
    @BindView(R.id.mbs_user_location_street)
    TextView userLocationStreet;
    @BindView(R.id.mbs_user_location_city_state)
    TextView userLocationCityState;
    @BindView(R.id.mbs_user_register_date)
    TextView userRegisterDate;
    @BindView(R.id.mbs_user_email)
    TextView userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_main, container, false);
        ButterKnife.bind(this, view);
        App.get(getActivity().getApplication()).getAppComponent().inject(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String json = preferencesManager.getString(Constants.PREFERENCES_KEY_USER, "");
        User user = new Gson().fromJson(json, User.class);
        if (user != null) {
            String fullName = user.name.fullNameInTwoLines();
            String gender = user.gender();
            String location = user.location.street();
            String cityState = user.location.cityAndState();
            String registeredDate = user.registered();
            String email = user.email;
            String userPicture = user.picture.large;
            userFullName.setText(fullName);
            userGender.setText(gender);
            userLocationStreet.setText(location);
            userLocationCityState.setText(cityState);
            userRegisterDate.setText(registeredDate);
            userEmail.setText(email);
            Picasso.with(getActivity())
                   .load(userPicture)
                   .noFade()
                   .placeholder(R.drawable.user_placeholder)
                   .into(this.userPicture);
        }
    }
}