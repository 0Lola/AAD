package com.example.zxa01.fundamentals.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zxa01.fundamentals.DAO.PhoneDAO;
import com.example.zxa01.fundamentals.R;
import com.example.zxa01.fundamentals.entity.Phone;
import com.example.zxa01.fundamentals.util.ConnectDatabase;

import java.util.LinkedList;

public class MyClassAdapter extends RecyclerView.Adapter<MyClassAdapter.MyViewHolder> {

    private final LinkedList<Phone> mList;
    private final LayoutInflater mInflater;
    private Context mContext;

    // 建構值
    public MyClassAdapter(Context context, LinkedList<Phone> mList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder mholder, int position) {
        Phone phone = mList.get(position);
        mholder.position = position;
        mholder.phone = phone;
        mholder.itemView.setText(phone.getPhone());
        mholder.typeView.setText(phone.getType());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void editAlertDialog(final int position) {
        final Phone mPhone = mList.get(position);
        final PhoneDAO phoneDao = ConnectDatabase.getDatabase(mContext.getApplicationContext()).phoneDao();

        // setup input view
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.phone_dialog, null);
        TextView id = (TextView) v.findViewById(R.id.textID);
        final EditText editPhone = (EditText) v.findViewById(R.id.editPhone);
        id.setText(String.valueOf(mPhone.getId()));
        editPhone.setText(mPhone.getPhone());

        // setup builder
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(R.string.dialog_message)
                .setView(v).setPositiveButton(R.string.dialog_correct, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPhone.setPhone(editPhone.getText().toString());
                        phoneDao.insertPhone(mPhone);
                        mList.set(position,mPhone);
                        notifyItemChanged(position);
                    }
                })
                .setNegativeButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phoneDao.deletePhone(mPhone);
                        mList.remove(position);
                        notifyDataSetChanged();
                    }
                })
                .setNeutralButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();

    }

    // 建立 custom holder
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;
        public Phone phone;
        public TextView itemView;
        public TextView typeView;
        public Button editButton;
        public MyClassAdapter mAdapter;
        public LinearLayout mLinearLayout;

        public MyViewHolder(View itemView, MyClassAdapter adapter) {
            super(itemView);
            this.mLinearLayout = (LinearLayout) itemView.findViewById(R.id.phoneLayout);
            this.itemView = (TextView) itemView.findViewById(R.id.phone);
            this.typeView = (TextView) itemView.findViewById(R.id.type);
            this.mAdapter = adapter;
            this.mLinearLayout.setOnClickListener(this);

            this.editButton = (Button) itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new Button.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    // 編輯
                    editAlertDialog(position);
                }
            });
        }

        @Override
        public void onClick(View v) {
            // implicit intents
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.ACTION_SEND, itemView.getText().toString());
            intent.setType("text/plain");
            // 選單式
            Intent chooser = Intent.createChooser(intent, "選取發送方式");
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(chooser);
            } else {
                Log.v("ImplicitIntents", "Can't handle this intent!");
            }
        }
    }

}