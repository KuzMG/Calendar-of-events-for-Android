package com.example.education.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.R;
import com.example.education.model.Events;
import java.time.LocalDate;
import java.util.List;

public class AdapterForPagerOfCalendar extends RecyclerView.Adapter<AdapterForPagerOfCalendar.Pager> {
    public interface Click {
        void click(int page, int month, int day);
        void select(String text,int month);
    }
    private final AdapterForEvents.OnEventClickListener onEventClickListener;
    private final LayoutInflater inflater;
    private final String[][] date;
    public AdapterForCalendar nextAdapter;
    public AdapterForCalendar lastAdapter;
    private final RecyclerView recyclerView;
    private final List<Events> events;
    private final Click click;
    private TextView dayOfWeek;
    private String selectDate;
    public AdapterForPagerOfCalendar(AdapterForEvents.OnEventClickListener onEventClickListener, Context context,
                                     String[][] date, RecyclerView recyclerView, List<Events> events, Click click) {
        this.onEventClickListener = onEventClickListener;
        this.inflater = LayoutInflater.from(context);
        this.date = date;
        this.recyclerView = recyclerView;
        this.events = events;
        this.click = click;
    }
    @NonNull
    @Override
    public Pager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Pager(inflater.inflate(R.layout.page_calendar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Pager holder, int position) {
        String[] cell = date[position];
        if(LocalDate.now().getMonthValue()==position+1)
            switch (LocalDate.now().getDayOfWeek().getValue()) {
                case 1:
                    dayOfWeek = holder.itemView.findViewById(R.id.monday);
                    dayOfWeek.setTextColor(holder.itemView.getResources().getColor(R.color.winter_today));
                    break;
                case 2:
                    dayOfWeek = holder.itemView.findViewById(R.id.tuesday);
                    dayOfWeek.setTextColor(holder.itemView.getResources().getColor(R.color.winter_today));
                    break;
                case 3:
                    dayOfWeek = holder.itemView.findViewById(R.id.wednesday);
                    dayOfWeek.setTextColor(holder.itemView.getResources().getColor(R.color.winter_today));
                    break;
                case 4:
                    dayOfWeek = holder.itemView.findViewById(R.id.thursday);
                    dayOfWeek.setTextColor(holder.itemView.getResources().getColor(R.color.winter_today));
                    break;
                case 5:
                    dayOfWeek = holder.itemView.findViewById(R.id.friday);
                    dayOfWeek.setTextColor(holder.itemView.getResources().getColor(R.color.winter_today));
                    break;
                case 6:
                    dayOfWeek = holder.itemView.findViewById(R.id.saturday);
                    dayOfWeek.setTextColor(holder.itemView.getResources().getColor(R.color.winter_today));
                    break;
                case 7:
                    dayOfWeek = holder.itemView.findViewById(R.id.sunday);
                    dayOfWeek.setTextColor(holder.itemView.getResources().getColor(R.color.winter_today));
                    break;
            }
        String dayNow  =LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth();
        selectDate=dayNow;
        AdapterForCalendar adapter = new AdapterForCalendar(inflater.getContext(),R.layout.cell,cell,position,events);
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridView grid = adapterView.findViewById(R.id.gridview);
                AdapterForCalendar adapter = (AdapterForCalendar) grid.getAdapter();
                View cell = view.findViewById(R.id.layout_cell);
                TextView cellText = cell.findViewById(R.id.cell);
                String text = (String) cellText.getText();
                String date;
                if(i<6 && Integer.valueOf(text)>6) {
                    if(holder.getAdapterPosition()==0)
                        return;
                    date = LocalDate.now().getYear() + "-" + (holder.getAdapterPosition()) + "-" + text;
                    nextAdapter.selectItem(cell,selectDate );
                    lastAdapter.selectItem(cell,selectDate);
                    if(i==0 && (Integer.valueOf(text)==31 || Integer.valueOf(text)==30) || i==1 && Integer.valueOf(text)==31) {
                        click.click(holder.getAdapterPosition()-1,holder.getAdapterPosition()-1,42-7+i);
                    }
                    else {
                        click.click(holder.getAdapterPosition()-1,holder.getAdapterPosition()-1,42-14+i);
                    }
                    adapter.selectItem(cell,"date");
                    selectDate = date;
                    lastAdapter = adapter;
                    //date = LocalDate.now().getYear() + "-" + (holder.getAdapterPosition()) + "-" + text;
                }
                else if(i>28 && Integer.valueOf(text)<20) {
                    if(holder.getAdapterPosition()==11)
                        return;
                    if (holder.getAdapterPosition() + 2 > 12) {
                        date = LocalDate.now().getYear() + 1 + "-" + 1 + "-" + text;
                    }
                    else
                        date = LocalDate.now().getYear() + "-" + (holder.getAdapterPosition() + 2) + "-" + text;
                    nextAdapter.selectItem(cell,selectDate);
                    lastAdapter.selectItem(cell,selectDate);
                    if(42-i+ Integer.valueOf(text)<=8) {
                        click.click(holder.getAdapterPosition()+1,holder.getAdapterPosition()+1,7-42+i);
                    }
                    else {
                        click.click(holder.getAdapterPosition()+1,holder.getAdapterPosition()+1,14-42+i);
                    }
                    adapter.selectItem(cell,"date");
                    selectDate = date;
                    lastAdapter = adapter;
                }
                else {
                    date = LocalDate.now().getYear()+"-"+(holder.getAdapterPosition()+1) +"-"+text;
                    nextAdapter.selectItem(cell,selectDate);
                    lastAdapter.selectItem(cell,selectDate);
                    if(Integer.valueOf(text)<20 && holder.getAdapterPosition()>0) {
                        click.select(text, holder.getAdapterPosition() - 1);
                    }
                    else if(Integer.valueOf(text)>20 && holder.getAdapterPosition()<11) {
                        click.select(text, holder.getAdapterPosition() +1);
                    }
                    adapter.selectItem(cell,"date");
                    selectDate = date;
                    lastAdapter =adapter;
                }
                eventOutput(date);
            }
        });
        holder.gridView.setAdapter(adapter);
        if(position == LocalDate.now().getMonthValue()-1) {
            lastAdapter = (AdapterForCalendar) holder.gridView.getAdapter();
            nextAdapter = (AdapterForCalendar) holder.gridView.getAdapter();
            eventOutput(dayNow);
        }
        if(position == LocalDate.now().getMonthValue()-2){
            AdapterForCalendar adapter1 = (AdapterForCalendar) holder.gridView.getAdapter();
            for (int i=0;i<adapter1.getCount();i++){
                if(adapter1.getItem(i).equals(String.valueOf(LocalDate.now().getDayOfMonth())) && i>28 &&
                        Integer.valueOf(adapter1.getItem(i))<20){
                    lastAdapter=(AdapterForCalendar) holder.gridView.getAdapter();
                }
            }
        }
        if(position == LocalDate.now().getMonthValue()){
            AdapterForCalendar adapter1 = (AdapterForCalendar) holder.gridView.getAdapter();
            for (int i=0;i<adapter1.getCount();i++){
                if(adapter1.getItem(i).equals(String.valueOf(LocalDate.now().getDayOfMonth())) && i<7 &&
                        Integer.valueOf(adapter1.getItem(i))>20){
                    nextAdapter=(AdapterForCalendar) holder.gridView.getAdapter();
                }

            }
        }
    }
    @Override
    public int getItemCount() {
        return date.length;
    }

    private void eventOutput(String date){
        boolean flag = false;
        for(Events event : events){
            if(date.equals(event.getDate())){
                AdapterForEvents recycleAdapter = new AdapterForEvents(recyclerView.getContext(), event.getEvent(), onEventClickListener);
                recyclerView.setAdapter(recycleAdapter);
                flag=true;
            }
        }
        if(!flag){
            AdapterForEvents recycleAdapter = new AdapterForEvents(recyclerView.getContext(), null, onEventClickListener);
            recyclerView.setAdapter(recycleAdapter);
        }
    }

    class Pager extends RecyclerView.ViewHolder {
        final GridView gridView;
        public Pager(@NonNull View itemView) {
            super(itemView);
            gridView=itemView.findViewById(R.id.gridview);
        }
    }
}
