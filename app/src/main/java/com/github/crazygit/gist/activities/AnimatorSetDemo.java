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
        dataBinding.btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                int duration = 2000;
                ObjectAnimator textViewAAnimation = ObjectAnimator.ofFloat(dataBinding.tvA, "translationY", 300f);
                textViewAAnimation.setDuration(duration);

                ObjectAnimator textViewBAnimation = ObjectAnimator.ofFloat(dataBinding.tvB, "alpha", 1f, 0f);
                textViewBAnimation.setDuration(duration);

                ObjectAnimator textViewCAnimation = ObjectAnimator.ofFloat(dataBinding.tvC, "rotation", 360);
                textViewCAnimation.setDuration(duration);

                // 从效果来看， AnimatorSet运行之后会一直保持最后的状态？
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(textViewAAnimation).with(textViewBAnimation);
                animatorSet.play(textViewAAnimation).with(textViewCAnimation);
                animatorSet.start();
                break;
            default:
                break;
        }
    }
}
