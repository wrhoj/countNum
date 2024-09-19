package com.example.countnum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassNameUserAdapter
 * @Author name
 * @Dates 2024/9/13
 * Description
 */
public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private Context context;
    private List<Bill> list = new ArrayList<>();

    private BillDAO billDao;

    String userName;

    public BillAdapter(Context context, List<Bill> list, String userName) {
        this.context = context;
        this.list = list;
        this.userName = userName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.bill_item, null);
        ViewHolder myViewHoder = new ViewHolder(view);
        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mMoney.setText(list.get(position).getUnitPrice());
        holder.mNumber.setText(list.get(position).getNum());
        holder.mDate.setText(list.get(position).getMonthDate());
        billDao = new BillDAO(context);
        //删除对应账单数据
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDao.deleteBill(list.get(position).getId());
                list.remove(list.get(position));
                notifyDataSetChanged();
            }
        });

        //修改对应账单数据
        holder.mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, itemActivity.class);
                intent.putExtra("id", list.get(position).getId());
                context.startActivity(intent);
//                list.get(position).setUnitPrice(holder.mMoney.getText().toString());
//                list.get(position).setNum(holder.mNumber.getText().toString());
//                list.get(position).setMonthDate(holder.mDate.getText().toString());
//                billDao.updateBill(list.get(position));
//                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public double mCount(){
        double count = 0;
        for (int i = 0; i < list.size(); i++) {
            count += Double.parseDouble(list.get(i).getUnitPrice()) * Double.parseDouble(list.get(i).getNum());
        }
        return count;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate,mMoney,mNumber;
        private Button mDelete,mUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.bill_name_tx);
            mMoney = itemView.findViewById(R.id.bill_money_tx);
            mNumber = itemView.findViewById(R.id.bill_number_tx);
            mDelete = itemView.findViewById(R.id.bill_delete);
            mUpdate = itemView.findViewById(R.id.bill_update);
        }

    }


}
