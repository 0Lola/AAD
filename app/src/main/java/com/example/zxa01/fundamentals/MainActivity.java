package com.example.zxa01.fundamentals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxa01.fundamentals.DAO.PhoneDAO;
import com.example.zxa01.fundamentals.entity.Phone;
import com.example.zxa01.fundamentals.service.MyClassAdapter;
import com.example.zxa01.fundamentals.util.ConnectDatabase;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.zxa01.fundamentals.extra.MESSAGE";
    private static PhoneDAO phoneDao;
    private LinkedList<Phone> mList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private MyClassAdapter mClassAdapter;
    private Button mButton;
    private EditText mEditText;
    private Spinner mSpinner;
    private Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();
        initComponent();
        initRecyclerView();

        // ADD
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mList.size();
                long id = phoneDao.insertPhone(new Phone(mEditText.getEditableText().toString(), mSpinner.getSelectedItem().toString()));
                mList.addFirst(phoneDao.getNewPhone(id));
                // Update
                mRecyclerView.getAdapter().notifyItemInserted(0); // 通知新增的位置
                mRecyclerView.smoothScrollToPosition(0); // Scroll to the bottom:(size) top:(size-1)
                toast.show();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    // intent to second activity
    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, mList.size());
        startActivity(intent);
    }

    // init database
    private void initDB(){
        phoneDao = ConnectDatabase.getDatabase(getApplicationContext()).phoneDao();
        mList.addAll(phoneDao.getAllPhone());
    }

    // init Component
    private void initComponent(){
        toast = Toast.makeText(MainActivity.this, "已新增資料", Toast.LENGTH_SHORT);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    // init RecyclerView
    private void initRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mClassAdapter = new MyClassAdapter(this, mList);
        mRecyclerView.setAdapter(mClassAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}