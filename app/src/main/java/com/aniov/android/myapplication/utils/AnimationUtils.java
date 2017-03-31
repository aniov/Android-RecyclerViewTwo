package com.aniov.android.myapplication.utils;

import android.animation.AnimatorSet;

/**
 * Created by Marius on 3/31/2017.
 */

public class AnimationUtils {

    public static final int ANIMATION_DURATION_400 = 400;

    private AnimationUtils(){}

    public static void stopAnimatorSet(AnimatorSet animatorSet) {
        if (animatorSet != null ){
            animatorSet.cancel();
            animatorSet.removeAllListeners();
        }
    }
}
