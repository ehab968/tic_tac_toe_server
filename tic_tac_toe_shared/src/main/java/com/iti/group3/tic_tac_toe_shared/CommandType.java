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
public enum CommandType implements Serializable {
    REGISTER,
    LOGIN,
    Invalid_Username_or_password,
    REGISTER_SUCCESS,
    REGISTER_FAILED,
    USERNAME_EXISTS,
    LOGIN_SUCCESS,
    LOGIN_FAILED,
    START_GAME,
    MOVE,
    GAME_OVER,
    LOGOUT
}