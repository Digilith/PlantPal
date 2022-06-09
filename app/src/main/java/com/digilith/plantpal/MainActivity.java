package com.digilith.plantpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MaterialButton addPlantBtn;
    private ArrayAdapter<String> adapter;
    private RecyclerView recyclerView;
    String[] data = {"New Plant", "New Plant", "New Plant"};
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shifting calendar to current date
        CalendarView cv = findViewById(R.id.calendarView);
        cv.setDate(System.currentTimeMillis(),false,true);

        List<String> items = new LinkedList<>();
        items.add("NEW Plant");

        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlantListAdapter adapter = new PlantListAdapter(items);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.mainAddPlantBtn).setOnClickListener(view -> {
            items.add(data[counter%3]);
            counter++;
            adapter.notifyItemInserted(items.size()-1);
        });
    }
}