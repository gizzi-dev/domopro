/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.domosym;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.backyard.*;


/**
 *
 * @author Gianmarco
 */
public class Simulazione {
    protected int id;
    protected String nome;
    protected boolean mod;
    protected ScenarioSimulazione scenario;
    private boolean salvato;
    protected ArrayList<Programma> programmi;
    protected ProgrammaSpecifico simula;
    private HashMap<DispositivoIntelligente,Evento> allarmi;
    private HashMap<DispositivoIntelligente,Evento> avvisi;
    
    
    public Simulazione(){        
        this.mod = false;      
        this.salvato = false;
        this.programmi = new ArrayList<>();
        this.allarmi = new HashMap<>();
        this.avvisi = new HashMap<>();
        
    }
    
    public static Simulazione load(ScenarioSimulazione scen) throws SQLException{
        Simulazione sim = new Simulazione();
        sim.setScenario(scen);
        String query = sim.buildQueryGetId();
        ResultSet r = DomoSymApplicationController.appCtrl.getDBController().executeQuery(query);
        if(r.next()){
            sim.loadId(r);
            sim.loadProgrammi();
        }      
        return sim;
    }
    
    public void setScenario(ScenarioSimulazione scen){
        this.nome= "Simulazione di "+scen.getNome();
        this.scenario = scen;
    }
    
    public void setId(int id){
     this.id = id;   
    }
    
    public void loadId(ResultSet r) throws SQLException{
       int id = r.getInt("id");
       this.setId(id);
       
    }
    
    public String buildQueryGetId(){
        return "";        
    }
    
    public void loadIscrizioni(){
        String q = this.BuildQueryLoadIscrizioni();
        ResultSet r = DomoSymApplicationController.appCtrl.getDBController().executeQuery(q);
        /*
        Popolo
        */
    }
    
    public void loadProgrammi(){
        String q = this.BuildQueryLoadProgrammi();
        ResultSet r = DomoSymApplicationController.appCtrl.getDBController().executeQuery(q);
        /*
        Popolo
        */
    }

    private String BuildQueryLoadProgrammi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String BuildQueryLoadIscrizioni() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ProgrammaSpecifico getSimula(){
        return this.simula;
    }
    
    public boolean attivaSimulazione(){
        if(this.getSimula() == null) return false;
        this.setModalita(true);
        return true;
    }
    
    
    public void setModalita(boolean mod){
        this.mod = mod;
    }
    
    public ArrayList<DispositivoIntelligente> ottieniListaDispIscritti(){
        ArrayList<DispositivoIntelligente> elenco = new ArrayList<>();
        for(Map.Entry<DispositivoIntelligente,Evento> allarme: this.allarmi.entrySet()){
                elenco.add(allarme.getKey());     
        }
        for(Map.Entry<DispositivoIntelligente,Evento> avviso: this.avvisi.entrySet()){
                elenco.add(avviso.getKey());     
        }
        return elenco;
    }
    
    
    //qualsiasi o devo controllare sia iscritto?
    public DispositivoIntelligente apriDispositivo(String nomeDisp){
        return scenario.apriDispositivo(nome);
    }
    
    public boolean salvaSimulazione(){
        if(salvato) return false;
        String q = this.buildQuerySalva();
        for(Programma p: this.programmi){
            p.salvaProgramma();
        }
        /*
        salvo disp iscritti
        */
        this.setSalvato(true);
        return true;
    }

    private String buildQuerySalva() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setSalvato(boolean b) {
       this.salvato = b;
    }
        
    public boolean controllaNomeProgramma(String nome){        
        for(Programma p: this.programmi){            
            if(p.getNome().equals(nome)) return true;
        }
        return false;
    }
    public boolean creaNuovoProgramma(String nomeProgramma, boolean tipo){
        if( nomeProgramma.isEmpty() ||this.controllaNomeProgramma(nomeProgramma) ) return false;
        Programma p = null;
        if(tipo) p =new ProgrammaSpecifico(nomeProgramma);
        else  p =new ProgrammaGenerico(nomeProgramma);
        this.programmi.add(p);        
        p.setSimulazione(this);
        this.setSalvato(false);
        return true;
    }
    
    public Programma apriProgramma(String nomeProgramma){
        for(Programma p: this.programmi){
            if(p.getNome().equals(nomeProgramma)) return p;
        }
        return null;
    }
    
    public boolean aggiungiASimulazione(Programma p){        
        if(p==null || p instanceof ProgrammaGenerico) return false;
        this.simula.setAttivato(false);
        this.simula = (ProgrammaSpecifico)p;
        this.simula.setSalvato(false);
        ((ProgrammaSpecifico)p).setAttivato(true);
        this.setSalvato(false);
        p.setSalvato(false);
        return true;
    }
    
    public boolean eliminaProgramma(String nomeProgramma){
        Programma p = this.apriProgramma(nomeProgramma);        
        p.elimina();       
        
        if(p.equals(this.simula)){
            this.simula= null;
            ((ProgrammaSpecifico)p).setAttivato(false);
        }
        setSalvato(false);
        return true;
    }
    
    public boolean modificaNomeProgramma(Programma p,String nome){
        if(this.controllaNomeProgramma(nome)) return false;
        p.setNome(nome);
        p.setSalvato(false);
        this.setSalvato(false);
        return true;
    }
    
    public void salvaProgramma(Programma p){
        p.salvaProgramma();
    }
    
    public ArrayList<Programma> ottieniElencoProgrammi(){
        return this.programmi;
    }
    
    public boolean creaNuovoComando(String nomeComando,Programma prog){
        if(controllaNomeComando(nomeComando,prog)) return false;
        ComandoProgramma c = new ComandoProgramma(nomeComando);
        prog.aggiungiComando(c);
        c.setProgramma(prog);
        c.setSalvato(false);
        prog.setSalvato(false);       
        return true;
    }
    
    public boolean controllaNomeComando(String nomeComando,Programma prog){
        for(ComandoProgramma com: prog.comandi){
            if(com.getNome().equals(nomeComando)) return true;
        }
        return false;
    }
    
    public ComandoProgramma apriComando(String nomeComando,Programma prog){
        for(ComandoProgramma com: prog.comandi){
            if(com.getNome().equals(nomeComando)) return com;
        }
        return null;
    }
    
    public boolean aggiungiSottoprogramma(ComandoProgramma c, Programma sottop){
        if(sottop instanceof ProgrammaSpecifico) return false;
        c.setAzione((ProgrammaGenerico)sottop);
        return true;
    }
    
    public ArrayList<ComandoProgramma> ottieniElencoProgrammiSequenza(Programma p){
        return p.comandi;
    }
    
    public boolean cambiaNomeComando(ComandoProgramma c, String nuovoNome){
        Programma p = c.getProgramma();
        if(this.controllaNomeComando(nuovoNome,p)) return false;
        c.setNome(nuovoNome);
        c.setSalvato(false);
        return true;
    }
    
    public void salvaComando(ComandoProgramma c){
        c.salvaComando();
    }
    
    public ArrayList<ProgrammaGenerico> ottieniElencoProgrammiGenerici(){
        ArrayList<ProgrammaGenerico> elenco = new ArrayList<>();
        for(Programma p : this.programmi){
            if(p.isGenerico()) elenco.add((ProgrammaGenerico)p);
        }
        return elenco;
    }
    
    public boolean cancellaComando(String nomeComando,Programma p){        
        ComandoProgramma com = this.apriComando(nomeComando, p);
        if(com == null) return false;
        p.eliminaComando(com);
        return true;
    }
    
    public boolean impostaDurata(ComandoProgramma com,int durata){
        return com.modificaDurata(durata);
    }
    
    public void impostaAzione(Comando com,AzioneComando az){
        com.setAzione(az);
    }
    
    
    
   /********************
    * 
    * @param args 
    */
    public static void main(String[] args){
        //System.out.println("prova");
        Simulazione s = new Simulazione();
        s.creaNuovoProgramma("Gino", true);
        s.creaNuovoProgramma("Ciccio", false);
        for(Programma p: s.programmi){
            System.out.println(p);
        }
        Programma p1 = s.apriProgramma("Ciccio");
        
    }
}
