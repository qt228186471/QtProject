package com.android.qtproject.splash;

import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;

/**
 * qt
 * 2019-10-18
 */
public interface ISplashConstract {
    interface ISplashView extends IMvpBaseView{
        void jump();
        void showSplash();

        void showCountNum(long l);
    }

    interface ISplashPresent extends IMvpBasePresent<ISplashView>{
        /**
         * 开启倒计时
         * @param millis 毫秒级
         */
        void startCountdown(long millis);

        void cancelCountdown();
    }
}
