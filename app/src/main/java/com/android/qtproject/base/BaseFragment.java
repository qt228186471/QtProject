package com.android.qtproject.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment<T extends IMvpBasePresent> extends Fragment implements IMvpBaseView {
    private T iMvpBasePresent;
    protected View root;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(getRes(), container, false);
        return root;
    }

    protected abstract void loadData();

    protected abstract void initView();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMvpBasePresent = createPresent();
        iMvpBasePresent.attach(this);
    }

    protected abstract T createPresent();

    @Override
    public void onDetach() {
        super.onDetach();
        iMvpBasePresent.detach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        loadData();
    }

    public abstract int getRes();

}
