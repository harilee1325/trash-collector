package com.example.trashcollector.main.customer;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcollector.databinding.CompletedRequestCardBinding;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private final ArrayList<RequestModel> requestModels = new ArrayList<>();
    private OnItemClickListener listener;

    public RequestAdapter(ArrayList<RequestModel> requestModels) {
        this.requestModels.addAll(requestModels);
    }

    public interface OnItemClickListener {

        void onClickListener(String id);
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CompletedRequestCardBinding view = CompletedRequestCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        holder.binding.requestTitle.setText(requestModels.get(position).getDate());
        holder.binding.driverName.setText("Driver: "+requestModels.get(position).getDriverName());
        holder.binding.location.setText("Location: "+requestModels.get(position).getLocationName());
        holder.binding.status.setText("Status: "+requestModels.get(position).getStatus());

        if (holder.binding.status.getText().toString().contains("open")){
            holder.binding.status.setTextColor(Color.RED);
        }else{
            holder.binding.status.setTextColor(Color.GREEN);

        }

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CompletedRequestCardBinding binding;

        public ViewHolder(@NonNull CompletedRequestCardBinding itemView) {
            super(itemView.getRoot());
            binding = CompletedRequestCardBinding.bind(itemView.getRoot());
            itemView.seeRequestCard.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onClickListener(requestModels.get(position).getId());
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
