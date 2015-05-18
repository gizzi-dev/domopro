/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomoPro;

import java.util.ArrayList;

/**
 *
 * @author Gianmarco
 */
public class Stanza extends Luogo {
    private String nome;    
    private Piano piano;
    
    public Stanza(String nome){
        this.nome=nome;
        risorse = new ArrayList<Risorsa>();
    }
    
    public static Stanza creaStanzaDefault(){
        return new Stanza("Stanza Default");
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome=nome;
    }
    
    public String toString(){
        String stringa="Nome Stanza: "+this.nome;
        return stringa;
    }
    
    public void setPiano(Piano piano){
        this.piano = piano;
    }
    public Piano getPiano(){
        return piano;
    }   
    
    public ArrayList<Luogo> getLuoghi(){
        ArrayList<Luogo> luoghi = new ArrayList<Luogo>();
        luoghi.add(this);
        return luoghi;
    }   

    @Override
    public ArrayList<Risorsa> getRisorse() {
        return this.risorse;
    }
    
    public void collectRisorseAccessibili(ArrayList<Risorsa> risorse)    {
        risorse.addAll(this.risorse);
        this.piano.collectRisorseAccessibili(risorse);
    }

    @Override
    public void addRisorsa(Risorsa risorsa) {
       risorse.add(risorsa);
    }

    @Override
    public void removeRisorsa(Risorsa risorsa) {
        risorse.remove(risorsa);
    }
}
