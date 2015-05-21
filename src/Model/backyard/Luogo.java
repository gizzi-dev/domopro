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
    public abstract void collectRisorseAccessibili(ArrayList<Risorsa> risorse);
    
    public abstract ArrayList<Luogo> getLuoghi();     
    
    public abstract ArrayList<Risorsa> getRisorse();
    
    public abstract String getNome();
    
    
    
    
    
}
