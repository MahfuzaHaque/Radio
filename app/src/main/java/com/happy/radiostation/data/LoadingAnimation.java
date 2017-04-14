package com.happy.radiostation.data;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class LoadingAnimation {
    private static TranslateAnimation animation;
    private ImageView loading;

    public LoadingAnimation(ImageView loading) {
        this.loading = loading;
        animation = new TranslateAnimation(-50, 50, 0, 0);
        animation.setDuration(800);
        animation.setFillAfter(true);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
    }

    public static boolean hasEnded() {
        boolean status = true;
        try {
            status = animation.hasEnded();
        } catch (Exception e) {
            e.getMessage();
        }
        if (status) return true;
        else return false;
    }

    public void startAnimation() {
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation(animation);
    }

    public void clearAnimation() {
        loading.clearAnimation();
        loading.setVisibility(View.INVISIBLE);
    }
}

