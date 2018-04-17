package com.tal.abctime;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by irene on 2018/4/16.
 */

public class LoginActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance();
    }
}
