/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.domosym;

import java.util.ArrayList;

/**
 *
 * @author Gianmarco
 */
public class ProgrammaGenerico extends Programma implements AzioneComando{
    private ArrayList<Comando> usatoDa;
    
    public ProgrammaGenerico(String nome) {
        super(nome);
        usatoDa = new ArrayList<>();
    }

    
    public  boolean isGenerico(){
        return true;
    }
    
    @Override
    public boolean usato(){
        return !this.usatoDa.isEmpty();
    }

    @Override
    public void rimuoviDaComando(Comando com) {
        this.usatoDa.remove(com);
    }

    @Override
    public String buildQuerySalva() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean setDurata(int durata){
        return false;
    }
    
    public String toString(){        
        return this.getNome();                
    }
    
    public int getDurata(){
        return 0;
    }
    
}
