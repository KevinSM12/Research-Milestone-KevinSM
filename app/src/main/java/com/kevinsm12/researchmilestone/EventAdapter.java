package com.kevinsm12.researchmilestone;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<EventEntity> events;

    public EventAdapter(List<EventEntity> events) {
        this.events = events;
    }

    public void updateData(List<EventEntity> newEvents) {
        this.events = newEvents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventEntity item = events.get(position);
        holder.tvName.setText(item.eventName);
        holder.tvDateRoute.setText(item.eventDate + " • " + item.eventRoute);
        holder.tvBudget.setText(item.allocatedBudget);
        holder.tvRegistered.setText("0 Students");

        if (item.description != null && !item.description.isEmpty()) {
            holder.tvDesc.setText(item.description);
        }

        holder.btnManage.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AnalyticsActivity.class);
            intent.putExtra("event_name", item.eventName);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDateRoute, tvBudget, tvDesc, tvRegistered;
        View btnManage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvDateRoute = itemView.findViewById(R.id.tvItemDateRoute);
            tvBudget = itemView.findViewById(R.id.tvItemBudget);
            tvDesc = itemView.findViewById(R.id.tvItemDesc);
            tvRegistered = itemView.findViewById(R.id.tvItemRegistered);
            btnManage = itemView.findViewById(R.id.btnManage);
        }
    }
}
