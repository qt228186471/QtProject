package com.android.qtproject.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

/**
 * qt
 * 2019-10-12
 */
public class QtRefreshHeader extends InternalAbstract implements RefreshHeader {


    protected QtRefreshHeader(@NonNull View wrapped) {
        super(wrapped);
    }

    protected QtRefreshHeader(@NonNull View wrappedView, @Nullable RefreshInternal wrappedInternal) {
        super(wrappedView, wrappedInternal);
    }

    protected QtRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
