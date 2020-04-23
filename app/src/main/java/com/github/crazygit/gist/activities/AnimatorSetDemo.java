package com.github.crazygit.gist.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityAnimatorSetDemoBinding;

public class AnimatorSetDemo extends BaseActivity<ActivityAnimatorSetDemoBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = ActivityAnimatorSetDemoBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnStartAbc.setOnClickListener(this);
        dataBinding.btnStartD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_abc:
                // AnimatorSet作用于多个控件
                int duration = 2000;
                ObjectAnimator textViewAAnimation = ObjectAnimator.ofFloat(dataBinding.tvA, "translationY", 0, 300f);
                textViewAAnimation.setDuration(duration);

                ObjectAnimator textViewBAnimation = ObjectAnimator.ofFloat(dataBinding.tvB, "alpha", 1f, 0f);
                textViewBAnimation.setDuration(duration);

                ObjectAnimator textViewCAnimation = ObjectAnimator.ofFloat(dataBinding.tvC, "rotation", 0, 360);
                textViewCAnimation.setDuration(duration);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(textViewAAnimation).with(textViewBAnimation);
                animatorSet.play(textViewAAnimation).with(textViewCAnimation);
                animatorSet.start();
                break;
            case R.id.btn_start_d:
                // AnimatorSet作用于一个控件
                ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(dataBinding.tvD, "rotation", 0, 360f);
                rotationAnimator.setDuration(2000);
                ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(dataBinding.tvD, "translationY", 0, 500f);
                translateAnimator.setDuration(2000);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(rotationAnimator, translateAnimator);
                set.start();
            default:
                break;
        }
    }
}
