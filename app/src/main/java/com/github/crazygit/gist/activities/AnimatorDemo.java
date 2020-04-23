package com.github.crazygit.gist.activities;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.crazygit.gist.R;
import com.github.crazygit.gist.base.BaseActivity;
import com.github.crazygit.gist.databinding.ActivityAnimatorDemoBinding;

//
// ValueAnimator与ObjectAnimator的基本使用
// https://medium.com/@shubham08gupta/a-beginners-guide-to-implement-android-animations-part-1-2-part-series-b5fce1fc85
// https://medium.com/@shubham08gupta/animations-v2-2ada6ef3e5c8
// 进阶使用，参考
// https://hencoder.com/ui-1-6/
// https://hencoder.com/ui-1-7/
//
public class AnimatorDemo extends BaseActivity<ActivityAnimatorDemoBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = ActivityAnimatorDemoBinding.inflate(getLayoutInflater());
        setContentView(dataBinding.getRoot());
        dataBinding.btnMoveByValueAnimatorCode.setOnClickListener(this);
        dataBinding.btnMoveByValueAnimatorXml.setOnClickListener(this);
        dataBinding.btnMoveByObjectAnimatorCode.setOnClickListener(this);
        dataBinding.btnMoveByObjectAnimatorXml.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_move_by_value_animator_code:
                ValueAnimator codeValueAnimator = ValueAnimator.ofFloat(0f, 500f);
//                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                codeValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // increase the speed first and then decrease
                codeValueAnimator.setDuration(2000);
                codeValueAnimator.addUpdateListener(animation -> {
                    float progress = (float) animation.getAnimatedValue();
                    // no need to use invalidate() as it is already present in the text view.
                    dataBinding.tvTextView.setTranslationY(progress);
                });
                codeValueAnimator.start();
                break;
            case R.id.btn_move_by_value_animator_xml:
                ValueAnimator xmlValueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(
                        this, R.animator.value_animator_ex);

                xmlValueAnimator.addUpdateListener(animation -> {
                    float progress = (float) animation.getAnimatedValue();
                    dataBinding.tvTextView.setTranslationY(progress);
                });
                xmlValueAnimator.start();
                break;
            case R.id.btn_move_by_object_animator_code:
                // ObjectAnimator是ValueAnimator的子类
                // 使用上更简便，不需要自己实现addUpdateListener
                ObjectAnimator codeObjectAnimator = ObjectAnimator.ofFloat(dataBinding.tvTextView, "translationY", 0f, 500f);
                codeObjectAnimator.setDuration(2000);
                codeObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                codeObjectAnimator.start();
                break;
            case R.id.btn_move_by_object_animator_xml:
                ObjectAnimator xmlObjectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.object_animator_ex);
                xmlObjectAnimator.setTarget(dataBinding.tvTextView);
                xmlObjectAnimator.start();
            default:
                break;
        }
    }
}
