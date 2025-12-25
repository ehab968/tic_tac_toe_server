/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.group3.tic_tac_toe_shared;

import java.io.Serializable;

/**
 *
 * @author mahmo
 */
//class for each action the user take on the grid
public class GameMove implements Serializable {

    private int cellId;
    private char character;

    public GameMove(int cellId, char character) {
        this.cellId = cellId;
        this.character = character;
    }

    public int getCellId() {
        return cellId;
    }

    public char getCharacter() {
        return character;
    }
}
