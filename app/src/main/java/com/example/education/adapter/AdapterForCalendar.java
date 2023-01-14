package com.example.education.adapter;

import static android.graphics.Color.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.education.R;
import com.example.education.model.Events;

import java.time.LocalDate;
import java.util.List;
public class AdapterForCalendar extends ArrayAdapter<String> {
    private View selectView;
    private View today;
    private final LayoutInflater inflater;
    private final int layout;
    private final String[] date;
    private final int month;
    private final List<Events> events;
    private String dayNow;
    public AdapterForCalendar(@NonNull Context context, int resource, @NonNull String[] objects, int month, List<Events> events) {
        super(context, resource,  objects);
        layout=resource;
        date=objects;
        inflater = LayoutInflater.from(context);
        this.month = month;
        this.events = events;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        dayNow  =LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth();
        //selectDate = dayNow;
        String dateEvent = LocalDate.now().getYear() +"-"+(month+1)+"-"+date[position];
        if((position<7 && Integer.valueOf(date[position])>20) || (position>28 && Integer.valueOf(date[position])<20)) {
            if(position<7) {
                dateEvent = LocalDate.now().getYear() + "-" + (month) + "-" + date[position];
                if(dateEvent.equals(dayNow)){
                    View view = convertView.findViewById(R.id.cell_view);
                     view.setBackgroundResource(R.drawable.round_corners_selected);
                    selectView=convertView;
                    today=convertView;
                }
            }
            else {
                dateEvent = LocalDate.now().getYear() + "-" + (month + 2) + "-" + date[position];
                if(dateEvent.equals(dayNow)){
                    View view = convertView.findViewById(R.id.cell_view);
                    view.setBackgroundResource(R.drawable.round_corners_selected);
                    selectView=convertView;
                    today=convertView;
                }
            }
            viewHolder.cell.setTextColor(GRAY);

        }
        else if(month == LocalDate.now().getMonthValue()-1 && Integer.valueOf(date[position])==LocalDate.now().getDayOfMonth()) {
            View view = convertView.findViewById(R.id.cell_view);

            view.setBackgroundResource(R.drawable.round_corners_selected);
            selectView=convertView;
            today=convertView;
        }
        viewHolder.cell.setText(date[position]);
        for(Events event : events)
            if(dateEvent.equals(event.getDate()) && !(dateEvent.equals(dayNow))){
                viewHolder.eventIdentify.setBackgroundResource(R.drawable.round_corners_event);
            }
        return convertView;
    }

    public void selectItem(View cell,String date){
        View view1 = cell.findViewById(R.id.cell_view);
        View event = cell.findViewById(R.id.event_identify);
        if(selectView!=null) {
            View select = selectView.findViewById(R.id.cell_view);
            View eventId = selectView.findViewById(R.id.event_identify);
            select.setBackgroundResource(R.color.transparent);
//            if(!flag) {
//                eventId.setBackgroundResource(R.drawable.round_corners_event);
//                flag=true;
//            }
            for(Events eventsf : events)
                if(date.equals(eventsf.getDate()) && !(date.equals(dayNow))){
                    eventId.setBackgroundResource(R.drawable.round_corners_event);
                }
        }
        if(today!=null) {
            View view2 = today.findViewById(R.id.cell_view);
            view2.setBackgroundResource(R.drawable.round_corners_today);
        }
        view1.setBackgroundResource(R.drawable.round_corners_selected);
        if(event.getBackground().getAlpha()>0) {
            event.setBackgroundResource(R.color.transparent);
        }
        selectView=cell;
    }

    private class ViewHolder {
        final TextView cell;
        final View eventIdentify;
        ViewHolder(View view){
            cell = view.findViewById(R.id.cell);
            eventIdentify = view.findViewById(R.id.event_identify);
        }
    }
}
