package com.github.crazygit.gist.activities;

import android.os.Bundle;
import android.view.View;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityUserInterfaceNavigationBinding;

public class UserInterfaceAndNavigation extends BaseActivity<ActivityUserInterfaceNavigationBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface_navigation);
        dataBinding = ActivityUserInterfaceNavigationBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnCustomView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_custom_view:
                startSubActivity(CustomCircularProgressBar.class);

        }
    }
}
