package com.demo.swt.mystudyappshop.greendao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UserDao;
import com.demo.swt.mystudyappshop.R;

import java.util.ArrayList;
import java.util.List;

public class GreenMainAcitivity extends AppCompatActivity implements View.OnClickListener {
    private UserDao dao;
    //    private Button Add, Delect, Update, Query;
    private EditText EtId, EtName, EtAge, EtSex;
    private RecyclerView mRecyclerView;
    private List<User> mUserList = new ArrayList<>();
    private RvAdapter adapter;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.green_main);
        initview();
        openDb();
        initRv();
    }


    public void initRv() {
        mUserList = dao.loadAll();
        adapter = new RvAdapter(this, mUserList, R.layout.green_rv_item);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openDb() {
        mHelper = new DaoMaster.DevOpenHelper(this, "swt-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        dao = mDaoSession.getUserDao();
    }


    private void initview() {
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);
        EtId = (EditText) findViewById(R.id.et_id);
        EtName = (EditText) findViewById(R.id.et_name);
        EtAge = (EditText) findViewById(R.id.et_age);
        EtSex = (EditText) findViewById(R.id.et_sex);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_result);

//        dao =MyApplication.newInstance().getSession().getUserDao();

    }

    public Long getId() {
        Long id = Long.parseLong(EtId.getText().toString());
        return id;
    }

    public String getEtName() {
        String name = EtName.getText().toString();
        return name;
    }

    public int getAge() {
        if (EtAge.getText().toString() != null && !TextUtils.isEmpty(EtAge.getText().toString())) {
            int age = Integer.parseInt(EtAge.getText().toString());
            return age;
        }
        return 0;
    }

    public String getSex() {
        String sex = EtSex.getText().toString();
        return sex;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                if (getEtName() != null && EtAge.getText().toString() != null && getSex() != null) {
                    User insertUser = new User(null, getEtName(), getAge(), getSex());
                    dao.insert(insertUser);
                }
                break;
            case R.id.delete:
                dao.deleteByKey(getId());
                break;
            case R.id.update:
                User UpdateUser = new User(null, getEtName(), getAge(), getSex());
                dao.update(UpdateUser);
                break;
            case R.id.query:
                dao.loadAll();
                //动态展示的 所以暂时不需要这个东西
                break;
        }
        initRv();
        resetET();
    }

    private void resetET() {
        EtAge.setText("");
        EtName.setText("");
        EtSex.setText("");
        EtId.setText("");
    }
}
