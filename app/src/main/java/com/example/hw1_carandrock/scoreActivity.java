package com.example.hw1_carandrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class scoreActivity extends AppCompatActivity {

    private TextView scoreActivity_TXT_score;
    private Button playAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scoreActivity_TXT_score=findViewById(R.id.score);
        Intent intent=getIntent();
        scoreActivity_TXT_score.setText("your score is "+ intent.getExtras().getInt("score"));

        playAgain = findViewById(R.id.btn_score_startAgain);
        playAgain.setOnClickListener(goToMenuActivity);

    }
    View.OnClickListener goToMenuActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent next= new Intent(getApplicationContext(), menuActivity.class);
            startActivity(next);
            finish();

        }
    };


}

