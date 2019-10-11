package com.android.qtproject.home;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import android.widget.TextView;
import com.android.qtproject.R;
import com.android.qtproject.base.BaseFragment;

/**
 * qt
 * 2019-09-27
 */
public class HomeFragment extends BaseFragment<IHomeBaseFragmentConstract.IHomeBaseFragmentPresent> implements IHomeBaseFragmentConstract.IHomeBaseFragmentView {
    public static final String KEY = "key";
    private IHomeBaseFragmentConstract.IHomeBaseFragmentPresent iHomeBaseFragmentPresent;
    private TextView textView;

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
    }

    @Override
    public void showHomeView() {

    }
}
