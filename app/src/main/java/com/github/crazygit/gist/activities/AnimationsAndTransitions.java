package com.github.crazygit.gist.activities;

import android.os.Bundle;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityAnimationsAndTransitionsBinding;


public class AnimationsAndTransitions extends BaseActivity<ActivityAnimationsAndTransitionsBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations_and_transitions);
        dataBinding = ActivityAnimationsAndTransitionsBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
    }
}
