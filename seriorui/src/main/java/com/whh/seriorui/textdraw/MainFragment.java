package com.whh.seriorui.textdraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.whh.seriorui.textdraw.activity.OverDrawActivity;
import com.whh.seriorui.textdraw.activity.SimpleActivity;
import com.whh.seriorui.textdraw.activity.TextMeasureActivity;
import com.whh.seriorui.textdraw.activity.ViewPagerActivity;

/**
 * 文字绘制示例
 * 1.Simple文字渐变
 * 2.文字测量演示
 * 3.ViewPager+文字变色
 * 4.过渡绘制演示
 * author:wuhuihui 2021.09.07
 */
public class MainFragment extends ListFragment {

    public static Fragment newIntance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    ArrayAdapter<String> arrayAdapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] array = new String[]{
                "Simple文字渐变",//0
                "文字测量演示",//1
                "ViewPager+文字变色",//2
                "过渡绘制演示",//3
        };
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        String item = arrayAdapter.getItem(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
        Intent gotoAct;
        switch (position) {
            case 0://Simple文字渐变
                gotoAct = new Intent(getActivity(), SimpleActivity.class);
                startActivity(gotoAct);
                break;
            case 1://文字测量演示
                gotoAct = new Intent(getActivity(), TextMeasureActivity.class);
                startActivity(gotoAct);
                break;
            case 2://ViewPager+文字变色
                gotoAct = new Intent(getActivity(), ViewPagerActivity.class);
                startActivity(gotoAct);
                break;
            case 3://过渡绘制演示
                gotoAct = new Intent(getActivity(), OverDrawActivity.class);
                startActivity(gotoAct);
                break;
            default:
                break;
        }
    }


}
