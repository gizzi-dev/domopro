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
public abstract class Programma {
    private String nome;
    private boolean salvato;
    protected ArrayList<ComandoProgramma> comandi;
    private Simulazione sim;
    
    public Programma(String nome){
        this.nome = nome;
        comandi = new ArrayList<ComandoProgramma>();
    }
    
    public Simulazione getSimulazione(){
        return this.sim;
    }
    
    public void setNome(String nome){
        this.nome =nome;
    }
    
    public void setSalvato(boolean bol){
        this.salvato =bol;
    }
    
    public void setSimulazione(Simulazione sim){
        this.sim =sim;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public ArrayList<ComandoProgramma> getComandi(){
        return this.comandi;
    }
    
    public boolean isSalvato(){
        return this.salvato;
    }
    
    public abstract boolean usato();
    
    public abstract String buildQuerySalva();
    
    public void salvaProgramma(){
        if(!this.isSalvato()){
            String query = this.buildQuerySalva();
            DomoSymApplicationController.appCtrl.getDBController().executeUpdate(query);
            for(ComandoProgramma c: this.comandi){
                c.salvaComando();
            }
        }
        this.setSalvato(true);
    }
    
    public void elimina(){
        for(ComandoProgramma c: this.comandi){
            c.elimina();            
        }
        String query = this.buildQueryEliminaProgramma();
        //DomoSymApplicationController.appCtrl.getDBController().executeUpdate(query);
        this.setSalvato(true);
    }
    
    public void eliminaComando(ComandoProgramma com){
        this.comandi.remove(com);
        com.elimina();
        setSalvato(false);
    }
    
    public void aggiungiComando(ComandoProgramma c){
        this.comandi.add(c);
    }
    
    public abstract boolean isGenerico();

    private String buildQueryEliminaProgramma() {
        return "";
    }
    
}
