package com.android.qtproject.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.qtproject.R;
import com.android.qtproject.detail.NewPageActivity;
import com.example.base.BaseFragment;
import com.example.location.LocationActivityDemo;

/**
 * qt
 * 2019-09-27
 */
public class HomeFragment extends BaseFragment<IHomeBaseFragmentConstract.IHomeBaseFragmentPresent> implements IHomeBaseFragmentConstract.IHomeBaseFragmentView {
    public static final String KEY = "key";
    private IHomeBaseFragmentConstract.IHomeBaseFragmentPresent iHomeBaseFragmentPresent;
    private TextView textView;
    private Button button;


    public static HomeFragment newInstance(FragmentManager fragmentManager, Context context, String value) {
        FragmentFactory fragmentFactory = fragmentManager.getFragmentFactory();
        HomeFragment fragment = (HomeFragment) fragmentFactory.instantiate(context.getClassLoader(), HomeFragment.class.getName());

        Bundle bundle = new Bundle();
        bundle.putString(KEY, value);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected IHomeBaseFragmentConstract.IHomeBaseFragmentPresent createPresent() {
        iHomeBaseFragmentPresent = new HomeFragmentPresent();
        return iHomeBaseFragmentPresent;
    }

    @Override
    public int getRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {
        iHomeBaseFragmentPresent.loadHomeData();
    }

    @Override
    protected void initView() {
        textView = root.findViewById(R.id.home_tv);
        textView.setText(getArguments().getString(KEY));
        button = root.findViewById(R.id.home_btn);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LocationActivityDemo.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewPageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showHomeView() {

    }
}
