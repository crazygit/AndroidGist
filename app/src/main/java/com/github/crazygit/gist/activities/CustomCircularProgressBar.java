package com.github.crazygit.gist.activities;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityCustomCircularProgressBarBinding;

public class CustomCircularProgressBar extends BaseActivity<ActivityCustomCircularProgressBarBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = ActivityCustomCircularProgressBarBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnIncreaseProgress.setOnClickListener(this);
        dataBinding.btnDecreaseProgress.setOnClickListener(this);
        dataBinding.btnStartAnimator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_increase_progress:
                dataBinding.progressCircular.setProcess(dataBinding.progressCircular.getProcess() + 10);
                break;
            case R.id.btn_decrease_progress:
                dataBinding.progressCircular.setProcess(dataBinding.progressCircular.getProcess() - 10);
                break;
            case R.id.btn_start_animator:
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(dataBinding.progressCircular, "process", 0f, 100f);
                objectAnimator.setStartDelay(500);
                objectAnimator.setDuration(2000);
                objectAnimator.setInterpolator(new LinearInterpolator());
                objectAnimator.addUpdateListener(animation -> dataBinding.tvProcess.setText((int) Float.parseFloat(animation.getAnimatedValue().toString()) + "%"));
                objectAnimator.start();
            default:
                break;
        }
    }
}
