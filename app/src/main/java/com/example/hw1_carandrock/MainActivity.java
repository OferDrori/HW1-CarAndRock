package com.example.hw1_carandrock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable myRun;
    final static String KEY_SCORES = "KEY_SCORES";
    final int ROCK = 1;
    final int CAR = 2;
    final int COIN = 3;
    final int EMPTY = 0;
    private GridLayout main_LAY_raceMap;
    private LinearLayout main_LAY_life;
    private Button leftBtn;
    private Button rigthBtn;
    private ImageView[] lifeArr = new ImageView[3];
    private ImageView[][] imageArr = new ImageView[8][5];
    private int life = 3;
    private int carLocation = 1;
    private int[][] logicGame = new int[8][5];
    private TextView main_TXT_score;
    private int counterScore = 0;
    private int makeAspace = 0;
    private MediaPlayer coinSound;
    private MediaPlayer boomSound;
    private int speed;
    private MySharedPreferences msp;
    private Location myLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leftBtn = findViewById(R.id.leftBtn);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        rigthBtn = findViewById(R.id.rigthBtn);
        msp = new MySharedPreferences(this);
        rigthBtn.setOnClickListener(moveRight);
        leftBtn.setOnClickListener(moveLeft);
        main_TXT_score = findViewById(R.id.score);
        main_LAY_raceMap = findViewById(R.id.racemap);
        main_LAY_life = findViewById(R.id.main_LAY_life);
        coinSound = MediaPlayer.create(MainActivity.this, R.raw.coinsound);
        boomSound = MediaPlayer.create(MainActivity.this, R.raw.carcrash);
        int index = 0;
        Intent intent = getIntent();
        speed = intent.getExtras().getInt("speed");
        Log.i("speed", speed + " ");
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
        Log.i("dsfs", imageArr.length - 2 + " ");
        imageArr[imageArr.length - 2][carLocation].setImageResource(R.drawable.car);
        logicGame[logicGame.length - 2][carLocation] = CAR;
        loopFunc();
    }

    private void loopFunc() {

        myRun = new Runnable() {
            @Override
            public void run() {
                if (makeAspace % 2 == 0)
                    makeArandomRock();
                if (makeAspace % 5 == 0)
                    makeACoin();
                makeAspace++;
                counterScore++;
                main_TXT_score.setText("Score " + counterScore);
                int type = -1;
                if (life == 0) {
                    addPlayer();
                    return;
                } else {
                    for (int i = logicGame.length - 2; i >= 0; i--) {
                        for (int j = 0; j < logicGame[0].length; j++) {
                            if (logicGame[i][j] != EMPTY && logicGame[i][j] != CAR) {
                                type = logicGame[i][j];
                                imageArr[i][j].setImageResource(0);
                                logicGame[i][j] = EMPTY;
                                if (!isClash(i, j, type)) {
                                    moveObject(i, j, type);
                                }
                            }
                        }
                    }
                    clearLastRow();
//                    makeItFaster();
                }


                loopFunc();

            }
        };
        handler.postDelayed(myRun, 500);
    }

    private void makeItFaster() {
        if (speed > 200)
            speed = speed - 50;
    }

    void clearLastRow() {
        for (int j = 0; j < logicGame[0].length; j++) // clean the last row
        {
            if (logicGame[logicGame.length - 1][j] == COIN || logicGame[logicGame.length - 1][j] == ROCK) {
                imageArr[logicGame.length - 1][j].setImageResource(0);
                logicGame[logicGame.length - 1][j] = 0;

            }
        }

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

    private void makeACoin() {

        int max = imageArr[0].length - 1;
        int min = 0;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        imageArr[0][i1].setImageResource(R.drawable.coin);
        logicGame[0][i1] = 3; // 3 is simbol for coin
    }

    View.OnClickListener moveLeft = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imageArr[imageArr.length - 2][carLocation].setImageResource(0);
            logicGame[logicGame.length - 2][carLocation] = 0;
            if (carLocation > 0)
                carLocation--;
            imageArr[imageArr.length - 2][carLocation].setImageResource(R.drawable.car);
            logicGame[logicGame.length - 2][carLocation] = 2;

        }
    };
    View.OnClickListener moveRight = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            imageArr[imageArr.length - 2][carLocation].setImageResource(0);
            logicGame[logicGame.length - 2][carLocation] = 0;
            if (carLocation < imageArr[0].length - 1)
                carLocation++;
            imageArr[imageArr.length - 2][carLocation].setImageResource(R.drawable.car);
            logicGame[logicGame.length - 2][carLocation] = CAR;

        }
    };

    boolean isClash(int row, int colm, int type) {
        if (logicGame[row + 1][colm] == CAR) {
            imageArr[row + 1][colm].setImageResource(R.drawable.car);
            if (type == ROCK) {//if is rock
                life--;
                lifeArr[life].setImageResource(0);
                boomSound.start();
                Toast.makeText(this, "boom!", Toast.LENGTH_SHORT).show();
                MyFeedbacks.vibrate(getApplicationContext(), 300);
            } else if (type == COIN) {
                counterScore += 100;
                coinSound.start();
                Toast.makeText(this, "coin", Toast.LENGTH_SHORT).show();
            }


            Log.i("dsa", "" + life);
            return true;

        }
        return false;
    }

    void changescreenGameover() {

        Intent next = new Intent(getApplicationContext(), ScoreActivity.class);
        next.putExtra("score", counterScore);
        startActivity(next);
        finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(myRun);
    }

    protected void onResume() {
        super.onResume();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                myLocation = location;
            }
        });
    }

    /**
     * When the player returns to the activity it starts from the place he left it
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(myRun, speed);
    }

    void moveObject ( int i, int j, int type){
        if (type == 1) {
            imageArr[i + 1][j].setImageResource(R.drawable.rock);
        } else if (type == 3)
            imageArr[i + 1][j].setImageResource(R.drawable.coin);
        logicGame[i + 1][j] = type;
        Log.i("info", "the" + type + "moved");
    }

    private void addPlayer()
    {
        final EditText name = new EditText(this);
        final Player player = new Player(counterScore, " ", myLocation);
        new AlertDialog.Builder(this).setTitle("Game Over!")
                .setMessage("Add your name")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        player.setName(name.getText().toString());
                        saveInformation(player);
                        changescreenGameover();//go to next activity
                    }


                }).setCancelable(false).setView(name).create().show();
    }

    private void saveInformation (Player playerInfo)
    {
        ArrayList<Player> topPlayers;
        Gson gson = new Gson();
        topPlayers = gson.fromJson(msp.getString(KEY_SCORES, ""), new TypeToken<List<Player>>() {
        }.getType());
        if (topPlayers == null)
            topPlayers = new ArrayList<>();
        if (topPlayers.size() >= 10) {
            if (topPlayers.get(9).getScore() < playerInfo.getScore()) {
                topPlayers.remove(9);
                topPlayers.add(playerInfo);
            }

        } else {
            topPlayers.add(playerInfo);
        }
        Collections.sort(topPlayers);
        msp.putString(KEY_SCORES, gson.toJson(topPlayers));
    }
}
