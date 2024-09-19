package com.example.countnum;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @ClassNameUserAdapter
 * @Author name
 * @Dates 2024/9/13
 * Description
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    private DialogView mDialogUpView;

    private BillDAO billDao;

    private SQLiteDatabase mDatabase;


    public UserAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.user_item, null);
        ViewHolder myViewHoder = new ViewHolder(view);
        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mName.setText(list.get(position));
        System.out.println(list.get(position));
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //删除对应商家数据
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 进行其他数据库操作
                    billDao = new BillDAO(context);
                    billDao.deleteBillByUser(list.get(position));
                } catch (SQLiteException e) {
                    // 数据库打开或创建失败，进行错误处理
                    e.printStackTrace();
                }
                list.remove(list.get(position));
                notifyDataSetChanged();
            }
        });

        //进入商家对应数据
        holder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //修改数据
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putExtra("user", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private Button mDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.user_name);
            mDelete = itemView.findViewById(R.id.user_delete);
        }

    }
}
