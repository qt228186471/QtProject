package com.example.util;

import android.widget.EditText;

/**
 * qt
 * 2019-10-18
 */
public class AMapUtils {
    public static String checkEditText(EditText editText) {
        if (editText != null && editText.getText() != null
                && !(editText.getText().toString().trim().equals(""))) {
            return editText.getText().toString().trim();
        } else {
            return "";
        }
    }
}
