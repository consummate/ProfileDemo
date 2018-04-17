package com.tal.abctime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tal.abctime.utils.Utils;
import com.tal.abctime.view.EditTextWithDel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tal.abctime.PhoneLoginFragment.VerifyState.COUNT;
import static com.tal.abctime.PhoneLoginFragment.VerifyState.DISABLED;
import static com.tal.abctime.PhoneLoginFragment.VerifyState.NORMAL;
import static com.tal.abctime.PhoneLoginFragment.VerifyState.RESENT;

/**
 * Created by irene on 2018/4/17.
 */

public class PhoneLoginFragment extends android.support.v4.app.Fragment {
    public static final String TAG = "PhoneLoginFragment";
    @BindView(R.id.et_bindphone_phone)
    EditTextWithDel mEtPhoneNumber;
    @BindView(R.id.et_bindphone_verify)
    EditTextWithDel mEtVerifyNumber;
    @BindView(R.id.tv_bindphone_hint)
    TextView mTvPhoneHint;
    @BindView(R.id.tv_bp_overtimehint)
    TextView mTvOvertimeHint;
    @BindView(R.id.btn_bp_send_verify)
    Button mBtnSendVerify;
    @BindView(R.id.btn_bp_submit)
    Button mBtnSubmit;
    @BindView(R.id.imgbtn_bp_verify_del)
    ImageButton mBtnVerifyDel;
    @BindView(R.id.btn_bp_back)
    ImageButton mBtnBack;
    @BindView(R.id.btn_bp_skip)
    Button mBtnSkip;

    private Context mContext;
    boolean isVerifyOvertime = false;

    public static PhoneLoginFragment newInstance() {
        return new PhoneLoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bind_phone_page, container, false);
        ButterKnife.bind(this, v);
        checkSubmitBtnState();
        mEtPhoneNumber.setOnTextClearListener(new EditTextWithDel.OnTextClearListener() {
            @Override
            public void onEmpty() {
                mTvPhoneHint.setVisibility(View.GONE);
                setVerifyBtnState(DISABLED,"");
                checkSubmitBtnState();
            }

            @Override
            public void onNotEmpty() {
                mTvPhoneHint.setVisibility(View.VISIBLE);
                String inputNumber = mEtPhoneNumber.getText().toString();
                if (Utils.isPhoneNumber(inputNumber)) {
                    setVerifyBtnState(NORMAL,"");
                } else {
                    setVerifyBtnState(DISABLED,"");
                }
                checkSubmitBtnState();
            }
        });
        if (mEtPhoneNumber.getText().length()<1) {
            mTvPhoneHint.setVisibility(View.GONE);
            setVerifyBtnState(DISABLED,"");
        }

        mEtVerifyNumber.setOnTextClearListener(new EditTextWithDel.OnTextClearListener() {
            @Override
            public void onEmpty() {
                mBtnVerifyDel.setVisibility(View.GONE);
                checkSubmitBtnState();
            }

            @Override
            public void onNotEmpty() {
                mBtnVerifyDel.setVisibility(View.VISIBLE);
                checkSubmitBtnState();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @OnClick(R.id.btn_bp_send_verify)
    void sendVerify() {
        isVerifyOvertime = false;
        setOvertimeHintVisible(false);
        Toast.makeText(mContext, "sendVerify", Toast.LENGTH_SHORT).show();
        new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setVerifyBtnState(COUNT, millisUntilFinished/1000+" s");
            }

            @Override
            public void onFinish() {
                isVerifyOvertime = true;
                mTvOvertimeHint.setVisibility(View.VISIBLE);
                setVerifyBtnState(RESENT,"");
            }
        }.start();
        checkSubmitBtnState();
    }

    @OnClick(R.id.imgbtn_bp_verify_del)
    void clearVerifyNumber() {
        mEtVerifyNumber.setText("");
    }

    @OnClick(R.id.btn_bp_submit)
    void sumbit() {
        Toast.makeText(mContext,"submit",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_bp_back)
    void back() {
        getActivity().onBackPressed();
    }

    //TODO: skip
    @OnClick(R.id.btn_bp_skip)
    void skip() {
        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        startActivity(intent);
    }

    void checkSubmitBtnState() {
        if (Utils.isPhoneNumber(mEtPhoneNumber.getText().toString())
                && Utils.isVerifyNumber(mEtVerifyNumber.getText().toString()) && !isVerifyOvertime) {
            mBtnSubmit.setEnabled(true);
        } else {
            mBtnSubmit.setEnabled(false);
        }
    }

    void setOvertimeHintVisible(boolean state) {
        if (state) {
            if (mTvOvertimeHint.getVisibility() == View.GONE) {
                mTvOvertimeHint.setVisibility(View.VISIBLE);
            }
        } else {
            if (mTvOvertimeHint.getVisibility() == View.VISIBLE) {
                mTvOvertimeHint.setVisibility(View.GONE);
            }
        }
    }

    private void setVerifyBtnState(VerifyState state, String msg) {
        switch (state) {
            case NORMAL:
                isVerifyOvertime = false;
                setOvertimeHintVisible(false);
                mBtnSendVerify.setText("发送验证码");
                mBtnSendVerify.setEnabled(true);
                mBtnSendVerify.setBackground(getResources().getDrawable(R.drawable.btn_verify_bg_enabled, null));
                break;
            case COUNT:
                mBtnSendVerify.setText(msg);
                mBtnSendVerify.setEnabled(false);
                mBtnSendVerify.setBackground(getResources().getDrawable(R.drawable.btn_verify_bg_count, null));
                break;
            case RESENT:
                mBtnSendVerify.setText("重新发送");
                mBtnSendVerify.setEnabled(true);
                mBtnSendVerify.setBackground(getResources().getDrawable(R.drawable.btn_verify_bg_resend, null));
                break;
            case DISABLED:
                setOvertimeHintVisible(false);
                mBtnSendVerify.setText("发送验证码");
                mBtnSendVerify.setEnabled(false);
                mBtnSendVerify.setBackground(getResources().getDrawable(R.drawable.btn_verify_bg_disabled, null));
                break;
        }
    }

    enum VerifyState {
        NORMAL,COUNT,RESENT,DISABLED
    }

}