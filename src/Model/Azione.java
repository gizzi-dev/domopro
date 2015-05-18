/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
    private void shallowReplace(Azione azione1,Azione azione2){
        for(Azione sottoAz: getSottoAzioni()){
            if(azione1.equals(azione2)){
               int index = this.getSottoAzioni().indexOf(azione1);
               ((AzioneComplessa)this).removeSottoAzione(azione1);
               ((AzioneComplessa)this).addSottoAzioneToPos(azione2, index);
            }
        }
    }
    
    public boolean isSottoazione(){
        return !usataIn.isEmpty();
    }
    
    public boolean isMacroazione(){
        return this.programmabile;
    }
    
    public void setNome(String nome){
        this.nome = nome;        
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
    public AzioneComplessa getAsComplessa(){
        if(this instanceof AzioneComplessa) return (AzioneComplessa)this;
        else if(this instanceof AzioneSemplice){
            AzioneComplessa az = new AzioneComplessa(this.nome);
            az.setDispositivo(this.dispositivo);
            az.setDurata(this.durata);
            az.setEventi(this.eventi);
            az.setProgrammabilita(this.programmabile);
            az.setUtilizzoRisorse(this.utilizzoRisorse);
            az.setUsataIn(this.usataIn);
            for(Azione azi: this.usataIn){
                azi.shallowReplace(this, az);
            }
            for(Entry<Risorsa,Double> ris: this.utilizzoRisorse.entrySet()){
                ris.getKey().removeUtilizzo(az);                
                ris.getKey().addUtilizzo(az);               
            }
            return az;
        }   
        return null;
    }
    
    /**
     * Converte l'azione in AzioneSemplice creando un altro oggetto se necessario
     * @return AzioneSemplice 
     */
    public AzioneSemplice getAsSemplice(){
        if(this instanceof AzioneSemplice)return (AzioneSemplice)this;
        else if(this instanceof AzioneComplessa){
            AzioneSemplice az = new AzioneSemplice(this.nome);
            az.setDispositivo(this.dispositivo);
            az.setDurata(this.durata);
            az.setProgrammabilita(this.programmabile);
            az.setUtilizzoRisorse(this.utilizzoRisorse);
            for(Evento ev: this.eventi){
                if(ev.isTransitionEvent()){
                    az.getEventi().remove(ev);
                }
            }
            for(Azione ac: this.usataIn){
                this.shallowReplace(az, ac);
            }    
            for(Entry<Risorsa,Double> ris: this.utilizzoRisorse.entrySet()){
                ris.getKey().removeUtilizzo(az);                
                ris.getKey().addUtilizzo(az);
            }
            return az;            
        }
        return null;
    }
    
    
    public void setEventi(ArrayList<Evento> eventi) {
        this.eventi = eventi;
    }
    
    public void setUtilizzoRisorse(HashMap<Risorsa, Double> utilizzoRisorse) {
        this.utilizzoRisorse = utilizzoRisorse;
    }
    
    public void setUsataIn(ArrayList<AzioneComplessa> usataIn) {
        this.usataIn = usataIn;
    }
    
    public ArrayList<Azione> getSottoAzioni(){       
        try{
            return ((AzioneComplessa)this).getSottoazioni();
        }
        catch(ClassCastException e){
            return new ArrayList<Azione>();
        }
    }    
    
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
