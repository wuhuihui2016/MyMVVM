package com.whh.myrxjava.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.whh.myrxjava.R;
import com.whh.myrxjava.databinding.ActivityRxjavaInfoBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * RxBinding
 * 它是对 Android View 事件的扩展, 它使得开发者可以对 View 事件使用 RxJava 的各种操作.
 * 提供了与 Rxjava 一致的回调, 使得代码简洁明了.
 * 几乎支持所有的常用控件及事件, 还对 Kotlin 支持.
 * author:wuhuihui 2021.07.14
 */
public class RxBindingActivity extends AppCompatActivity {

    private Activity activity;
    private ActivityRxjavaInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_rxjava_info);

        Observable<CharSequence> value1 = RxTextView.textChanges(binding.edit1).skip(1);
        Observable<CharSequence> value2 = RxTextView.textChanges(binding.edit2).skip(1);
        Observable<CharSequence> value3 = RxTextView.textChanges(binding.edit3).skip(1);

        //RxView设置点击事件
        RxView.clicks(binding.confirmButton)
                .throttleFirst(2, TimeUnit.SECONDS) //防止2秒内重复点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(activity, "已提交数据!", Toast.LENGTH_LONG).show();
                    }
                });

        //长按点击事件
        RxView.longClicks(binding.confirmButton)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(activity, "长按提交数据!", Toast.LENGTH_LONG).show();
                    }
                });


        Observable.combineLatest(value1, value2, value3, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                boolean valid1 = !TextUtils.isEmpty(binding.edit1.getText().toString());
                boolean valid2 = !TextUtils.isEmpty(binding.edit2.getText().toString());
                boolean valid3 = !TextUtils.isEmpty(binding.edit3.getText().toString());
                return valid1 && valid2 && valid3;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                binding.confirmButton.setEnabled(true);

            }
        });
    }
}
