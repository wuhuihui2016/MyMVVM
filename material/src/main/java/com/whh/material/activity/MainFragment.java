package com.whh.material.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

/**
 * material UI实现集
 * author:wuhuihui 2021.09.08
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
                "ToolBar",//0
                "FloatActionButton与Snackbar",//1
                "MaterialButtonActivity",//2
                "DrawLayout与NaviagtionView",//3
                "TextInputActivity",//4
                "CardView",//5
                "TabLayout",//6
                "CoordinatorLayout",//7
                "Translucent",//8
                "BottomSheets",//9
                "ViewPager2Activity",//10
                "BottomAppBarActivity",//11
                "ChipsActivity",//12
                "NestScrollActivity",//13
                "NestedTraditionActivity",//14
                "NestedScrolling1Activity",//15
                "NestedScrollViewActivity",//16
                "CoordinatorMainActivity",//17
        };
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String item = arrayAdapter.getItem(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
        Intent gotoAct;
        switch (position) {
            case 0:// "ToolBar",//0
                gotoAct = new Intent(getActivity(), ToolbarActivity.class);
                startActivity(gotoAct);
                break;
            case 1://"FloatActionButton",//1
                gotoAct = new Intent(getActivity(), FloatActionButtonActivity.class);
                startActivity(gotoAct);
                break;
            case 2:// "MaterialButtonActivity",//2
                gotoAct = new Intent(getActivity(), MaterialButtonActivity.class);
                startActivity(gotoAct);
                break;
            case 3:// "NaviagtionView",//3
                gotoAct = new Intent(getActivity(), DrawerLayoutNavActivity.class);
                startActivity(gotoAct);
                break;
            case 4:// "DrawLayout",//4
                gotoAct = new Intent(getActivity(), TextInputActivity.class);
                startActivity(gotoAct);
                break;
            case 5:// "CardView",//4
                gotoAct = new Intent(getActivity(), CardViewActivity.class);
                startActivity(gotoAct);
                break;
            case 6://"Tab",//6
                gotoAct = new Intent(getActivity(), TabActivity.class);
                startActivity(gotoAct);
                break;
            case 7://"CoordinatorLayout",//6
                gotoAct = new Intent(getActivity(), CoordinatorActivity.class);
                startActivity(gotoAct);
                break;
            case 8://"Translucent",//8
                gotoAct = new Intent(getActivity(), QQMusicActivity.class);
                startActivity(gotoAct);
                break;
            case 9://"BottomSheets",//9
                gotoAct = new Intent(getActivity(), BottomSheetsActivity.class);
                startActivity(gotoAct);
                break;
            case 10://"ViewPager2Activity",//10
                gotoAct = new Intent(getActivity(), ViewPager2Activity.class);
                startActivity(gotoAct);
                break;
            case 11://"BottomAppBarActivity",//11
                gotoAct = new Intent(getActivity(), BottomAppBarActivity.class);
                startActivity(gotoAct);
                break;
            case 12://"ChipsActivity",//12
                gotoAct = new Intent(getActivity(), ChipsActivity.class);
                startActivity(gotoAct);
                break;
            case 13://"NestScrollActivity",//13
                gotoAct = new Intent(getActivity(), NestScrollActivity.class);
                startActivity(gotoAct);
                break;

            case 14://"NestedTraditionActivity",//14
                gotoAct = new Intent(getActivity(), NestedTraditionActivity.class);
                startActivity(gotoAct);
                break;
            case 15://"NestedScrolling1Activity",//15
                gotoAct = new Intent(getActivity(), NestedScrolling1Activity.class);
                startActivity(gotoAct);
                break;
            case 16://"NestedScrollViewActivity",//16
                gotoAct = new Intent(getActivity(), NestedScrollViewActivity.class);
                startActivity(gotoAct);
                break;
            case 17://"CoordinatorMainActivity",//17
                gotoAct = new Intent(getActivity(), CoordinatorMainActivity.class);
                startActivity(gotoAct);
                break;
            default:
                break;
        }
    }


}
