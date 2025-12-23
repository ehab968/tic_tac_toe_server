/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.group3.tic_tac_toe_shared;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author mahmo
 */
public class GameData implements Serializable{

    String id;
    public UserData playerX;
    public UserData playerO;
    public UserData winner;
    public LocalDateTime date;

    public GameData(String id, UserData playerX, UserData playetO, UserData winner) {
        this.id = id;
        this.playerX = playerX;
        this.playerO = playetO;
        this.winner = winner;
    }

    public GameData(String id, UserData playerX, UserData playerO) {
        this.id = id;
        this.playerX = playerX;
        this.playerO = playerO;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setWinner(UserData player) {
        winner = player;
    }
}
