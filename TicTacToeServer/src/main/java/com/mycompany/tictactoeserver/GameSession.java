/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoeserver;

/**
 *
 * @author ehab
 */
public class GameSession {

    private UserStreamSocket playerX;
    private UserStreamSocket playerO;

    public GameSession(UserStreamSocket p1, UserStreamSocket p2) {
        this.playerX = p1;
        this.playerO = p2;
    }

    public boolean contains(UserStreamSocket socket) {
        return socket == playerX || socket == playerO;
    }

    public UserStreamSocket getOpponent(UserStreamSocket sender) {
        return sender == playerX ? playerO : playerX;
    }
}
