package com.digilith.plantpal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    //private RecyclerView recyclerView;
    LinearLayout layout;
    // Plants list
    List<String> items = new LinkedList<>();
    Uri imageUri;
    int hour, minute;
    PendingIntent operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        layout = findViewById(R.id.linearLayout);

        Calendar calendar = Calendar.getInstance();

        // Adding new plants
        findViewById(R.id.mainAddPlantBtn).setOnClickListener(view -> {
            addPlant();
        });

        //Status bar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.malachite));
    }

    private void addPlant() {
        View view = getLayoutInflater().inflate(R.layout.activity_main_plant_list_item, null);
        TextView name = view.findViewById(R.id.mainListPlantName);
        TextView note = view.findViewById(R.id.mainPlantListNote);
        ShapeableImageView avatar = view.findViewById(R.id.mainListPlantAvatar);
        MaterialButton del = view.findViewById(R.id.listDeleteBtn);
        MaterialButton edit = view.findViewById(R.id.listEditBtn);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().equals("Bob"))
                    Toast.makeText(MainActivity.this, "Watered 6 10 2022", Toast.LENGTH_LONG)
                            .show();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }
        });

        // Edits list item
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                // Inflate custom view
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dialogBox = inflater.inflate(R.layout.activity_plant_edit,null);

                // Initialize elements
                EditText editName = dialogBox.findViewById(R.id.editTextName);
                EditText editNote = dialogBox.findViewById(R.id.editTextNote);
                TextView time = dialogBox.findViewById(R.id.textViewTime);
                Button editAvatarBtn = dialogBox.findViewById(R.id.editAvatarBtn);
                Button pickTimeBtn = dialogBox.findViewById(R.id.pickTimeBtn);

                // Use layout
                builder.setView(dialogBox);

                editAvatarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 3);
                    }
                });

                pickTimeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popTimePicker(v, time);
                        Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
                        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
                                0, intent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        long timeAtButtonClick;
                        timeAtButtonClick = System.currentTimeMillis();
                        long tenSecondsMillis = 1000 * 10;
                        alarmManager.set(AlarmManager.RTC_WAKEUP,
                                timeAtButtonClick + tenSecondsMillis, pendingIntent);
                    }
                });

                // Dialog box logic
                builder.setTitle("Edit Plant")
                        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(!editName.getText().toString().isEmpty())
                                    name.setText(editName.getText().toString());
                                if(!editNote.getText().toString().isEmpty())
                                    note.setText(editNote.getText().toString());
                                if(imageUri != null)
                                    avatar.setImageURI(imageUri);
                                imageUri = null;

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                // Show dialog
                builder.show();
            }
        });

        layout.addView(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
        }
    }

    public void popTimePicker(View view, TextView time) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                time.setText(String
                        .format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog =
                new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Pick time");
        timePickerDialog.show();
    }

    private void createNotificationChannel() {
        CharSequence name = "plantpal";
        String description = "Channel for PlantPal";
        NotificationChannel channel = new NotificationChannel("plantpal", name, NotificationManager.IMPORTANCE_HIGH);

    }
}

