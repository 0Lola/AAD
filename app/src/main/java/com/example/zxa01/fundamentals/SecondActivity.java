package com.example.zxa01.fundamentals;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    private String size;
    private Button mButton;
    private LinearLayout mLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_main);

        if (savedInstanceState != null) {
            size = savedInstanceState.getString("value");
            Toast.makeText(this, "旋轉畫面", Toast.LENGTH_LONG).show();
        } else {
            Intent mIntent = getIntent();
            size = String.valueOf(mIntent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0));
        }

        TextView mtextView = (TextView) findViewById(R.id.amountPhone);
        mtextView.setText("共" + String.valueOf(size) + "筆");

        // date picker dialog
        mButton = (Button) findViewById(R.id.circleButton);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearDate);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(SecondActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        TextView dateText = new TextView(SecondActivity.this);
                        dateText.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(day));
                        dateText.setTextColor(R.color.colorPrimary);
                        dateText.setTextSize(20);
                        dateText.setPadding(100,10,10,10);
                        mLinearLayout.addView(dateText);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("value", String.valueOf(size));
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    // intent to main activity
    public void returnMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
