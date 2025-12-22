/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.group3.tic_tac_toe_shared;

import java.io.Serializable;

/**
 *
 * @author COMPUMARTS
 */
public class Response<T> implements Serializable {

    private boolean success;
    private ResponseType message;
    private T data;

    public Response(boolean success, ResponseType message, T data) {
        this.success = success;
        this.message = message;
        this.data = (T) data;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResponseType getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "success: " + success + " -- type: " + message + " -- data: " + data;
    }
}
