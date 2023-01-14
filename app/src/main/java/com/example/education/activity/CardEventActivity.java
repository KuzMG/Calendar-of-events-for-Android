package com.example.education.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.education.R;
import com.example.education.model.Event;

public class CardEventActivity extends AppCompatActivity {
    private Event event;
    private Button educationButton, testButton, closeButton;
    private TextView fullName, reference, date;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_event);
        initData();
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fullName.setText(event.getFullName());
        reference.setText(event.getReference());
        date.setText(event.getDate());
        image.setImageResource(event.getImage());
        Intent intent1 = new Intent(this, DetailEventActivity.class);
        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1.putExtra(Event.class.getSimpleName(), event);
                startActivity(intent1);
            }
        });
        Intent intent2 = new Intent(this, TestEventActivity.class);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent2.putExtra(Event.class.getSimpleName(), event);
                startActivity(intent2);
            }
        });


    }
    private void initData(){
        closeButton = findViewById(R.id.card_close);
        educationButton = findViewById(R.id.button_detail_event);
        fullName = findViewById(R.id.full_name);
        testButton = findViewById(R.id.button_test);
        reference = findViewById(R.id.reference);
        image = findViewById(R.id.image_card);
        date = findViewById(R.id.date);
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            event = arguments.getParcelable(Event.class.getSimpleName());
        }
    }
}