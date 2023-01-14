package com.example.education.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.education.R;
import com.example.education.adapter.AdapterForTest;
import com.example.education.animation.SnowAnimationView;
import com.example.education.fragment.FragmentForTest;
import com.example.education.model.Event;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TestEventActivity extends AppCompatActivity {
    private Event event;
    private int numberPages=0;
    private int numberRightAnswer=0;
    private Button button;
    private Button textButton;
    private TextView textFullName;
    private LinearLayout linearLayout;
    private ConstraintLayout.LayoutParams layoutParams;
    private Intent intentResult;
    private ViewPager2 pager;
    private FragmentForTest.Click click;
    private AdapterForTest pageAdapter;
    private TabLayout tabLayout;
    private TabLayoutMediator tabLayoutMediator;
    private ConstraintLayout constraintLayout;
    private SnowAnimationView snowAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event);

        initData();
        button();
        snowAnimationView.resume();
        textFullName.setText(event.getFullName());

        pager.setOffscreenPageLimit(event.getTests().size());
        pager.setAdapter(pageAdapter);
        tabLayoutMediator.attach();
    }

    private void button() {
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button.setBackgroundResource(R.drawable.button_test);
        button.setText("Посмотреть результаты");
        button.setLayoutParams(layoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentResult.putExtra("numberPage",numberPages);
                intentResult.putExtra("wrongAnswer", numberPages-numberRightAnswer);
                intentResult.putExtra(Event.class.getSimpleName(), event);
                intentResult.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intentResult);
            }
        });
    }

    private void initData(){
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null)
            event = arguments.getParcelable(Event.class.getSimpleName());
        intentResult = new Intent(this, ResultEventActivity.class);
        textButton = findViewById(R.id.test_close);
        textFullName = findViewById(R.id.test_full_name);
        tabLayout = findViewById(R.id.tab_layout);
        pager = findViewById(R.id.pager);
        linearLayout = findViewById(R.id.linear_layout);
        constraintLayout = findViewById(R.id.test_constraint_layout);
        snowAnimationView = findViewById(R.id.test_snow_animation_view);
        button = new Button(this);
        layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;;
        layoutParams.bottomMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        layoutParams.leftMargin =(int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        click = new FragmentForTest.Click() {
            @Override
            public void click(int page) {
                pager.setCurrentItem(pager.getCurrentItem()+1);
            }

            @Override
            public void addNumberPage(boolean trueOrFalse) {
                TabLayout.Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
                numberPages++;
                if(trueOrFalse) {
                    numberRightAnswer++;
                    tab.view.setBackgroundResource(R.drawable.tab_background_true);
                }
                else{
                    tab.view.setBackgroundResource(R.drawable.tab_background_false);
                }
                if(numberPages==event.getTests().size())
                    constraintLayout.addView(button);
            }
            @Override
            public int describeContents() {
                return 0;
            }
            @Override
            public void writeToParcel(Parcel parcel, int i) {

            }
        };
        pageAdapter = new AdapterForTest(this,event, click);

        tabLayoutMediator= new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                tab.setText(String.valueOf(position + 1));
            }
        });
    }
}