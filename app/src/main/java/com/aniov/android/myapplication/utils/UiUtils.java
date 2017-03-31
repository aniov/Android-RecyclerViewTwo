package com.aniov.android.myapplication.utils;

import android.graphics.Point;
import android.view.Display;

import com.aniov.android.myapplication.activity.MainActivity;

/**
 * Created by Marius on 3/31/2017.
 */

public class UiUtils {

    private UiUtils(){}

    public static int getScreenHeight(MainActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.y;
    }

    public static int getScreenWidth(MainActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }
}
