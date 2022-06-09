package com.digilith.plantpal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    private PlantListAdapter adapter;

    public PlantListViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.mainListPlantName);
        itemView.findViewById(R.id.listDeleteBtn).setOnClickListener(view -> {
            adapter.items.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
        });
    }

    public PlantListViewHolder linkAdapter(PlantListAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}