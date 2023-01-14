package com.example.education.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.R;
import com.example.education.model.Event;

import java.util.List;

public class AdapterForEvents extends RecyclerView.Adapter<AdapterForEvents.RecycleViewHolder> {
    public interface OnEventClickListener{
        void onEventClick(Event event, int position);
    }
    private final LayoutInflater inflater;
    private final List<Event> events;
    private final OnEventClickListener onEventClickListener;
    public AdapterForEvents(Context context, List<Event> events, OnEventClickListener onEventClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.events = events;
        this.onEventClickListener = onEventClickListener;
    }

    @NonNull
    @Override
    public AdapterForEvents.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event,parent,false);
        return new RecycleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterForEvents.RecycleViewHolder holder, int position) {
        if(events!=null) {
            Event event = events.get(position);
            holder.image.setImageResource(event.getImage());
            holder.fullName.setText(event.getFullName());
            holder.date.setText(event.getDate());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEventClickListener.onEventClick(event, holder.getAdapterPosition());
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        if(events==null)
            return 0;
        else
            return events.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView fullName, date;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.picture);
            fullName = itemView.findViewById(R.id.full_name);
            date = itemView.findViewById(R.id.date);
        }
    }
}
