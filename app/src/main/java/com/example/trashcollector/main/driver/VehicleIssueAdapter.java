package com.example.trashcollector.main.driver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcollector.databinding.CompletedRequestCardBinding;
import com.example.trashcollector.databinding.VehicleIssueCardBinding;
import com.example.trashcollector.main.customer.RequestAdapter;

public class VehicleIssueAdapter extends RecyclerView.Adapter<VehicleIssueAdapter.ViewHolder>{
    @NonNull
    @Override
    public VehicleIssueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VehicleIssueCardBinding view =  VehicleIssueCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleIssueAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private VehicleIssueCardBinding binding;

        public ViewHolder(@NonNull VehicleIssueCardBinding itemView) {
            super(itemView.getRoot());
            binding = VehicleIssueCardBinding.bind(itemView.getRoot());

        }
    }
}
