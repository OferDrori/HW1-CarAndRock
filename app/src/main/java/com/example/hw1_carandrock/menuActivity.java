package com.example.hw1_carandrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class menuActivity extends AppCompatActivity {
    private Button btnStart;
    private ImageView menu_IMG_back;
    private final int FAST_SPEED = 500;
    private final int SLOW_SPEED = 700;
    private final String SPEED_KEY = "SPEED_KEY";
    private int speed;
    private RadioGroup menu_RDG_speed, menu_RDG_control;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu_IMG_back = findViewById(R.id.menu_IMG_back);
        btnStart = findViewById(R.id.menu_BTN_start);
        menu_RDG_speed = findViewById(R.id.menu_RDG_speed);
        menu_RDG_control = findViewById(R.id.menu_RDG_control);
        btnStart.setOnClickListener(playedFast);

        //
        menu_RDG_control.check(R.id.menu_RDB_button);
        menu_RDG_speed.check(R.id.menu_RDB_slow);


        //RadioGruou listener
        menu_RDG_speed.setOnCheckedChangeListener(speedConfiguration);
        menu_RDG_control.setOnCheckedChangeListener(controlConfiguration);
    }

    RadioGroup.OnCheckedChangeListener speedConfiguration = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.menu_RDB_fast:
                    speed = FAST_SPEED;
                    // do operations specific to this selection
                    break;
                case R.id.menu_RDB_slow:
                    speed = SLOW_SPEED;
                    // do operations specific to this selection
                    break;
            }
        }
    };


    RadioGroup.OnCheckedChangeListener controlConfiguration = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId){
                case R.id.menu_RDB_button:
                    // do operations specific to this selection
                    break;
                case R.id.menu_RDB_sensor:
                    // do operations specific to this selection
                    break;
            }
        }
    };
    View.OnClickListener playedFast = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent next = new Intent(getApplicationContext(), MainActivity.class);
            next.putExtra("speed", speed);
            startActivity(next);
        }
    };



}