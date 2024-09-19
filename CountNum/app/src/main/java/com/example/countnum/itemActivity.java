package com.example.countnum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class itemActivity extends AppCompatActivity {

    private EditText date,price,num;
    private Button btn;
    private BillDAO billDao;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        billDao = new BillDAO(this);
        Bill bill = billDao.getBillById(id);

        findViews();

        date.setText(bill.getMonthDate());
        price.setText(bill.getUnitPrice());
        num.setText(bill.getNum());

        btn.setOnClickListener(v -> {
            if(isNumeric2(price.getText().toString()) && isNumeric2(num.getText().toString())) {
                bill.setMonthDate(date.getText().toString());
                bill.setUnitPrice(price.getText().toString());
                bill.setNum(num.getText().toString());
                billDao.updateBill(bill);
                Intent intent1 = new Intent(itemActivity.this,UserDetailActivity.class);
                intent1.putExtra("user",bill.getUserName());
                startActivity(intent1);
            } else {
                Toast.makeText(this,
                        "请检查输入的单价和数量是否为数字",
                        Toast.LENGTH_LONG).show();
            }

        });

    }

    private void findViews() {
        date = findViewById(R.id.i_bill_name_tx);
        price = findViewById(R.id.i_bill_money_tx);
        num = findViewById(R.id.i_bill_number_tx);
        btn = findViewById(R.id.item_confirm);
    }

    //判断是否为数字
    public boolean isNumeric2(String str) {
        return str != null && NUMBER_PATTERN.matcher(str).matches();
    }
}