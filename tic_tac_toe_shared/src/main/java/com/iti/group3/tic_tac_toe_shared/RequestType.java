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
public enum RequestType implements Serializable {
    REGISTER,
    LOGIN,
    LOGOUT,
    START_GAME,
    MOVE,
    GAME_OVER,
    GetOnlineUsers,
    GET_LEADER_BOARD,
    INVITE_USER,
    ACCEPT_INVITE,
    REJECT_INVITE,
    SET_USER,

}
