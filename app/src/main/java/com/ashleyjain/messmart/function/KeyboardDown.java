package com.ashleyjain.messmart.function;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.ashleyjain.messmart.StartActivity;

/**
 * Created by ANKIT on 03-Jun-16.
 */
public class KeyboardDown {
    public static void keyboardDown(){
        InputMethodManager inputManager = (InputMethodManager)StartActivity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(StartActivity.get().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
