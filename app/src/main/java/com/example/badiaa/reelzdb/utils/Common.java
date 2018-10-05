package com.example.badiaa.reelzdb.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class Common {

    // Collpase the keyboard
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
