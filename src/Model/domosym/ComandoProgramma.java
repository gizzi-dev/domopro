/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.domosym;

/**
 *
 * @author Gianmarco
 */
public class ComandoProgramma extends Comando{
    private String nome;
    private Programma programma;
    private boolean salvato;
    private CondizioneAttivazione cond;

    public ComandoProgramma(String nome){        
        this.nome = nome;
    }
    
 public String getNome(){
        return this.nome;
    }
    
    public void setProgramma(Programma p){
        this.programma = p;
    }
    
    public Programma getProgramma(){
        return this.programma;
    }   
      
    public void salvaComando() {
        //se programma = null non lo salvo
    }
    
    public void elimina(){
        this.programma = null;
        if(this.getAzioneComando() != null)
            this.getAzioneComando().rimuoviDaComando(this);
        String query = this.buildQueryEliminaComando();
        //DomoSymApplicationController.appCtrl.getDBController().executeUpdate(query);
        setSalvato(true);
    }
    
    public void setSalvato(boolean b){
        this.salvato = b;
    }
    
    public void setCondizione(CondizioneAttivazione cond){
        this.cond = cond;
    }
    
    public CondizioneAttivazione getCondizione(){
        return this.cond;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }

    private String buildQueryEliminaComando() {
       return "";
    }
    
    public boolean modificaDurata(int durata){
        if(durata <1) return false;
        this.getAzioneComando().setDurata(durata);
        this.setSalvato(false);
        return true;
    }
    
    public String toString(){
        return this.nome;
    }
    
}
