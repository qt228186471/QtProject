package com.android.qtproject.manager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * qt
 * 2019-10-08
 */
public class HomeFragmentManager {
    private FragmentManager fragmentManager;
    private HashMap<Integer, WeakReference<Fragment>> fragmentsMap;
    private HomeFragmentManager(){

    }

}
