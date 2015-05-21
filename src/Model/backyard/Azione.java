/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.backyard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Antonio
 */
public abstract class Azione {
    private String nome;
    private int durata;
    private boolean programmabile;
    private ArrayList<Evento> eventi;
    private HashMap<Risorsa,Double> utilizzoRisorse;
    private ArrayList<AzioneComplessa> usataIn;
    private DispositivoIntelligente dispositivo;
    
    public Azione(String nome){
        this.nome=nome;
        this.eventi = new ArrayList<Evento>();
        this.utilizzoRisorse = new HashMap<Risorsa,Double>();
        this.usataIn = new ArrayList<AzioneComplessa>();
        
    }
    
    /**
     * Se azione1 era una delle sottoazioni di this, bisogna rimpiazzarla
     * fra le sue sottoazioni con la "nuova identità" azione2
     * @param azione1
     * @param azione2 
     */
    public abstract void shallowReplace(Azione azione1,Azione azione2);
    
    public boolean isSottoazione(){
        return !usataIn.isEmpty();
    }
    
    public boolean isMacroazione(){
        return this.programmabile;
    }
    
    public void setNome(String nome){
        this.nome = nome;        
    }
    
    public ArrayList<AzioneComplessa> getUsataIn(){
        return this.usataIn;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setProgrammabilita(boolean prog){
        this.programmabile = prog;
    }
    public void setDurata(int durata){
        this.durata =durata;
    }
    
    public int getDurata(){
        return this.durata;
    }
    
    public boolean getProgrammabile(){
        return this.programmabile;
    }
    
    public DispositivoIntelligente getDispositivo(){
        return this.dispositivo;
    }
    
    public boolean addEvent(Evento ev){
        return this.eventi.add(ev);
    }    
    
    public HashMap<Risorsa,Double> getUtilizzoRisorse(){
        return this.utilizzoRisorse;
    }
    
    public boolean addUtilizzoRisorsa(Risorsa risorsa, Double quanto){
        if(risorsa!= null || quanto != null){
            utilizzoRisorse.put(risorsa, quanto);            
            this.eventi.add(Evento.createUnavailabilityEvent(this, risorsa));
            return true;
        }
        return false;
    }
    
    
    public boolean removeUtilizzoRisorsa(Risorsa risorsa){  
        if(utilizzoRisorse.remove(risorsa)== null) return false;
        for(Evento ev: this.eventi){
            if(ev.isUnavailabilityEventFor(risorsa)){
                this.eventi.remove(ev);
            }
        }        
        return true;        
        
    }
    
    /**
     * Coverte l'azione in AzioneComplessa creando un altro oggetto se necessario
     * @return 
     */
    public abstract AzioneComplessa getAsComplessa();
    
    /**
     * Converte l'azione in AzioneSemplice creando un altro oggetto se necessario
     * @return AzioneSemplice 
     */
    public abstract AzioneSemplice getAsSemplice();
    
    
    public void setEventi(ArrayList<Evento> eventi) {
        this.eventi = eventi;
    }
    
    public void setUtilizzoRisorse(HashMap<Risorsa, Double> utilizzoRisorse) {
        this.utilizzoRisorse = utilizzoRisorse;
    }
    
    public void setUsataIn(ArrayList<AzioneComplessa> usataIn) {
        this.usataIn = usataIn;
    }
    
    public abstract ArrayList<Azione> getSottoAzioni();
    
    public ArrayList<Evento> getEventi(){
        return this.eventi;
    }
    
    public void setDispositivo(DispositivoIntelligente dispositivo){
        this.dispositivo = dispositivo;
    }
    
    /**
     * Funzione che controlla se l'azione in questione un azione di un altro sottoDisp
     * @param sottoDisp
     * @return true se lo usa, false no;
     */
    public boolean usaDispositivo(DispositivoIntelligente sottoDisp){
        if(!this.isMacroazione()) return false;
        for(Azione sottoAz: this.getSottoAzioni()){
            if(sottoAz.usaDispositivo(sottoDisp)) return true;
        }
        return false;
        
    }  

}
