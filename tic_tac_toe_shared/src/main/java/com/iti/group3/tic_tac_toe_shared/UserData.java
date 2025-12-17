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
    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
   public UserData(int id, String name){
        this.id = id;
        this.name = name;    
    }
    
    @Override
    public String toString(){
        return id +" -- "+"name: "+name;
    }
}