package com.example.education.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.education.R;
import com.example.education.model.Event;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailEventActivity extends AppCompatActivity {
    private Button buttonClose;
    private TextView information;
    private Event event;
    private ImageView image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        initData();
        button();

        information.setText(event.getInformation());
        collapsingToolbarLayout.setTitleCollapseMode(CollapsingToolbarLayout.TITLE_COLLAPSE_MODE_FADE);
        collapsingToolbarLayout.setTitle(event.getFullName());
        image.setImageResource(event.getImage());
    }

    private void button() {
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData(){
        buttonClose = findViewById(R.id.close_button);
        information = findViewById(R.id.textInformation);
        collapsingToolbarLayout =findViewById(R.id.collapsing_toolbar);
        image = findViewById(R.id.image_view);
                Bundle arguments = getIntent().getExtras();
        if(arguments!=null)
            event = arguments.getParcelable(Event.class.getSimpleName());
    }
}