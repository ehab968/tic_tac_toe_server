/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iti.group3.tic_tac_toe_shared;

import java.io.Serializable;

/**
 *
 * @author mahmo
 */ 
public class UserDate implements Serializable{
    int id;
    String name;
    
   public UserDate(int id, String name){
        this.id = id;
        this.name = name;    
    }
    
    @Override
    public String toString(){
        return id +" -- "+"name: "+name;
    }
}