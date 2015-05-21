/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.backyard;

import java.util.ArrayList;

/**
 *
 * @author Gianmarco
 */
public class Risorsa {    
    private String nome;
    private int limite;    
    private int limiteTotale;
    private int giorniRinnovo;
    private Collocazione collocazione;
    private ArrayList<Azione> utilizzataDa;
    
    
    Risorsa(String nome,Collocazione collocazione,int limite){
        this.nome = nome;
        this.collocazione = collocazione;
        this.limite = limite;
        this.utilizzataDa = new ArrayList<Azione>();
    }
    Risorsa(String nome,Collocazione collocazione,int limitetotale,int limite,int giorniRinnovo){       
        this(nome,collocazione,limite);
        this.limiteTotale = limiteTotale;
        this.giorniRinnovo = giorniRinnovo;
    }
    
    public String getNome(){
        return this.nome;
    }
    public boolean isUtilizzata(){
        return this.utilizzataDa.size() > 0;
    }
    
    public void addUtilizzo(Azione azione){
        this.utilizzataDa.add(azione);
    }
    
    public void removeUtilizzo(Azione azione){
        this.utilizzataDa.remove(azione);
    }
    
    public void setCollocazione(Collocazione collocazione){
        this.collocazione=collocazione;
    }
    public Collocazione getCollocazione(){
        return this.collocazione;
    }    
    
    public boolean aConsumo(){
        return giorniRinnovo>1;
    }

    public void setlimite(int limite) {
        this.limite=limite;
    }
    
    public void addAzione(Azione az){
        this.utilizzataDa.add(az);
    }
    
    public int getLimite(){
        return this.limite;
    }
    
    public int getGiorniRinnovo(){
        return this.giorniRinnovo;
    }
    
     public int getLimiteTot(){
        return this.limiteTotale;
    }

    public void setNome(String nome) {
        this.nome=nome;
    }

    public void setlimiteTot(int limitetot) {
        this.limiteTotale=limitetot;
    }

    public void setRinnovo(int rinnovo) {
        this.giorniRinnovo= rinnovo;
    }
    
    public boolean usata(){
        return this.utilizzataDa.size()!= 0;
    }
    
    public String toString(){
        return this.nome;
    }
   
}
