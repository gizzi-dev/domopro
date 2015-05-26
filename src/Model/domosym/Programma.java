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
    private int id;
    private boolean salvato;
    protected ArrayList<ComandoProgramma> comandi;
    private Simulazione sim;
    
    public Programma(String nome){
        this.nome = nome;
        comandi = new ArrayList<ComandoProgramma>();
        salvato = false;
        this.id =-1;
    }
    
    public Simulazione getSimulazione(){
        return this.sim;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setNome(String nome){
        this.nome =nome;
    }
    
    public void setSalvato(boolean bol){
        this.salvato =bol;
    }
    
    public boolean getSalvato(){
        return this.salvato;
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
    
    public abstract String buildQuerySalvaInsert();
    
    public void salvaProgramma(){
        if(!this.isSalvato()){
            String query="";
            if(id!=-1){
                query = this.buildQuerySalva();
                DomoSymApplicationController.appCtrl.getDBController().executeUpdate(query);
            }
            else{
                query = this.buildQuerySalvaInsert();
                int id = DomoSymApplicationController.appCtrl.getDBController().executeInsert(query);
                this.id = id;                
            }
            System.out.println(query);
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
