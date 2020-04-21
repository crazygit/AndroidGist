package com.github.crazygit.gist.activities;

import android.os.Bundle;
import android.view.View;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityCustomCircularProgressBarBinding;
import com.github.crazygit.gist.widget.CircularProgressBar;

public class CustomCircularProgressBar extends BaseActivity<ActivityCustomCircularProgressBarBinding> implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_circular_progress_bar);
        dataBinding = ActivityCustomCircularProgressBarBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnIncreaseProgress.setOnClickListener(this);
        dataBinding.btnDecreaseProgress.setOnClickListener(this);

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
            default:
                break;
        }
    }
}
