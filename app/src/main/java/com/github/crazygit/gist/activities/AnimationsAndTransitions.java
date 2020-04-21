package com.github.crazygit.gist.activities;

import android.os.Bundle;
import android.view.View;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityAnimationsAndTransitionsBinding;


public class AnimationsAndTransitions extends BaseActivity<ActivityAnimationsAndTransitionsBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = ActivityAnimationsAndTransitionsBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnAnimator.setOnClickListener(this);
        dataBinding.btnAnimatorSet.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_animator:
                startSubActivity(AnimatorDemo.class);
                break;
            case R.id.btn_animator_set:
                startSubActivity(AnimatorSetDemo.class);
            default:
                break;

        }
    }
}
