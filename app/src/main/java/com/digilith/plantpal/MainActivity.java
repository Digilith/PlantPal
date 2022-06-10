package com.digilith.plantpal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    //private RecyclerView recyclerView;
    LinearLayout layout;
    // Plants list
    List<String> items = new LinkedList<>();
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shifting calendar to current date
        CalendarView cv = findViewById(R.id.calendarView);
        cv.setDate(System.currentTimeMillis(),false,true);

        layout = findViewById(R.id.linearLayout);

        // Adding new plants
        findViewById(R.id.mainAddPlantBtn).setOnClickListener(view -> {
            addPlant();
            //items.add("New Plant");
            //adapter.notifyItemInserted(items.size()-1);
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

                // Dialog box logic
                builder.setTitle("Edit Plant")
                        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                name.setText(editName.getText().toString());
                                note.setText(editNote.getText().toString());
                                avatar.setImageURI(imageUri);
                                // TODO: image upload
                                // TODO: pick date
                                /*pickTimeBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });*/
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
}