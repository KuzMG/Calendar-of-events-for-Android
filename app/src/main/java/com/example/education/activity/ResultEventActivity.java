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

public class ResultEventActivity extends AppCompatActivity {
    private Intent intentDetailEvent;
    private Intent intentCardEvent;
    private Intent intentTest;
    private Intent intentMainActivity;
    private Button closeButton;
    private Button lookMistakesButton;
    private Button repeatButton;
    private TextView numberRightAnswer;
    private TextView notice;
    private int wrongAnswer;
    private String mistakes;
    private String questions;
    private Event event;
    private TextView fullName;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initData();
        button();

        intentMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intentCardEvent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intentTest.putExtra(Event.class.getSimpleName(),event);
        intentDetailEvent.putExtra(Event.class.getSimpleName(),event);

        String numberRightAnswerText = mistakes+", "+ questions;
        numberRightAnswer.setText(numberRightAnswerText);
        repeatButton.setText("перерешать");
        lookMistakesButton.setText("текст");
        fullName.setText(event.getFullName());

        if(wrongAnswer==0){
            imageView.setImageResource(R.drawable.salute);
            notice.setText("Поздравляю!");
            numberRightAnswer.setText("нет ошибок");
            lookMistakesButton.setText("вернуться к календарю");
            lookMistakesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intentMainActivity);
                }
            });
        }
        else if(wrongAnswer<=5){
            imageView.setImageResource(R.drawable.laik);
            notice.setText("Молодец, почти получилось");
        }
        else{
            imageView.setImageResource(R.drawable.ok);
            notice.setText("Прочитай текст поподробнее");
        }
    }

    private void initData(){
        closeButton = findViewById(R.id.result_close_button);
        lookMistakesButton = findViewById(R.id.mistakes);
        repeatButton = findViewById(R.id.repeat);
        numberRightAnswer = findViewById(R.id.number_of_mistakes);
        notice = findViewById(R.id.notice);
        fullName = findViewById(R.id.result_full_name);
        imageView= findViewById(R.id.result_image_view);

        Bundle arguments = getIntent().getExtras();
        event = arguments.getParcelable(Event.class.getSimpleName());

        wrongAnswer = (int) arguments.get("wrongAnswer");
        int numberPage = (int) arguments.get("numberPage");
        mistakes = getResources().getQuantityString(R.plurals.mistakes, wrongAnswer, wrongAnswer);
        questions = getResources().getQuantityString(R.plurals.questions, numberPage, numberPage);

        intentDetailEvent = new Intent(this, DetailEventActivity.class);
        intentCardEvent = new Intent(this, CardEventActivity.class);
        intentTest = new Intent(this, TestEventActivity.class);
        intentMainActivity = new Intent(this, MainActivity.class);
    }

    private void button(){
        lookMistakesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCardEvent);
                startActivity(intentDetailEvent);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCardEvent);
                startActivity(intentTest);
            }
        });
    }
}