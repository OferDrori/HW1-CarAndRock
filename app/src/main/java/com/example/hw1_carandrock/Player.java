package com.example.hw1_carandrock;

import android.location.Location;

import androidx.annotation.NonNull;

public class Player implements Comparable<Player> {
    private int score;
    private String name;
    private Location location;

    public Player(int score , String name , Location location) {
        this.score = score;
        this.name = name;
        this.location=location;
    }

    @NonNull
    @Override
    public String toString() {
       return this.name+" "+this.getScore();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public int compareTo(Player player)
    {
        if(player == null) {
            return 0;
        }
        return player.getScore() - this.getScore();
    }
}
