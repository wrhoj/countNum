package com.example.countnum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 吴若恒
 * @date: 2022/9/13
 * @time: 2024.9.19
 * @desc:主页面**/
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloatingActionButton button,delBtn;
    private List<String> userList = new ArrayList<>();

    private DialogView mDialogUpView;
    UserAdapter adapter;
    private EditText mTextName;
    private Button mTextCancel, mTextConfirm;

    private SQLiteDatabase mDatabase;

    private BillDAO billDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        try {
            // 尝试打开数据库
            mDatabase = openOrCreateDatabase("mydb.db", MODE_PRIVATE, null);
            // 进行其他数据库操作
            billDao = new BillDAO(this);
            userList = billDao.getAllUser();
        } catch (SQLiteException e) {
            // 打开数据库失败
            e.printStackTrace();
            Toast.makeText(this, "无法打开数据库", Toast.LENGTH_SHORT).show();
        }

        adapter = new UserAdapter(MainActivity.this,userList);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        //添加默认的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);

        showDialogView();

        button.setOnClickListener(v -> {
            UserDialog.getInstance().show(mDialogUpView);
            adapter.notifyDataSetChanged();
        });

        delBtn.setOnClickListener(v -> {
            billDao.deleteAllBills();
            userList.clear();
            adapter.notifyDataSetChanged();
        });


    }


    private void showDialogView() {
        mDialogUpView = UserDialog.getInstance().initView(this, R.layout.user_dialog, Gravity.BOTTOM);
        mTextName =  mDialogUpView.findViewById(R.id.dialog_edit);
        mTextCancel =  mDialogUpView.findViewById(R.id.dialog_cancel);
        mTextConfirm =  mDialogUpView.findViewById(R.id.dialog_confirm);

        mTextCancel.setOnClickListener(v -> {
            Toast.makeText(this, "取消退出", Toast.LENGTH_SHORT).show();
            UserDialog.getInstance().hide(mDialogUpView); //关闭对话框
        });

        mTextConfirm.setOnClickListener(v -> {
            userList.add(mTextName.getText().toString());
            adapter.notifyDataSetChanged();
            mTextName.setText("");
            Toast.makeText(this, "确认", Toast.LENGTH_SHORT).show();
            UserDialog.getInstance().hide(mDialogUpView); //关闭对话框
        });
    }

    private void findViews() {
        mRecyclerView = findViewById(R.id.rv_view);
        button = findViewById(R.id.add_user);
        delBtn = findViewById(R.id.delete_user);
    }
}