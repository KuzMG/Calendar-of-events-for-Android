package com.example.education.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.education.R;
import com.example.education.adapter.AdapterForPagerOfCalendar;
import com.example.education.adapter.AdapterForCalendar;
import com.example.education.adapter.AdapterForEvents;
import com.example.education.animation.SnowAnimationView;
import com.example.education.model.Event;
import com.example.education.model.Events;
import com.example.education.model.Test;
import com.example.education.animation.CalendarTransformer;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private SnowAnimationView snowAnimationView;
    private TextView mothYearText1;
    private TextView mothYearText2;
    private final String[] month = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
    private ViewPager2 viewPager2;
    private AdapterForPagerOfCalendar pageAdapter;
    private List<Events> events;
    private String[][] dateCalendar;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillingEvents();
        initData();
        mothYearText1.setText( month[LocalDate.now().getMonthValue()-1]);
        mothYearText2.setText(String.valueOf(LocalDate.now().getYear()));
        viewPager2.setAdapter(pageAdapter);
        viewPager2.setCurrentItem(LocalDate.now().getMonthValue()-1,false);
        viewPager2.setOffscreenPageLimit(12);
        viewPager2.setPageTransformer(new CalendarTransformer());
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                    mothYearText1.setText( month[position]);
            }
        });
    }
    private String[][] calendar(){
        String[][] calendar = new String[12][42];
        int a=0;
        int b=0;
        for (int i=1;i<=12;i++) {
            LocalDate localDate = LocalDate.of(LocalDate.now().getYear(),i,1);
            YearMonth yearMonth = YearMonth.from(localDate);
            LocalDate dateLast;
            if(localDate.getMonth().getValue()!=1) {
                dateLast = LocalDate.of(localDate.getYear(),
                        localDate.getMonth().getValue() - 1,
                        localDate.getDayOfMonth());
            }
            else{
                dateLast = LocalDate.of(localDate.getYear()-1, 12, 1);
            }
            YearMonth yearMonthLast = YearMonth.from(dateLast);

            int dayInMonthLength = yearMonth.lengthOfMonth();
            int dayInMonthLengthPast = yearMonthLast.lengthOfMonth();
            LocalDate firstOfMonth = localDate.withDayOfMonth(1);
            int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
            int g = 1;
            for (int j = 1; j <= 42; j++) {
                if (j < dayOfWeek || j >= dayInMonthLength + dayOfWeek) {
                    if (j < dayOfWeek) {
                        calendar[i - 1][j - 1] = String.valueOf(dayInMonthLengthPast - dayOfWeek + j + 1);
                    }
                    else {
                        calendar[i-1][j-1]=String.valueOf(g++);
                    }
                } else {
                    calendar[i-1][j-1]=String.valueOf(j - dayOfWeek + 1);
                }
            }
        }
        return calendar;
    }
    private void initData(){
        AdapterForPagerOfCalendar.Click click = new AdapterForPagerOfCalendar.Click() {
            @Override
            public void click(int page, int month, int day) {
                AdapterForPagerOfCalendar adapterFragmetCell = (AdapterForPagerOfCalendar) viewPager2.getAdapter();
                viewPager2.setCurrentItem(page);
                RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
                View gridView = recyclerView.getChildAt(month);
                GridView grid = gridView.findViewById(R.id.gridview);
                adapterFragmetCell.nextAdapter = (AdapterForCalendar) grid.getAdapter();
                assert adapterFragmetCell != null;
                View cell = grid.getChildAt(day);
                AdapterForCalendar ad = (AdapterForCalendar) grid.getAdapter();
                ad.selectItem(cell,"date");
            }
            @Override
            public void select(String text,int month) {
                AdapterForPagerOfCalendar adapterFragmetCell = (AdapterForPagerOfCalendar) viewPager2.getAdapter();
                RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
                View gridView = recyclerView.getChildAt(month);
                GridView grid = gridView.findViewById(R.id.gridview);
                //adapterFragmetCell.nextAdapter = (GridViewAdapter) grid.getAdapter();
                for (int i=0;i<grid.getChildCount();i++){
                    View cell = grid.getChildAt(i);
                    AdapterForCalendar ad = (AdapterForCalendar) grid.getAdapter();
                    TextView textCell = cell.findViewById(R.id.cell);
                    String thisText = (String) textCell.getText();
                    if(text.equals(thisText) && (Integer.valueOf(text)<20 && i>28)) {
                        ad.selectItem(cell,"date");
                        adapterFragmetCell.nextAdapter = (AdapterForCalendar) grid.getAdapter();
                    }
                    else if(text.equals(thisText) && (Integer.valueOf(text)>20 && i<7)){
                        ad.selectItem(cell,"date");
                        adapterFragmetCell.nextAdapter = (AdapterForCalendar) grid.getAdapter();
                    }
                }
            }
        };
        Intent intent = new Intent(this, CardEventActivity.class);
        AdapterForEvents.OnEventClickListener onEventClickListener = new AdapterForEvents.OnEventClickListener() {
            @Override
            public void onEventClick(Event event, int position) {
                intent.putExtra(event.getClass().getSimpleName(),event);
                startActivity(intent);
            }
        };
        viewPager2 = findViewById(R.id.page_calendar);
        dateCalendar=calendar();
        snowAnimationView = findViewById(R.id.animated_view);
        snowAnimationView.resume();
        pageAdapter = new AdapterForPagerOfCalendar(onEventClickListener, this,dateCalendar,findViewById(R.id.list), events, click);
        mothYearText1 = findViewById(R.id.date1);
        mothYearText2 = findViewById(R.id.date2);
    }
    private void fillingEvents(){
        String information = "Михаил, вот Ваша хронология за 2022 год\n" +
                "Вы получили это сообщение, поскольку Вы включили историю местоположений. Эта функция настраивается на уровне аккаунта Google и сохраняет в хронологии информацию о местах, которые Вы посетили.\n" +
                "На основе истории местоположений вы получаете персональные рекомендации, например о ресторанах, которые могут вам понравиться, а также удобных вариантах маршрутов. Посмотреть, изменить или удалить информацию о местах, в которых вы побывали, можно в хронологии.";
        events = new ArrayList<>();
        List<Event> event = new ArrayList<Event>();
        List<Test> tests =  List.of(new Test("ьыъ?", "да", List.of("Да","Нет"),1),
                new Test("ъюъ?", "да", List.of("Да","Нет","Нет","Нет"),1),
                new Test("ъуъ?", "да", List.of("Да","Нет","Нет","Нет"),1),
                new Test("ъёё?", "да", List.of("Да","Нет","Нет","Нет"),1),
                new Test("ььЬ?", "да", List.of("Да","Нет","Нет","Нет"),1),
                new Test("у?", "да", List.of("Да","Нет","Нет","Нет"),1),
                new Test("е?", "да", List.of("Да","Нет","Нет","Нет"),1),
                new Test("ю?", "да", List.of("Да","Нет","Нет","Нет"),1),
                new Test("ъ?", "да", List.of("Да","Нет","Нет","Нет"),1),
                new Test("ъъ?", "да", List.of("Да","Нет","Нет","Нет"),1));
        event.add(new Event(R.drawable.lk,"Зубенко Михаил Петрович","17 марта 1980 - 23 июня 2022","Великий мафиозник, борис бебра, лысый, красивая шляпа.",information, tests));
        event.add(new Event(R.drawable.lk,"Иванов Иван Иванович","12 марта 1971 - 23 февраля 2022","??????????",information, tests));
        events = List.of(new Events("2023-2-1", event),new Events("2023-1-31", event),new Events("2023-2-8", event));
    }
}