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
public class Risorsa {    
    private String nome;
    private int limite;    
    private int limiteTotale;
    private int giorniRinnovo;
    private Collocazione collocazione;
    private ArrayList<Azione> utilizzataDa;
    
    
    Risorsa(String nome,Collocazione collocazione,int limitetotale){
        this.nome = nome;
        this.collocazione = collocazione;
        this.limiteTotale = limitetotale;
        this.utilizzataDa = new ArrayList<Azione>();
    }
    Risorsa(String nome,Collocazione collocazione,int limitetotale,int limite,int giorniRinnovo){       
        this(nome,collocazione,limitetotale);
        this.limite = limite;
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
        this.nome=nome;
    }

    public void setRinnovo(int rinnovo) {
        this.giorniRinnovo= rinnovo;
    }
   
}
