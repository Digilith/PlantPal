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

public class PlantListAdapter extends RecyclerView.Adapter<PlantListViewHolder>{

    // DB here
    List<String> items;

    public PlantListAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PlantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_plant_list_item, parent, false);
        return new PlantListViewHolder(view).linkAdapter(this);
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
}


class PlantListViewHolder extends RecyclerView.ViewHolder{

    TextView name;
    ShapeableImageView avatar;
    private PlantListAdapter adapter;
    AlertDialog dialog;

    public PlantListViewHolder(@NonNull View itemView) {
        super(itemView);
        // Initialize name & avatar
        name = itemView.findViewById(R.id.mainListPlantName);
        avatar = itemView.findViewById(R.id.mainListPlantAvatar);
        // Deletes list item
        itemView.findViewById(R.id.listDeleteBtn).setOnClickListener(view -> {
            adapter.items.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
        });
        // Edits list item
        itemView.findViewById(R.id.listEditBtn).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
            View dialogBox = inflater.inflate(R.layout.activity_main_plant_list_edit_dialog,
                    null);
            EditText editName = dialogBox.findViewById(R.id.edit_name);
            Button editAvatarBtn = dialogBox.findViewById(R.id.edit_avatar);

            builder.setView(dialogBox);
            builder.setTitle("Edit Plant")
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name.setText(editName.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
                builder.show();
        });

    }

    public PlantListViewHolder linkAdapter(PlantListAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}