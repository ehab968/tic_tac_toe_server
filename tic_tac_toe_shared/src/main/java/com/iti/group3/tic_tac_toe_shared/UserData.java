/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iti.group3.tic_tac_toe_shared;

import java.io.Serializable;

/**
 *
 * @author mahmo
 */ 
public class UserData implements Serializable{
    private static int counter = 1;
    private  int id;
    private int userName;
    private int password;
    private int score;
    private int status;
    private int wins;
    private int losses;
    private int draws;


    public UserData(int userName, int password, int score, int status, int wins, int losses, int draws) {
        this.id = counter++;
        this.userName = userName;
        this.password = password;
        this.score = score;
        this.status = status;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }

    public static int getCounter() {
        return counter;
    }

    public int getId() {
        return id;
    }

    public int getUserName() {
        return userName;
    }

    public int getPassword() {
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

    
   
    
}