package com.tal.abctime;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tal.abctime.utils.DeviceInfo;
import com.tal.abctime.utils.LocalConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by irene on 2018/4/13.
 */

public class WelcomeActivity extends Activity {
    @BindView(R.id.wel_btn1)
    Button mButton1;
    @BindView(R.id.wel_btn2)
    Button mButton2;
    @BindView(R.id.wel_tv)
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_fragment);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.wel_btn1)
    public void startLoginPage(View view) {
        SharedPreferences sp = getSharedPreferences(LocalConstants.LOCAL_SP, MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putBoolean("isfirst", true);

        et.apply();

    }

    @OnClick(R.id.wel_btn2)
    public void startProfilePage(View view) {
        isFirstlogin();
    }

    private void isFirstlogin() {
        //获取SharedPreferences对象，这个方法没有使用自定义类的方法来获取对象
        SharedPreferences sp = getSharedPreferences(LocalConstants.LOCAL_SP, MODE_PRIVATE);
        //获取SharedPreferences对象里面的某一个值
        boolean isfirst = sp.getBoolean("isfirst", true);
        //对这个值进行判断
        if (isfirst) {
            //如果是第一次登陆，添加登陆过的标记，并显示第一次登陆
//            SharedPreferences.Editor et = sp.edit();
//            et.putBoolean("isfirst", false);
//            et.commit();
            Toast.makeText(this, "第一次登陆", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

}
