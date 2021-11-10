package com.example.trashcollector.main.customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcollector.databinding.CommentCardBinding;
import com.example.trashcollector.databinding.CompletedRequestCardBinding;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentCardBinding view =  CommentCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CommentCardBinding binding;
        public ViewHolder(@NonNull CommentCardBinding itemView) {
            super(itemView.getRoot());
            binding = CommentCardBinding.bind(itemView.getRoot());
        }
    }
}
