package com.example.hw1_carandrock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    private TextView scoreActivity_TXT_score;
    private RelativeLayout score_relative_layout;
    private Button playAgain;
    private Button btnMap;
    private ListView lstView;
    final String KEY_SCORES = "KEY_SCORES";
    private MySharedPreferences msp;
    private FragmentTopScoreMap fragment_a;
    FragmentTransaction transaction = null;
    Fragment frameLayout;
    private boolean changeView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scoreActivity_TXT_score = findViewById(R.id.score);
        Intent intent = getIntent();
        scoreActivity_TXT_score.setText("your score is " + intent.getExtras().getInt("score"));
        msp = new MySharedPreferences(this);
        playAgain = findViewById(R.id.btn_score_startAgain);
        btnMap = findViewById(R.id.btn_score_Map);
        lstView = findViewById(R.id.listView_score_lst);
        showtopTenList();
        playAgain.setOnClickListener(goToMenuActivity);
        btnMap.setOnClickListener(showLocation);
        fragment_a = new FragmentTopScoreMap(this);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.top_score, fragment_a);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragment_a);
        transaction.commit();


    }

    private void showtopTenList() {
        ArrayList<Player> topPlayers;
        Gson gson = new Gson();
        topPlayers = gson.fromJson(msp.getString(KEY_SCORES, ""), new TypeToken<List<Player>>() {
        }.getType());
        ArrayList<String> topPlayersStr = new ArrayList<>();
        for (int i = 0; i < topPlayers.size(); i++) {
            topPlayersStr.add(topPlayers.get(i).toString());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topPlayersStr);
        lstView.setAdapter(arrayAdapter);

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(), "Hello ", Toast.LENGTH_LONG).show();

            }
        });
    }

    View.OnClickListener goToMenuActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent next = new Intent(getApplicationContext(), menuActivity.class);
            startActivity(next);
            finish();

        }
    };

    View.OnClickListener showLocation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (changeView == false)
                showA();
            else if (changeView == true)
                hideA();
        }
    };

    private void showA() {
        changeView = true;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().show(fragment_a).commit();

        score_relative_layout.setVisibility(View.INVISIBLE);

    }

    private void hideA() {
        changeView = false;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().hide(fragment_a).commit();
        score_relative_layout.setVisibility(View.VISIBLE);

    }

}


