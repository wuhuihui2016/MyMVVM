package com.whh.mymvvm.room;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.whh.mymvvm.utils.RxUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * AndroidViewModel中含有Application
 * author:wuhuihui 2021.06.17
 */
public class StudentViewModel extends AndroidViewModel {

    private StudentDao studentDao;
    private LiveData<List<Student>> students;

    public StudentViewModel(@NonNull @NotNull Application application) {
        super(application);
        studentDao = StudentDatabase.getInstance(application).studentDao();
        students = studentDao.getAll();
    }

    /**
     * 插入数据
     * @param student
     */
    public void insert(Student student) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) {
                studentDao.insert(student);
            }
        }).compose(RxUtils.<Boolean>rxSchedulerHelper()).subscribe();
    }

    /**
     * 更新数据
     * @param student
     */
    public void update(Student student) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) {
                studentDao.update(student);
            }
        }).compose(RxUtils.<Boolean>rxSchedulerHelper()).subscribe();
    }

    /**
     * 删除数据
     * @param student
     */
    public void delete(Student student) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) {
                studentDao.delete(student);
            }
        }).compose(RxUtils.<Boolean>rxSchedulerHelper()).subscribe();
    }

    /**
     * 通过年龄查找
     * @param age
     */
    public void getStudentByAge(int age) {
        Log.e("whh0617", "getStudentByAge...");
        Observable.create(new ObservableOnSubscribe<List<Student>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Student>> e) {
                students = studentDao.getStudentByAge(age);
                if (students.getValue() == null) //TODO 查询数据失败，为null???
                    Log.e("whh0617", "getStudentByAge...students is null");
                else Log.e("whh0617", "getStudentByAge..." + students.getValue().size());
            }
        }).compose(RxUtils.<List<Student>>rxSchedulerHelper()).subscribe();
    }

    /**
     * 通过id查找
     * @param id
     */
    public void getStudent(int id) {
//        Observable.create(new ObservableOnSubscribe<Student>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Student> e) {
                students = studentDao.getStudent(id);
                Log.e("whh0617", "getStudent by ID==>" + students.getValue()); //TODO 查询数据失败，为null???
//            }
//        }).compose(RxUtils.<Student>rxSchedulerHelper()).subscribe();
    }


    /**
     * 获取数据
     * @return
     */
    public LiveData<List<Student>> getAllStucdents() {
        return students;
    }


}
