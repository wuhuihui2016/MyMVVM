package com.whh.mymvvm.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * 数据操作工具，定义数据名和版本，引用数据操作接口
 * author:wuhuihui 2021.06.17
 */
@Database(entities = {Student.class}, version = 1,exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {

    public abstract StudentDao  studentDao();
    private static final String DB_NAME = "StudentDatabase.db";
    private static volatile StudentDatabase instance;

    public static synchronized StudentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    StudentDatabase.class, DB_NAME).build();
        }
        return instance;
    }

}

