package com.example.trashcollector.main.officer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcollector.databinding.DriverNameCardBinding;
import com.example.trashcollector.databinding.VehicleIssueCardBinding;
import com.example.trashcollector.main.customer.RequestAdapter;
import com.example.trashcollector.main.driver.VehicleIssueAdapter;

import java.util.ArrayList;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder>{
    private final List<DriverModel> driverModels = new ArrayList<>();
    private OnItemClickListener listener;

    public DriverAdapter(List<DriverModel> driverModels) {
        this.driverModels.addAll(driverModels);
    }

    public interface OnItemClickListener {

        void onClickListener(String id, String name);
    }
    @NonNull
    @Override
    public DriverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DriverNameCardBinding view =  DriverNameCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.ViewHolder holder, int position) {

        holder.binding.dirverLocation.setText("Location: "+driverModels.get(position).getLocation());
        holder.binding.driverName.setText("Name: "+driverModels.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return driverModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private DriverNameCardBinding binding;
        public ViewHolder(@NonNull DriverNameCardBinding itemView) {
            super(itemView.getRoot());
            binding = DriverNameCardBinding.bind(itemView.getRoot());
            itemView.pickedUpBt.setOnClickListener(v->{
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onClickListener(driverModels.get(position).getId(), driverModels.get(position).getName());
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
