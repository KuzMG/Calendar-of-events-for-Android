package com.example.education.animation;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.education.R;

import java.util.Random;

public class SnowAnimationView extends View {
    private Random random = new Random();
    private static final int BASE_SPEED_DP_PER_S = 50;
    private static final int COUNT = 40;
    private static final float SCALE_MIN_PART = 0.05f;
    private static final float SCALE_RANDOM_PART = 0.55f;
    private static final float ALPHA_SCALE_PART = 0.5f;
    private static final float ALPHA_RANDOM_PART = 0.4f;
    private TimeAnimator mTimeAnimator;
    private Drawable mDrawable;
    private float mBaseSpeed;
    private float mBaseSize;
    private long mCurrentPlayTime;
    private static class Snow {
        private float x;
        private float y;
        private float scale;
        private float alpha;
        private float speed;
    }
    private final Snow[] snows = new Snow[COUNT];
    public SnowAnimationView(Context context) {
        super(context);
        init();
    }
    public SnowAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public SnowAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public SnowAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mDrawable = ContextCompat.getDrawable(getContext(), R.drawable.motozov);
        mBaseSize = Math.max(mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight()) / 2f;
        mBaseSpeed = BASE_SPEED_DP_PER_S * getResources().getDisplayMetrics().density;
    }
    private void initializeSnow(Snow snow, int viewWidth, int viewHeight){
        snow.scale = SCALE_MIN_PART + SCALE_RANDOM_PART * random.nextFloat();
        snow.x = viewWidth * random.nextFloat();
        snow.y = viewHeight/(3*(random.nextFloat()+0.1f));
        snow.alpha = ALPHA_SCALE_PART * snow.scale + ALPHA_RANDOM_PART * random.nextFloat();
        snow.speed = mBaseSpeed * snow.alpha * (snow.scale+0.2f);
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        for (int i = 0; i < snows.length; i++) {
            final Snow snow = new Snow();
            initializeSnow(snow, width, height);
            snows[i] = snow;
        }
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTimeAnimator = new TimeAnimator();
        mTimeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if (!isLaidOut()) {
                    return;
                }
                updateSnow(deltaTime);
                invalidate();
            }
        });
        mTimeAnimator.start();
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTimeAnimator.cancel();
        mTimeAnimator.setTimeListener(null);
        mTimeAnimator.removeAllListeners();
        mTimeAnimator = null;
    }
    private void updateSnow(float deltaMs) {
        final float deltaSeconds = deltaMs / 1000f;
        final int viewWidth = getWidth();
        final int viewHeight = getHeight();
        for (final Snow snow : snows) {
            snow.y += snow.speed * deltaSeconds;
            final float size = snow.scale * mBaseSize;
            if (snow.y  > viewHeight + size) {
                initializeSnow(snow, viewWidth, viewHeight);
            }

        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        final int viewHeight = getHeight();
        for (final Snow snow : snows) {
            final float starSize = snow.scale * mBaseSize;
            if (snow.y + starSize < 0 || snow.y - starSize > viewHeight) {
                continue;
            }
            final int save = canvas.save();
            canvas.translate(snow.x, snow.y);
            final float progress = (snow.y + starSize) / viewHeight;
            canvas.rotate(360 * progress);
            final int size = Math.round(starSize);
            mDrawable.setBounds(-size, -size, size, size);
            mDrawable.setAlpha(Math.round(255 * snow.alpha));
            mDrawable.draw(canvas);
            canvas.restoreToCount(save);
        }
    }
    public void resume() {
        if (mTimeAnimator != null && mTimeAnimator.isPaused()) {
            mTimeAnimator.start();
            mTimeAnimator.setCurrentPlayTime(mCurrentPlayTime);
        }
    }

}
