package com.android.userlogin;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.base.BaseActivity;
import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;

public class LoginActivity extends BaseActivity implements ILoginConstract.ILoginView {
    private ILoginConstract.ILoginPresent iLoginPresent;
    private EditText etUsername;
    private EditText etPasswd;
    private TextView tvFotgetPasswd;
    private Button btnSubmit;
    private Button btnCancel;

    @Override
    protected IMvpBaseView getIBaseView() {
        return this;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        etUsername = findViewById(R.id.et_username);
        etPasswd = findViewById(R.id.et_passwd);
        tvFotgetPasswd = findViewById(R.id.tv_forget_passwd);

        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "确定", Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvFotgetPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "忘记密码", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public IMvpBasePresent createPresent() {
        iLoginPresent = new LoginPresent();
        return iLoginPresent;
    }

    @Override
    public int getRes() {
        return R.layout.activity_login;
    }

    @Override
    public void checkInvalidate() {

    }

    @Override
    public void onSubmitClick() {

    }

    @Override
    public void onCancelClick() {

    }

    @Override
    public void onRegisterAccountClick() {

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
