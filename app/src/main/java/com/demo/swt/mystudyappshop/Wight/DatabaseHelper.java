package com.demo.swt.mystudyappshop.Wight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/10
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    //在SQLiteOepnHelper的子类当中，必须有该构造函数
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    //该函数是在第一次创建数据库的时候执行,实际上是在第一次得到SQLiteDatabse对象的时候，才会调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create a Database");
        //execSQL函数用于执行SQL语句
        db.execSQL("create table user(id int,name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("update a Database");
    }
}
