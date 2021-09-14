package com.whh.material.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.whh.material.R;
import com.whh.material.databinding.ActivityToolbarBinding;

/**
 * AndroidX Toolbar 实现
 * 1. 按照支持库设置中所述，向您的项目添加 v7 appcompat 支持库。
 * 2. 确保 Activity 可以扩展 AppCompatActivity
 * 3. 在应用清单中，将 <application> 元素设置为使用 appcompat 的其中一个 NoActionBar 主题背景。使用其中一个主题背景可以防止应用使用原生 ActionBar 类提供应用栏
 * 4. 向 Activity 的布局添加一个 Toolbar。例如，以下布局代码会添加一个 Toolbar，并赋予其浮动在 Activity 之上的外观
 * author:wuhuihui 2021.09.08
 */
public class ToolbarActivity extends AppCompatActivity {

    private ActivityToolbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(ToolbarActivity.this, R.string.settings, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_favorite:
                Toast.makeText(ToolbarActivity.this, R.string.favorite, Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}