package com.digilith.plantpal;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class PlantListAdapter extends RecyclerView.Adapter<PlantListAdapter.PlantListViewHolder>{

    // DB here
    List<String> items;
    OnPlantListener onPlantListener;

    public PlantListAdapter(List<String> items, OnPlantListener onPlantListener) {
        this.items = items;
        this.onPlantListener = onPlantListener;
    }

    @NonNull
    @Override
    public PlantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_plant_list_item, parent, false);
        return new PlantListViewHolder(view, onPlantListener);
    }

    // DB here
    @Override
    public void onBindViewHolder(@NonNull PlantListViewHolder holder, int position) {
        holder.name.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PlantListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView note;
        ShapeableImageView avatar;
        private PlantListAdapter adapter;
        AlertDialog dialog;
        OnPlantListener onPlantListener;

        public PlantListViewHolder(@NonNull View itemView, OnPlantListener onPlantListener) {
            super(itemView);
            // Attach onclicklistener to the viewholder
            itemView.setOnClickListener(this);
            //Set onplantlistener
            this.onPlantListener = onPlantListener;
            // Initialize name & avatar
            name = itemView.findViewById(R.id.mainListPlantName);
            avatar = itemView.findViewById(R.id.mainListPlantAvatar);
            note = itemView.findViewById(R.id.mainPlantListNote);
            // Deletes list item
            itemView.findViewById(R.id.listDeleteBtn).setOnClickListener(view -> {
                adapter.items.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
            });
            // Edits list item
            itemView.findViewById(R.id.listEditBtn).setOnClickListener(view -> {
                // Create dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                // Inflate custom view
                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                View dialogBox = inflater.inflate(R.layout.activity_main_plant_list_edit_dialog,
                        null);
                // Initialize elements
                EditText editName = dialogBox.findViewById(R.id.editTextName);
                EditText editNote = dialogBox.findViewById(R.id.editTextNote);
                Button editAvatarBtn = dialogBox.findViewById(R.id.editTextAvatar);

                // Use layout
                builder.setView(dialogBox);
                // Dialog box logic
                builder.setTitle("Edit Plant")
                        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                name.setText(editName.getText().toString());
                                note.setText(editNote.getText().toString());
                                // TODO: image upload
                                // TODO: db
                                // TODO: don't change when empty
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
            });

        }

        public PlantListViewHolder linkAdapter(PlantListAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        @Override
        public void onClick(View view) {
            onPlantListener.onEditClick(getAdapterPosition());
        }
    }

    // Handling click events on recyclerview
    public interface OnPlantListener {
        void onEditClick(int position);

    }
}