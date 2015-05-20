/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.backyard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gianmarco
 */
public abstract class DispositivoIntelligente implements Contesto , Importabile, Collocazione {
    private String nome;    
    private tipoDispositivo tipo ;
    private Luogo dove;
    private ArrayList<Azione> azioni;
    private ArrayList<DIComplesso> usatoIn;
    private ArrayList<Risorsa> risorse;
    
    public DispositivoIntelligente(String nome){
        this.nome = nome;
        azioni = new ArrayList<Azione>();
        risorse = new ArrayList<Risorsa>();
        usatoIn = new ArrayList<DIComplesso>();
    }
    
    private boolean ControllaNomeAzione(String nomeAzione){
        for(Azione azione:azioni){
            if(azione.getNome().equals(nomeAzione)) return true;
        }        
        return false;
    }
    
    public DispositivoIntelligente clone(){
        try {
            return (DispositivoIntelligente)super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public ArrayList<DIComplesso> getUsatoIn(){
        return this.usatoIn;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    /**
     * 
     * @param luogo
     * @return boolean se è spostabile o meno
     */
    public boolean isSpostabile(Luogo luogo){
        ArrayList<Risorsa> risPerdute = new ArrayList<Risorsa>();
        if(dove instanceof Stanza && luogo != dove){ //se sta in una stanza e lo voglio spostare in un altro luogo
            risPerdute.addAll(dove.getRisorse());  //aggiungo le risorse della stanza
            if((luogo instanceof Stanza && !((Stanza)dove).getPiano().hasStanza((Stanza)luogo)) && //è un altra stanza in un altro piano
                    (luogo instanceof Piano &&((Stanza)dove).getPiano() != luogo) && // lo metto in un piano diverso da quello in cui sto
                            (luogo instanceof Alloggio)){// lo metto nell'alloggio
                 risPerdute.addAll(((Stanza)dove).getPiano().getRisorse()); //aggiungo le risorse del piano in cui sta                
            }
        }
        else if(dove instanceof Piano && luogo!= dove && !((Piano)dove).hasStanza((Stanza)luogo)){
            risPerdute.addAll(dove.getRisorse());
        }
        for(Risorsa ris : risPerdute){
            if(this.usa(ris)) return false;
        }
        return false;            
    }    
    
    public boolean isSottoDispositivo(){
        return usatoIn.size()==0; 
    }
    
    public boolean isDIComplesso(){
        return (this instanceof DIComplesso );
    }
    
    public void setDove(Luogo dove){
        this.dove = dove;
    }
    
    public ArrayList<Azione> getAzioni(){
        return this.azioni;
    }
    
    public ArrayList<DispositivoIntelligente> getSottoDispositivi(){        
        try{
            return ((DIComplesso)this).getSottoDispositivi();
        }
        catch(ClassCastException e){
            return new ArrayList<DispositivoIntelligente>();
        }
    }
    
    public Azione richiediDettagliAzione(String nomeAzione){
        for(Azione azione: azioni){
            if(azione.equals(nomeAzione))return azione; 
        }
        return null;
    }
    
    public boolean creaNuovaAzione(String nome){        
        if(this.richiediDettagliAzione(nome) != null) return false;
        AzioneSemplice az = new AzioneSemplice(nome);
        Evento startEv =Evento.createStartEvent(az);
        Evento endEv =Evento.createEndEvent(az);
        az.addEvent(startEv);
        az.addEvent(endEv);
        this.azioni.add(az);
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
        return true;    
    }
    
    
    
    public boolean eliminaAzione(String nomeAzione) throws Exception{ 
        Azione az;
        if((az =this.richiediDettagliAzione(nomeAzione))==null) throw new Exception("Azione inesistente");
        if(az.isSottoazione())throw new Exception("Impossibile eliminare una sottoazione");
        this.azioni.remove(az);
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
        return true;        
    }
    
    public boolean modificaAzione(Azione azionedaModificare,String nome ,boolean programmabile) throws Exception{  
        if(!this.isSuaAzione(azionedaModificare)) return false;
        if(this.richiediDettagliAzione(nome) != null) throw new Exception("Esiste già un azione con quel nome");
        azionedaModificare.setNome(nome);
        azionedaModificare.setProgrammabilita(programmabile);
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
        return true;
    }
    
    public boolean impostaDurata(Azione azionedaModificare,int durata){ 
        if(!this.isSuaAzione(azionedaModificare)) return false;  
        azionedaModificare.setDurata(durata);
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
        return true;        
    }
    
    public HashMap<Risorsa,Double> richiediRisorseUtilizzate(Azione azione){
        if(!this.isSuaAzione(azione)) return null;               
        return azione.getUtilizzoRisorse();        
    }
    
    public ArrayList<Risorsa> richiediRisorseAccessibili(){
        ArrayList<Risorsa> ris = new ArrayList<Risorsa>();
        this.collectRisorseAccessibili(ris);
        ris.addAll(getRisorse());
        for(DispositivoIntelligente disp: this.getSottoDispositivi()){
            ris.addAll(disp.richiediRisorseAccessibili());
        }  
        return ris;
    }    
    
    public boolean aggiungiUtilizzoRisorsa(Azione azione,Risorsa risorsa,double val){
        if(!this.isSuaAzione(azione)) return false;        
        azione.addUtilizzoRisorsa(risorsa, val);
        risorsa.addUtilizzo(azione);
        azione.setDispositivo(this);
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
        return true;
    }
    
    public boolean eliminaUtilizzoRisorsa(Azione azione,Risorsa risorsa){
        if(!this.isSuaAzione(azione)) return false;
        azione.removeUtilizzoRisorsa(risorsa);  
        risorsa.removeUtilizzo(azione);        
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
        return true;
    }
    
    public boolean impostaMacroazione(Azione azione,boolean val){
       if(!this.isSuaAzione(azione) || !this.isDIComplesso()) return false;
       Azione az = null;
       if(val) az = azione.getAsComplessa();  
       else  az = azione.getAsSemplice();  
       this.azioni.remove(azione);
       this.azioni.add(az);
       BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
       return true;
    }
    
    public ArrayList<Azione> richiediElencoSottoazioni(Azione azione){
        if(!this.isSuaAzione(azione)) return null;         
        return azione.getSottoAzioni();
        
    } 
    
    /**
     * Ritorna una lista con tutte le sottoazioni dei sottoDisp del dispositivo
     * @param azione
     * @return ArrayList < Azione >
     */
    public ArrayList<Azione> richiediSottoazioniDisponibili(Azione azione) throws Exception{
        if(!azione.isMacroazione()) throw new Exception("Non e' una macroazione!");
        ArrayList<Azione> elenco = new ArrayList<Azione>();
        for(DispositivoIntelligente sottoDisp: this.getSottoDispositivi()){
            elenco.addAll(sottoDisp.getAzioni());
        }
        return elenco;
    }
    
    public ArrayList<Evento> richiediEventiDisponibili(){
        ArrayList<Evento> eventi = new ArrayList<Evento>();
        for(Azione azione: this.azioni){            
            eventi.addAll(azione.getEventi());
        }
        return eventi;
    }
    
    public boolean isSuaAzione(Azione azionedaCercare){
        if(azionedaCercare == null) return false;
        for(Azione azione: this.azioni){
            if(azione.getNome().equals(azionedaCercare.getNome())) return true;
        }        
        return false;
    }
    
    public boolean usa(DispositivoIntelligente disp){
        if(disp == this) return true;
        for(DispositivoIntelligente sottod : this.getSottoDispositivi()){
            if(usa(sottod)) return true;
        }
        return false;
    }
    
    /**
     * Controlla se il dispositivo usa una determinata risorsa
     * @param risorsa
     * @return true se usa la risorsa, in caso false
     */
    public boolean usa(Risorsa risorsa){
        if(risorsa == null) return false;        
        HashMap<Risorsa,Double> usate = new HashMap<Risorsa,Double>();        
        for(Azione azione: this.azioni){
            usate.putAll(azione.getUtilizzoRisorse());
        }
        for(Entry<Risorsa,Double> ris: usate.entrySet()){
            if(ris.getKey().getNome().equals(risorsa.getNome())) return true;
        }
        return false;
    }    
    
    public void setTipo(tipoDispositivo tipo){
        this.tipo = tipo;
    }
    
    /**
     * Trasforma il dispositovo
     * @return 
     */
    public abstract DIComplesso getAsComplesso()  ;   
    
    //
    public abstract DISemplice getAsSemplice();
    
    public boolean inLuogo(Luogo luogo){
        return (luogo==null) ? false : this.dove.equals(luogo);
    }      

    @Override
    public void addRisorsa(Risorsa risorsa) {
        this.risorse.add(risorsa);
    }

    @Override
    public void removeRisorsa(Risorsa risorsa) {
       this.risorse.remove(risorsa);
    }    
    
    @Override
    public ArrayList<Risorsa> getRisorseFornite() {
        return this.risorse;
    }
    
    @Override
    public ArrayList<Risorsa> getRisorse(){
        return this.risorse;
    }    
    
    @Override
    public void collectRisorseAccessibili(ArrayList<Risorsa> risorse) {
        risorse.addAll(this.risorse);
        for(DispositivoIntelligente disp: this.getSottoDispositivi()){
            disp.collectRisorseAccessibili(risorse);
        }
    }
    
    public Luogo getLuogo(){
        return this.dove;
    }
    
    public void impostaAbilitazione(Evento ev, Boolean abilitaz){
        ev.setAbilitato(abilitaz);
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);        
    }
    
   
    public boolean removeSottoDispositivo(DispositivoIntelligente sottoDisp) {
        for(Azione az: this.azioni){
            if(az.usaDispositivo(sottoDisp)) return false;            
        }
        this.removeSottoDispositivo(sottoDisp);
        return true;
    }
    
    public tipoDispositivo getTipo(){
        return this.tipo;
    }
}
