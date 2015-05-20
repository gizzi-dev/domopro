/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.backyard;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Gianmarco
 */
public abstract class Luogo implements Collocazione{
    public ArrayList<Risorsa> risorse;
    
    @Override
    public void collectRisorseAccessibili(ArrayList<Risorsa> risorse){
        
    }
    
    public ArrayList<Luogo> getLuoghi(){
        return null;
    }
    
    public ArrayList<Risorsa> getRisorse(){
        return null;
    }
    
    public String getNome(){
        return "";
    }
    
    
    
    
}
