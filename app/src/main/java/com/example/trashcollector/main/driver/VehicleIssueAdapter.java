package com.example.trashcollector.main.driver;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trashcollector.databinding.CompletedRequestCardBinding;
import com.example.trashcollector.databinding.VehicleIssueCardBinding;
import com.example.trashcollector.main.Utility;
import com.example.trashcollector.main.customer.RequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class VehicleIssueAdapter extends RecyclerView.Adapter<VehicleIssueAdapter.ViewHolder>{
    private final List<IssueModel> issueModels = new ArrayList<>();
    private OnItemClickListener listener;

    public VehicleIssueAdapter(List<IssueModel> issueModel) {
        this.issueModels.addAll(issueModel);
    }
    public interface OnItemClickListener {

        void onClickListener(String id);
    }

    @NonNull
    @Override
    public VehicleIssueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VehicleIssueCardBinding view =  VehicleIssueCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleIssueAdapter.ViewHolder holder, int position) {

        holder.binding.status.setText("Status: "+issueModels.get(position).getStatus());
        if (holder.binding.status.getText().toString().contains("open")){
            holder.binding.status.setTextColor(Color.RED);
            holder.binding.issuesSolved.setVisibility(View.VISIBLE);

        }else{
            holder.binding.status.setTextColor(Color.GREEN);
            holder.binding.issuesSolved.setVisibility(View.GONE);

        }
        holder.binding.issue.setText(issueModels.get(position).getIssue());
        holder.binding.issueId.setText("Issue No: #"+(position+1));
        holder.binding.driverName.setText("Driver Name: "+issueModels.get(position).getDriverName());

        if (Utility.getUtilityInstance().getPreference(holder.itemView.getContext(), Utility.TYPE).equalsIgnoreCase("driver")){
            holder.binding.issuesSolved.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return issueModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private VehicleIssueCardBinding binding;

        public ViewHolder(@NonNull VehicleIssueCardBinding itemView) {
            super(itemView.getRoot());
            binding = VehicleIssueCardBinding.bind(itemView.getRoot());
            itemView.issuesSolved.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onClickListener(issueModels.get(position).getId());
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
