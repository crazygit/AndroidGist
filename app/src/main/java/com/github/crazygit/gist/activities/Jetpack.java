package com.github.crazygit.gist.activities;

import android.os.Bundle;
import android.view.View;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityJetpackBinding;

public class Jetpack extends BaseActivity<ActivityJetpackBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = ActivityJetpackBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnLivedata.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_livedata:
                startSubActivity(JetpackLiveDataDemo.class);
                break;
            default:
                break;
        }
    }
}
