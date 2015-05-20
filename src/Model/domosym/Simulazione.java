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

    
}
