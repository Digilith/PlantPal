package com.digilith.plantpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.digilith.plantpal.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PlantListAdapter.OnPlantListener{
    private RecyclerView recyclerView;
    // Plants list
    List<String> items = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shifting calendar to current date
        CalendarView cv = findViewById(R.id.calendarView);
        cv.setDate(System.currentTimeMillis(),false,true);


        // Setting up recyclerview
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlantListAdapter adapter = new PlantListAdapter(items, this);
        recyclerView.setAdapter(adapter);

        // Adding new plants
        findViewById(R.id.mainAddPlantBtn).setOnClickListener(view -> {
            items.add("New Plant");
            adapter.notifyItemInserted(items.size()-1);
        });

        //Status bar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.malachite));
    }

    @Override
    public void onEditClick(int position) {
        items.get(position);
    }
}