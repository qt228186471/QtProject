package com.android.userlogin;

/**
 * qt
 * 2019-10-17
 */
public class LoginPresent implements ILoginConstract.ILoginPresent {
    private ILoginConstract.ILoginView iLoginView;

    @Override
    public void login() {

    }

    @Override
    public void register() {

    }

    @Override
    public void attach(ILoginConstract.ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void detach() {
        iLoginView = null;
    }
}
