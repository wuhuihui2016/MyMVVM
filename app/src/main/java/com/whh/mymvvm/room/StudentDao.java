package com.whh.mymvvm.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * SQL语句封装和接口方法定义：增删改查
 * author:wuhuihui 2021.06.17
 */
@Dao
public interface StudentDao {

    @Query("SELECT * FROM Student")
    LiveData<List<Student>> getAll();

    //以下方法如果没有具体实现，需注释了，否则程序运行失败：Not sure how to convert a Cursor to this method's return type
    @Query("SELECT * FROM Student WHERE age = :age")
    LiveData<List<Student>> getStudentByAge(int age);

    @Query("SELECT * FROM Student WHERE sId > :id")
    LiveData<List<Student>> getStudent(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Student... students);

    @Update
    void update(Student... students);

    @Delete
    void delete(Student student);

}

