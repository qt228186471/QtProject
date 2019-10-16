package com.android.common;

import java.util.Collection;

/**
 * qt
 * 2019-10-16
 */
public class CollectionsUtils {
    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        }
        return false;
    }
}
