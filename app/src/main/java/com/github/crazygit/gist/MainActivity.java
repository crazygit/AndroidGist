package com.github.crazygit.gist;

import android.os.Bundle;
import android.view.View;

import com.github.crazygit.gist.activities.AnimationsAndTransitions;
import com.github.crazygit.gist.activities.UserInterfaceAndNavigation;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityMainBinding;

// 布局文件对应的类生成路径为
// app/build/generated/data_binding_base_class_source_out
public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {
    //        private  ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);
        dataBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnUi.setOnClickListener(this);
        dataBinding.btnAnimations.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ui:
                startSubActivity(UserInterfaceAndNavigation.class);
                break;
            case R.id.btn_animations:
                startSubActivity(AnimationsAndTransitions.class);
                break;
            default:
                break;
        }
    }
}
