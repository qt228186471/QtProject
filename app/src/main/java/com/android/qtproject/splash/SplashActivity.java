package com.android.qtproject.splash;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.android.qtproject.R;
import com.android.qtproject.home.HomeActivity;
import com.example.base.BaseActivity;
import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;

/**
 * qt
 * 2019-09-29
 */
public class SplashActivity extends BaseActivity implements ISplashConstract.ISplashView {
    private ImageView imageView;
    private Button countDownNum;
    private ISplashConstract.ISplashPresent splashPresent;
    private static final long COUNT_DOWN_TIME = 3500l;

    @Override
    protected IMvpBaseView getIBaseView() {
        return this;
    }

    @Override
    protected void loadData() {
        showSplash();
        splashPresent.startCountdown(COUNT_DOWN_TIME);
    }

    @Override
    protected void initView() {
        imageView = findViewById(R.id.splash_iv);
        countDownNum = findViewById(R.id.splash_btn);

        countDownNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splashPresent.cancelCountdown();
            }
        });
    }

    @Override
    public IMvpBasePresent createPresent() {
        splashPresent = new SplashPresent();
        return splashPresent;
    }

    @Override
    public int getRes() {
        return R.layout.activity_splash;
    }

    @Override
    public void jump() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showSplash() {
        imageView.setBackgroundResource(R.drawable.splash);
    }

    @Override
    public void showCountNum(long l) {
        countDownNum.setText(String.valueOf(l));
    }

    @Override
    public Context getContext() {
        return this;
    }
}
