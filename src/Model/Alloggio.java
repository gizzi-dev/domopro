/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Gianmarco
 */
public class Alloggio extends Luogo implements Contesto {    
    private ArrayList<Piano> piani;    
    //private ArrayList<Risorsa> risorseFornite;//elenco di oggetti Risorsa che può contenere: tutte le risorse associate alla planimetria di s e ai dispositivi di s
    
    public Alloggio(){
        piani = new ArrayList<Piano>(); 
        this.risorse = new ArrayList<Risorsa>();        
        Piano unPiano = new Piano("unPiano",0);
        piani.add(unPiano);
    }
        
    public boolean addPiano(Piano piano){        
        piani.add(piano);
        piano.setAlloggio(this);
        return true;
    }
    
    public void deletePiano(Piano piano){
        piani.remove(piano);
    }
    
    public ArrayList<Piano> getPiani(){
        return piani;
    }

    /*
    raccolgo tutte le risorse dell'alloggio e gli aggiungo anche quelle fornite dai dispositivi?! o solo quelle dei dispositivi?
    */
    @Override
    public ArrayList<Risorsa> getRisorseFornite() {
       ArrayList<Risorsa> ris = new ArrayList<Risorsa>();
       ris.addAll(this.risorse);
       for(Piano piano:this.piani){
           ris.addAll(piano.getRisorse());
           for(Stanza stanza: piano.getStanze()){
               ris.addAll(stanza.getRisorse());
           }
       }
       return ris;
    }
    
    public ArrayList<Luogo> getLuoghi(){
        ArrayList<Luogo> luoghi = new ArrayList<Luogo>();     
        luoghi.add(this);
        for(Piano piano: piani){
            luoghi.addAll(piano.getLuoghi());
        }
        return luoghi;
    }    
    
    @Override
    public void collectRisorseAccessibili(ArrayList<Risorsa> risorse){
        risorse.addAll(this.risorse);        
    }
    @Override
    public ArrayList<Risorsa> getRisorse() {
        return this.risorse;
    }

    @Override
    public void addRisorsa(Risorsa risorsa) {
        this.risorse.add(risorsa); 
    }

    @Override
    public void removeRisorsa(Risorsa risorsa) {        
        this.risorse.remove(risorsa);        
    }
     
    public String toString(){
        return "Alloggio composto da "+this.piani.size();
    }

    
    public String getNome() {
        return "Alloggio";
    }
}