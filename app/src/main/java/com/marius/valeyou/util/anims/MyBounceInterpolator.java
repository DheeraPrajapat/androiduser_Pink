package com.marius.valeyou.util.anims;

public class MyBounceInterpolator implements android.view.animation.Interpolator {
    private double mAmplitude =0.2;
    private double mFrequency =20;

    public MyBounceInterpolator(double mAmplitude, double mFrequency) {
        this.mAmplitude = mAmplitude;
        this.mFrequency = mFrequency;
    }

    public MyBounceInterpolator() {
    }

    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}