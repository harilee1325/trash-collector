package com.example.trashcollector.main.customer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcollector.databinding.CompletedRequestCardBinding;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{
    private OnItemClickListener listener;

    public interface OnItemClickListener {

        void onClickListener( String id);
    }
    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CompletedRequestCardBinding view =  CompletedRequestCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CompletedRequestCardBinding binding;
        public ViewHolder(@NonNull CompletedRequestCardBinding itemView) {
            super(itemView.getRoot());
            binding = CompletedRequestCardBinding.bind(itemView.getRoot());
            itemView.seeRequestCard.setOnClickListener(v->{
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onClickListener("1");
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
