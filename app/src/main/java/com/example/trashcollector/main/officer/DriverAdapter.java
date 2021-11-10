package com.example.trashcollector.main.officer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcollector.databinding.DriverNameCardBinding;
import com.example.trashcollector.databinding.VehicleIssueCardBinding;
import com.example.trashcollector.main.driver.VehicleIssueAdapter;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder>{
    @NonNull
    @Override
    public DriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DriverNameCardBinding view =  DriverNameCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private DriverNameCardBinding binding;
        public ViewHolder(@NonNull DriverNameCardBinding itemView) {
            super(itemView.getRoot());
            binding = DriverNameCardBinding.bind(itemView.getRoot());

        }
    }
}
