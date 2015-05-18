/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Gianmarco
 */
public class AzioneComplessa extends Azione {
    private ArrayList<Azione> sottoazioni;
    
    public AzioneComplessa(String nome){
        super(nome);
        sottoazioni = new ArrayList<Azione>();
    }
    
    public boolean addSottoAzione(Azione azione){
        if( azione == null) return false;
        Azione ultima = this.sottoazioni.get(this.sottoazioni.size() -1);
        this.addEvent(Evento.createTransitionEvent(this, ultima, azione));
        sottoazioni.add(azione);     
        return true;
    }
    
    //da testare
    public boolean removeSottoAzione(Azione azione){
        if(azione == null) return false;
        int index = this.sottoazioni.indexOf(azione);
        Azione prev = this.sottoazioni.get(index-1);
        Azione next = this.sottoazioni.get(index+1);
        for(Evento ev: this.getEventi()){
            if(ev.isTransitionEventFor(prev, azione) || ev.isTransitionEventFor(azione, next)){
                this.getEventi().remove(ev);
            }
        }
        Evento evento = Evento.createTransitionEvent(this, prev, next);
        this.addEvent(evento);
        sottoazioni.remove(azione);
        return true;
    }
    
    public boolean addSottoAzioneToPos(Azione azione,int pos){
        if(azione == null || pos <0 )return false;
        if(pos == sottoazioni.size()-1) return this.addSottoAzione(azione);
        Azione prev = this.sottoazioni.get(pos-1);
        Azione next = this.sottoazioni.get(pos+1);
        for(Evento ev: this.getEventi()){
            if(ev.isTransitionEventFor(prev,next)){
                this.getEventi().remove(ev);
            } 
        }
        if(pos!=0)this.getEventi().add(Evento.createTransitionEvent(this, prev, azione));
        this.getEventi().add(Evento.createTransitionEvent(this, azione, next));        
        sottoazioni.add(pos,azione);
        return true;
    } 
    
    
    
    public ArrayList<Azione> getSottoazioni(){
        return this.sottoazioni;
    }

    

    

    
    
}
