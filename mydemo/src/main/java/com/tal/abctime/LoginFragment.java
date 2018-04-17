package com.tal.abctime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by irene on 2018/4/13.
 */

public class LoginFragment extends Fragment {
    public static final String TAG = "LoginFragment";

    @BindView(R.id.btn_phone)
    Button mBtnPhotoLogin;
    @BindView(R.id.login_btn_skip)
    Button mBtnSkip;
    @BindView(R.id.login_btn_wechat)
    ImageButton mBtnWechatLogin;
    @BindView(R.id.login_btn_back)
    ImageButton mBtnBack;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_page, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.btn_phone)
    void callPhoneLoginPage() {
        Fragment phoneFragment = PhoneLoginFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, phoneFragment, "phone")
                .addToBackStack(PhoneLoginFragment.class.getSimpleName())
                .commit();
    }

    //TODO: skip
    @OnClick(R.id.login_btn_skip)
    void skip() {
        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        startActivity(intent);
    }

    //TODO: skip
    @OnClick(R.id.login_btn_back)
    void back() {
        getActivity().onBackPressed();
    }

    //TODO: wechat
    @OnClick(R.id.login_btn_wechat)
    void loginByWechat() {

    }
}
