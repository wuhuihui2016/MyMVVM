package com.whh.mymvvm.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.whh.mymvvm.R;
import com.whh.mymvvm.adapter.StudentAdapter;
import com.whh.mymvvm.base.BaseActivity;
import com.whh.mymvvm.databinding.ActivityListRoomBinding;
import com.whh.mymvvm.room.Student;
import com.whh.mymvvm.room.StudentViewModel;
import com.whh.mymvvm.widget.RecycleViewDivider;

import java.util.List;
import java.util.Random;

/**
 * 数据库操作
 * 所有数据库的操作都需要放在异步线程 最好与RxJava合用
 * author:wuhuihui 2021.06.17
 */
public class RoomActivity extends BaseActivity {

    private ActivityListRoomBinding activityListRoomBinding;
    private XRecyclerView recyclerView;

    private Random random = new Random();

    private List<Student> studentList;
    private StudentAdapter studentAdapter;
    private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListRoomBinding = (ActivityListRoomBinding) baseBinding;
        initRecyclerView();

        initData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_list_room;
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {

        recyclerView = activityListRoomBinding.recyclerView;

        //设置列表滑动方向和间隔线
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 3, getResources().getColor(R.color.divide_color)));

        //设置上拉刷新下拉加载更多样式
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotate); //设置下拉刷新的样式
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate); //设置上拉加载更多的样式
        recyclerView.setArrowImageView(R.drawable.pull_down_arrow);

        //设置适配器
        studentAdapter = new StudentAdapter(this);
        activityListRoomBinding.setAdapterStudent(studentAdapter);
    }

    /**
     * 数据刷新
     */
    private void initData() {
        //AndroidViewModel与ViewModel获取方法不同
        //使用同ViewModel方法报错：Caused by: java.lang.InstantiationException: class StudentViewModel has no zero argument constructor
        studentViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()).create(StudentViewModel.class);
        studentViewModel.getAllStucdents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                Log.e("whh0617", "onChanged... size ==>" + students.size());
                studentList = students;
                studentAdapter.updateAll(students);
            }
        });
    }

    /**
     * 新增
     * @param view
     */
    public void add(View view) {
        int age = random.nextInt(20) + 1;
        Student student = new Student();
        student.setName("whh" + age);
        student.setAge(age);
        studentViewModel.insert(student);
    }

    /**
     * 删除
     * @param view
     */
    public void del(View view) {
        if (studentList.size() > 0) {
            studentViewModel.delete(studentList.get(0));
        }
    }
    
    /**
     * 修改信息
     * @param view
     */
    public void update(View view) {
        if (studentList.size() > 0) {
            Student student = new Student();
            student.setName("whh");
            student.setAge(18);
            studentViewModel.update(student);
        }
    }

    /**
     * 查找
     * @param view
     */
    public void search(View view) {
        Log.e("whh0617", "search...");
//        studentViewModel.getStudentByAge(10);
        studentViewModel.getStudent(10); //TODO 查询数据失败，为null???
    }
}
