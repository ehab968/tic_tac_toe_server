/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.group3.tic_tac_toe_shared;

import java.io.Serializable;

/**
 *
 * @author Ahmed Sayed
 */
public class Command implements Serializable{
    
    private CommandType type;
    private Object data;

    public Command(CommandType type) {
        this.type = type;
    }

    public Command(CommandType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public CommandType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
    
}


 