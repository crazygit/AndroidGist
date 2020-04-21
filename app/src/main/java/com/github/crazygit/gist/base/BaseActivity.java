package com.github.crazygit.gist.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;


// ViewBinding需要在app/build.gradle中添加如下依赖

// implementation 'androidx.databinding:viewbinding:3.6.3'

public abstract class BaseActivity<B extends ViewBinding> extends AppCompatActivity {

    protected B dataBinding;

    //The given layout resource must not be a merge layout.
    // 返回的类型是ViewDataBinding的子类，没法和接口ViewBinding兼容
    // ViewBinding 和DataBinding不应该用于同一个layout布局文件
    // https://stackoverflow.com/a/58558681/1957625
//    protected void bindView(@LayoutRes int layoutId) {
//        dataBinding = DataBindingUtil.setContentView(this, layoutId);
//    }
    public void startSubActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
