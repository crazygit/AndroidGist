package com.github.crazygit.gist.activities;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityJetpackLiveDataDemoBinding;
import com.github.crazygit.gist.viewmodel.NameViewModel;

import java.util.Random;

//  需要在app/build.gradle中添加如下依赖
//           def lifecycle_version = "2.2.0"
//           // ViewModel
//           implementation "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"
//           // LiveData
//           implementation "androidx.lifecycle:lifecycle-livedata:$lifecycle_version"
public class JetpackLiveDataDemo extends BaseActivity<ActivityJetpackLiveDataDemoBinding> implements View.OnClickListener {

    private NameViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = ActivityJetpackLiveDataDemoBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnSetName.setOnClickListener(this);

        // 获取 ViewModel
        model = new ViewModelProvider(this).get(NameViewModel.class);
        model.getCurrentName().observe(this, newName -> {
            // 更新UI界面
            dataBinding.tvShowName.setText(newName);
        });
    }

    private String getRandomName(int length) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random generator = new Random();
        StringBuilder name = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            int index = generator.nextInt(letters.length() - 1);
            name.append(letters.charAt(index));
        }
        return name.toString();
    }

    private void setName() {
        // 更新 LiveData 对象,对应的UI控件也会更新
        String newName = getRandomName(10);
        model.getCurrentName().setValue(newName);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set_name:
                setName();
                break;
        }
    }
}
