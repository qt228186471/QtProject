package com.android.qtproject.splash;

import android.os.CountDownTimer;

/**
 * qt
 * 2019-10-18
 */
public class SplashPresent implements ISplashConstract.ISplashPresent {
    private ISplashConstract.ISplashView view;
    private CountDownTimer countDownTimer;
    private static final long COUNT_DOWN_TIME_ALL = 3000L;
    private static final long COUNT_DOWN_TIME_INTERVAL = 1000L;

    @Override
    public void startCountdown(final long millis) {
        countDownTimer = new CountDownTimer(COUNT_DOWN_TIME_ALL, COUNT_DOWN_TIME_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (view != null) {
                    view.showCountNum(millisUntilFinished / COUNT_DOWN_TIME_INTERVAL);
                }
            }

            @Override
            public void onFinish() {
                if (view != null) {
                    view.jump();
                }
            }
        }.start();
    }

    @Override
    public void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        view.jump();
    }

    @Override
    public void attach(ISplashConstract.ISplashView iSplashView) {
        view = iSplashView;
    }

    @Override
    public void detach() {
        view = null;
    }
}
