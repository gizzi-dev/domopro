/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomoPro;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Gianmarco
 */
public class Piano extends Luogo implements Importabile{
    private int livello;
    private String nome;
    private ArrayList<Stanza> stanze;
    private Alloggio allogio;
    
    public Piano(String nome,int liv){
        this.nome=nome;
        this.livello = liv;
        stanze = new ArrayList<Stanza>();
        risorse = new ArrayList<Risorsa>();
        Stanza stz = Stanza.creaStanzaDefault();
        stz.setPiano(this);
        stanze.add(stz);
    }
    
    public Piano clone(){
        try {
            return (Piano)super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public int getLivello(){
        return livello;
    }
    
    public ArrayList<Stanza> getStanze(){
        return stanze;
    }
    
    public void deleteStanza(Stanza stanza){
        this.stanze.remove(stanza);
    }
    public void addStanza(Stanza stanza){
        this.stanze.add(stanza);
        stanza.setPiano(this);
    }
    
    public void setLivello(int liv){
        livello=liv;
    }
    
    public void setAlloggio(Alloggio alloggio){
        this.allogio=alloggio;
    }
    
    public void setNome(String nome){
        this.nome=nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public boolean hasStanza(Stanza stanza){
        for(Stanza stz: stanze){
            if(stanza.getNome().equals(stz.getNome())) return true;
        }
        return false;
    }
    
    public Stanza getStanza(String nome){
        for(Stanza stz: stanze){
            if(stz.getNome().equals(nome)) return stz;
        }
        return null;
    }
    
    public ArrayList<Luogo> getLuoghi(){
        ArrayList<Luogo> luoghi = new ArrayList<Luogo>();
        luoghi.add(this);
        for(Stanza stanza:stanze){
            luoghi.addAll(stanza.getLuoghi());           
        }
        return luoghi;
    } 
    
    public void collectRisorseAccessibili(ArrayList<Risorsa> risorse) {
        risorse.addAll(this.risorse);
        this.allogio.collectRisorseAccessibili(risorse);
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
}
