package com.example.hw1_carandrock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private GridLayout main_LAY_raceMap;
    private LinearLayout main_LAY_life;
    private Button leftBtn;
    private Button rigthBtn;
    private ImageView[] lifeArr = new ImageView[3];
    private ImageView[][] imageArr = new ImageView[8][3];
    private int life = 3;
    private int carLocation = 1;
    private int[][] logicGame = new int[8][3];
    private TextView main_TXT_score;
    private int counterScore=0;
    private int makeAspaceRock=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leftBtn = findViewById(R.id.leftBtn);
        rigthBtn = findViewById(R.id.rigthBtn);
        rigthBtn.setOnClickListener(moveRight);
        leftBtn.setOnClickListener(moveLeft);
        main_TXT_score=findViewById(R.id.score);
        main_LAY_raceMap = findViewById(R.id.racemap);
        main_LAY_life = findViewById(R.id.main_LAY_life);
        int index = 0;
        for (int i = 0; i < imageArr.length; i++) {
            for (int j = 0; j < imageArr[0].length; j++) {
                imageArr[i][j] = (ImageView) main_LAY_raceMap.getChildAt(index);
                index++;
            }
        }
        //Hearts array initiation
        for (int i = 0; i < lifeArr.length; i++) {
            lifeArr[i] = (ImageView) main_LAY_life.getChildAt(i);
        }
        clearLggicGame();
        imageArr[imageArr.length - 2][carLocation].setImageResource(R.drawable.car);
        logicGame[logicGame.length - 2][carLocation]=2;//2 prisent car
        makeArandomRock();
        loopFunc();
    }
    private void loopFunc() {
        final Handler handler = new Handler();
        Runnable myRun = new Runnable() {
            @Override
            public void run() {
                if(makeAspaceRock%2==0)
                     makeArandomRock();
                makeAspaceRock++;
                counterScore++;
                main_TXT_score.setText("Score " + counterScore);
                if (life == 0)
                {
                    changescreenGameover();
                    return;
                }
                else {
                    for (int i = logicGame.length - 2; i >= 0; i--) {
                        for (int j = 0; j < logicGame[0].length; j++) {
                            if (logicGame[i][j] == 1) {
                                imageArr[i][j].setImageResource(0);
                                logicGame[i][j] = 0;
                                if(isClash(i,j)==0)
                                {
                                    imageArr[i + 1][j].setImageResource(R.drawable.rock);
                                    logicGame[i + 1][j] = 1;//move the rock
                                    Log.i("info", "the rock moved");
                                }
                            }
                        }
                    }
                    for (int j = 0; j < logicGame[0].length; j++) {
                        if (logicGame[logicGame.length - 1][j] == 1) {
                            Log.i("info", "erase");
                            imageArr[logicGame.length - 1][j].setImageResource(0);
                            logicGame[logicGame.length - 1][j] = 0;

                        }
                    }
                }


                loopFunc();

            }
        };
        handler.postDelayed(myRun, 200);
    }

    private void clearLggicGame() {
        for (int i = 0; i < logicGame.length; i++) {
            for (int j = 0; j < logicGame[0].length; j++) {
                logicGame[i][j] = 0;
            }
        }
    }

    private void makeArandomRock() {

        int max = imageArr[0].length - 1;
        int min = 0;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        imageArr[0][i1].setImageResource(R.drawable.rock);
        logicGame[0][i1] = 1; // 1 is simbol for rock
    }

    View.OnClickListener moveLeft = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imageArr[imageArr.length - 2][carLocation].setImageResource(0);
            logicGame[logicGame.length - 2][carLocation]=0;
            if (carLocation > 0)
                carLocation--;
            imageArr[imageArr.length - 2][carLocation].setImageResource(R.drawable.car);
            logicGame[logicGame.length - 2][carLocation]=2;

        }
    };
    View.OnClickListener moveRight = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            imageArr[imageArr.length - 2][carLocation].setImageResource(0);
            logicGame[logicGame.length - 2][carLocation]=0;
            if (carLocation < imageArr[0].length - 1)
                carLocation++;
            imageArr[imageArr.length - 2][carLocation].setImageResource(R.drawable.car);
            logicGame[logicGame.length - 2][carLocation]=2;

        }
    };

    int isClash(int row, int colm) {
        if (logicGame[row + 1][colm] == 2) {
            imageArr[row + 1][colm].setImageResource(R.drawable.car);
            life--;
            lifeArr[life].setImageResource(0);
            MyFeedbacks.vibrate(getApplicationContext(), 250);
            Log.i("dsa", "" + life);
            return 1;

        }
        return 0;
    }
    void changescreenGameover(){
        Intent next= new Intent(getApplicationContext(), scoreActivity.class);
        next.putExtra("score",counterScore);
        startActivity(next);
        finish();

    }


}
