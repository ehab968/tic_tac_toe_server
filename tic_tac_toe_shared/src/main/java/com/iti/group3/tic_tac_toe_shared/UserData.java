/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.iti.group3.tic_tac_toe_shared;

import java.io.Serializable;

/**
 *
 * @author mahmo
 */
public class UserData implements Serializable {

    private String userName;
    private String password;
    private int score;
    private int status;
    private int wins;
    private int losses;
    private int draws;

    public UserData(String userName) {
        this.userName = userName;
        this.password = "";
        this.score = 0;
        this.status = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    public UserData(String userName, String password, int score, int status, int wins, int losses, int draws) {
        this.userName = userName;
        this.password = password;
        this.score = score;
        this.status = status;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    @Override
    public String toString() {
        return "userName: " + userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public int getStatus() {
        return status;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
