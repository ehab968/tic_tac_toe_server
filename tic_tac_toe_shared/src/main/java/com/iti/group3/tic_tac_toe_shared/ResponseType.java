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
public enum ResponseType implements Serializable {
  Invalid_Username_or_password,
    REGISTER_SUCCESS,
    REGISTER_FAILED,
    USERNAME_EXISTS,
    LOGIN_SUCCESS,
    LOGIN_FAILED,
    NO_ONLINEUSERS,
    GET_ONLINE_USERS_SUCCESS,
}
