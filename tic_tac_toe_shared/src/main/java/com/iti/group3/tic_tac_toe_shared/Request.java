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
public class Request implements Serializable{
    
    private RequestType type;
    private Serializable data;

    public Request(RequestType type) {
        this.type = type;
    }

    public Request(RequestType type, Serializable data) {
        this.type = type;
        this.data = data;
    }

    public RequestType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "type: "+ type+ " -- data: "+data;
    }
    
    
    
}


 