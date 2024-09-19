package com.example.countnum;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countnum.databinding.ActivityUserDetailBinding;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserDetailActivity extends AppCompatActivity {

    private ActivityUserDetailBinding binding;
    private BillAdapter adapter;
    private RecyclerView recyclerView;
    private DialogView mDialogUpView,cDialogUpView;
    private EditText mDate,mUnitPrice,mNumber;
    private Button nConfirm,nDelete;
    private TextView mCount;
    private FloatingActionButton addBtn,countBtn,returnBtn;
    private BillDAO billDao;

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");


    List<Bill> billList = new ArrayList<>();
    Bill mBill = new Bill();
    int position;
    String userName;
    private SQLiteDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        userName = intent.getStringExtra("user");
        try {
            // 尝试打开数据库
            mDatabase = openOrCreateDatabase("mydb.db", MODE_PRIVATE, null);
            // 进行其他数据库操作
            billDao = new BillDAO(this);
            billList = billDao.getBillByUser(userName);
        } catch (SQLiteException e) {
            // 数据库打开或创建失败，进行错误处理
            e.printStackTrace();
            Toast.makeText(this, "11无法打开数据库", Toast.LENGTH_SHORT).show();
        }


        /**绑定控件**/
        findViews();

        adapter = new BillAdapter(this,billList,userName);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserDetailActivity.this);
        //添加默认的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

        showBillDialogView();


        addBtn.setOnClickListener(v -> {
            UserDialog.getInstance().show(mDialogUpView);
            adapter.notifyDataSetChanged();
        });

        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double v = adapter.mCount();
                showCountDialogView(v);
                UserDialog.getInstance().show(cDialogUpView);
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDialog.getInstance().show(mDialogUpView);
                adapter.notifyDataSetChanged();
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(UserDetailActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }



    private void findViews() {
        recyclerView = findViewById(R.id.rvuser_view);
        addBtn = findViewById(R.id.add_btn);
        countBtn = findViewById(R.id.count_btn);
        returnBtn = findViewById(R.id.return_btn);
    }

    @Override
    public boolean onSupportNavigateUp() {

        return false;
    }

    public void showBillDialogView() {
        mDialogUpView = UserDialog.getInstance().initView(this, R.layout.bill_dialog, Gravity.BOTTOM);
        mDate =  mDialogUpView.findViewById(R.id.dialog_edit_date);
        mUnitPrice = mDialogUpView.findViewById(R.id.dialog_edit_price);
        mNumber = mDialogUpView.findViewById(R.id.dialog_edit_num);
        nDelete =  mDialogUpView.findViewById(R.id.dialog_bill_cancel);
        nConfirm =  mDialogUpView.findViewById(R.id.dialog_bill_confirm);

        try {
            // 尝试打开数据库
            mDatabase = openOrCreateDatabase("mydb.db", MODE_PRIVATE, null);
            // 进行其他数据库操作
            billDao = new BillDAO(this);
            nDelete.setOnClickListener(v -> {
                Toast.makeText(this, "取消退出", Toast.LENGTH_SHORT).show();
                UserDialog.getInstance().hide(mDialogUpView); //关闭对话框
            });

            nConfirm.setOnClickListener(v -> {
                Bill bill = new Bill();
                //生成随机id
                String id = String.valueOf(System.currentTimeMillis());
                bill.setId(id);
                if(isNumeric2(mUnitPrice.getText().toString()) && isNumeric2(mNumber.getText().toString())) {
                    bill.setMonthDate(mDate.getText().toString());
                    bill.setUnitPrice(mUnitPrice.getText().toString());
                    bill.setNum(mNumber.getText().toString());
                    bill.setUserName(userName);
                    //加入列表
                    billList.add(bill);
                    //加入数据库
                    billDao.addBill(bill);
                } else {
                    Toast.makeText(this, "请检查输入的单价和数量是否为数字", Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "确认成功", Toast.LENGTH_SHORT).show();
                mDate.setText("");
                mUnitPrice.setText("");
                mNumber.setText("");
                UserDialog.getInstance().hide(mDialogUpView);
            });

        } catch (SQLiteException e) {
            // 打开数据库失败
            e.printStackTrace();
            Toast.makeText(this, "无法打开数据库", Toast.LENGTH_SHORT).show();
        }


    }
    public void showCountDialogView(Double num) {
        cDialogUpView = UserDialog.getInstance().initView(this, R.layout.count_dialog, Gravity.BOTTOM);
        mCount =  cDialogUpView.findViewById(R.id.text_count);
        mCount.setText("计算结果："+num.toString());

    }

    //判断是否为数字
    public boolean isNumeric2(String str) {
        return str != null && NUMBER_PATTERN.matcher(str).matches();
    }
}