package com.digilith.plantpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView cv = findViewById(R.id.calendarView);
        cv.setDate(System.currentTimeMillis(),false,true);
    }
}