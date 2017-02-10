package com.demo.swt.mystudyappshop.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.DatabaseHelper;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/10
 */

public class SqliteDataBaseActivity extends FragmentActivity {
    private Button createButton;
    private Button insertButton;
    private Button updateButton;
    private Button updateRecordButton;
    private Button queryButton;
    private Button deleteButton;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private TextView dataview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite);
        createButton = (Button) findViewById(R.id.createDatabase);
        updateButton = (Button) findViewById(R.id.updateDatabase);
        insertButton = (Button) findViewById(R.id.insert);
        updateRecordButton = (Button) findViewById(R.id.update);
        queryButton = (Button) findViewById(R.id.query);
        deleteButton = (Button) findViewById(R.id.delete);
        dataview = (TextView) findViewById(R.id.data);
        createButton.setOnClickListener(new CreateListener());
        updateButton.setOnClickListener(new UpdateVersionListener());
        insertButton.setOnClickListener(new InsertListener());
        updateRecordButton.setOnClickListener(new UpdateRecordListener());
        queryButton.setOnClickListener(new QueryListener());
        deleteButton.setOnClickListener(new DeleteListener());
    }

    class CreateListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //创建一个DatabaseHelper对象
            if (dbHelper == null) {
                dbHelper = new DatabaseHelper(SqliteDataBaseActivity.this, "test_mars_db", 3);
                //只有调用了DatabaseHelper对象的getReadableDatabase()方法，或者是getWritableDatabase()方法之后，才会创建，或打开一个数据库
                db = dbHelper.getReadableDatabase();
                db.getVersion();
            } else {
                dataview.setText("你已经创建过数据库");
            }
        }
    }

    class UpdateVersionListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (dbHelper == null) {
                dataview.setText("请先创建数据库，在更新");
            } else {
                dbHelper = new DatabaseHelper(SqliteDataBaseActivity.this, "test_mars_db", 3);
                db = dbHelper.getReadableDatabase();
                dataview.setText("更新成功,您的数据库版本是" + db.getVersion());
            }
        }

    }

    class InsertListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (dbHelper == null) {
                dataview.setText("请先更新数据库");
            } else {
                //生成ContentValues对象
                ContentValues values = new ContentValues();
                //想该对象当中插入键值对，其中键是列名，值是希望插入到这一列的值，值必须和数据库当中的数据类型一致
                values.put("id", 1);
                values.put("name", "zhangsan");
                //调用insert方法，就可以将数据插入到数据库当中
                db.insert("user", null, values);
            }

        }
    }

    //更新操作就相当于执行SQL语句当中的update语句
    //UPDATE table_name SET XXCOL=XXX WHERE XXCOL=XX...
    class UpdateRecordListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            if (dbHelper == null) {
                dataview.setText("请先更新数据库");
            } else {
                //得到一个可写的SQLiteDatabase对象
                ContentValues values = new ContentValues();
                values.put("name", "zhangsanfeng");
                //第一个参数是要更新的表名
                //第二个参数是一个ContentValeus对象
                //第三个参数是where子句
                db.update("user", values, "id=?", new String[]{"1"});
            }

        }
    }

    class QueryListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (dbHelper == null) {
                dataview.setText("请先更新数据库");
            } else {
                Cursor cursor = db.query("user", new String[]{"id", "name"}, "id=?", new String[]{"1"}, null, null, null);
                if (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    dataview.setText(name);
                } else {
                    dataview.setText("暂无数据");
                }
            }

        }
    }

    //删除数据的方法
    class DeleteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (dbHelper == null) {
                dataview.setText("请先更新数据库");
            } else {
                String whereClauses = "id=?";
                String[] whereArgs = {String.valueOf(1)};
//调用delete方法，删除数据
                db.delete("user", whereClauses, whereArgs);
            }

        }
    }
}
