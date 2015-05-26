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
    private int durata;
    
    public ProgrammaGenerico(String nome) {
        super(nome);
        usatoDa = new ArrayList<>();
        durata = 0;
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
        return "UPDATE programma SET nome='"+this.getNome()+"',idSimulazione='"+DomoSymApplicationController.appCtrl.getSimCorrente().getId()+"'"
                + ",specifico='0' WHERE id="+this.getId();
    }
    
    public String buildQuerySalvaInsert(){
        return "INSERT INTO programma(nome,idSimulazione,specifico)"
                + " VALUES ('"+this.getNome()+"','"+DomoSymApplicationController.appCtrl.getSimCorrente().getId()+"','0')";
    }
    
    public void setDurata(int durata){
        this.durata = durata;
    }
    
    public String toString(){        
        return this.getNome();                
    }
    
    public int getDurata(){
        return this.durata;
    }
        
    
}
