package com.android.userlogin;

import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;

/**
 * qt
 * 2019-10-17
 */
public interface ILoginConstract {
    interface ILoginView extends IMvpBaseView{
        void checkInvalidate();
        void onSubmitClick();
        void onCancelClick();
        void onRegisterAccountClick();
        void showLoadingDialog();
        void dismissLoadingDialog();
    }

    interface ILoginPresent extends IMvpBasePresent<ILoginView>{
       void login();
       void register();
    }
}
