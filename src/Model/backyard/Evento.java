/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.backyard;

/**
 *
 * @author Gianmarco
 */
public class Evento {
    private String nome;
    private boolean abilitato;
    private boolean allarme;
    private Azione azione;
    private Risorsa ris;
    private AzioneComplessa uno;
    private Azione due;
    private Azione tre;
    
    private Evento(String nome,Azione az){
        this.nome = nome;
        this.azione = az;
    }
    
    public static Evento createStartEvent(Azione azione){
        return new Evento("Start Event("+azione.getNome()+")",azione);
    }
    public static Evento createEndEvent(Azione azione){
        return new Evento("End Event("+azione.getNome()+")",azione);
    }
    public static Evento createUnavailabilityEvent(Azione azione,Risorsa risorsa){
        return new Evento("E' finita la risorsa "+risorsa.getNome()+"("+azione.getNome()+")",azione);
    }
    public static Evento createTransitionEvent(Azione uno,Azione due,Azione tre){
        Evento ev =new Evento("Transition Event ("+uno.getNome()+") da "+due.getNome()+" a "+tre.getNome(),uno);
        ev.setAzioni(uno, due, tre);
        return ev;
    }
    
    
    public boolean isStartEvent(){
        return this.nome=="Start Event("+azione.getNome()+")";
    }
    public boolean isEndEvent(){
        return this.nome=="End Event("+azione.getNome()+")";
    }
    
    public boolean isUnavailabilityEvent(){
        return ris==null;
    }
    
    public boolean isUnavailabilityEventFor(Risorsa risorsa){
        return ris==risorsa;
    }
    
    public boolean isTransitionEvent(){
        return due!=null && tre != null;
    }
    
    public boolean isTransitionEventFor(Azione azione1,Azione azione2){
         return due==azione1 && tre == azione2;
    }
    
    //public void rename()//String??
    public void setAbilitato(boolean val){
        this.abilitato = val;
    }
    
    public void setRisorsa(Risorsa ris){
        
    }
    public void setAzioni(Azione uno,Azione due,Azione tre){
        
    }
    
}
