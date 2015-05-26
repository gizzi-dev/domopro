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
    private ArrayList<DispositivoIscritto> dispIscritti;
    protected Simulazione instanza;
    
    
    private Simulazione(){        
        this.mod = false;      
        this.salvato = false;
        this.programmi = new ArrayList<>();
        this.dispIscritti = new ArrayList<>();
        this.instanza = this;
        this.id =-1;
    }
    
    public static Simulazione load(ScenarioSimulazione scen) throws SQLException{
        Simulazione sim = new Simulazione();
        sim.setScenario(scen);
        int idScen = scen.getInfoScenario().getId();
        String query = sim.buildQueryLoad(idScen);
        ResultSet r = DomoSymApplicationController.appCtrl.getDBController().executeQuery(query);        
        if(r != null && r.next()){
            sim.loadId(r);
            sim.loadProgrammi();
           // sim.loadIscrizioni();
            Programma p = sim.apriProgramma(r.getString("simula"));
            if(p!= null) sim.setSimula((ProgrammaSpecifico) p);
        }    
        return sim;
    }
    
    public ScenarioSimulazione getScenario(){
        return this.scenario;
    }
    
    public int getId(){
        return this.id;
    }
    public void setScenario(ScenarioSimulazione scen){
        this.nome= "Simulazione di "+scen.getNome();
        this.scenario = scen;
    }
    
    public boolean getSalvato(){
        return this.salvato;
    }
    
    public void setId(int id){
     this.id = id;   
    }
    
    public void loadId(ResultSet r) throws SQLException{
       int id = r.getInt("id");
       this.setId(id);
       
    }
    
    private String buildQueryLoad(int idScen){
        return "SELECT * FROM simulazione where idScenario = '"+idScen+"'";        
    }
    
    private void loadIscrizioni(){
        String q = this.BuildQueryLoadIscrizioni();
        ResultSet r = DomoSymApplicationController.appCtrl.getDBController().executeQuery(q);
        /*
        Popolo
        */
    }
    
    private void loadProgrammi(){
        String[] q = this.BuildQueryLoadProgrammi();
        ResultSet r = null;
        for(int i = 0; i <q.length;i++){
            r= DomoSymApplicationController.appCtrl.getDBController().executeQuery(q[i]);
            try{
                while(r.next()){
                    String nome = r.getString("nome");
                    boolean attivato = r.getBoolean("attivato");
                    boolean specifico = r.getBoolean("specifico");
                    int id = r.getInt("id");
                    Programma p;
                    if(specifico){
                        p = new ProgrammaSpecifico(nome);
                        if(attivato){
                            ((ProgrammaSpecifico)p).setAttivato(true);
                            this.simula = (ProgrammaSpecifico)p;
                        }
                    }
                    else p = new ProgrammaGenerico(nome);
                    p.setId(id);
                    p.setSimulazione(this);
                    this.programmi.add(p);
                }
            }catch(SQLException e){
                
            }
        }
        
    }

    private String[] BuildQueryLoadProgrammi() {
        String [] query = new String [1];
        query[0] = "SELECT * FROM programma WHERE idSimulazione='"+this.id+"'";
        return query;
    }

    private String BuildQueryLoadIscrizioni() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ProgrammaSpecifico getSimula(){
        return this.simula;
    }
    
    public void setSimula(ProgrammaSpecifico prog){
        if(prog.equals(simula)) 
            this.simula = null;
        else this.simula = prog;            
        
    }
    
    public boolean attivaSimulazione(){
        if(this.getSimula() == null) return false;
        this.setModalita(true);
        this.setSalvato(false);
        return true;
    }
    
    
    public void setModalita(boolean mod){
        this.mod = mod;
    }
    
    public ArrayList<DispositivoIntelligente> ottieniListaDispIscritti(){
        ArrayList<DispositivoIntelligente> elenco = new ArrayList<>();
        for(DispositivoIscritto i: this.dispIscritti){
            elenco.add(i.getDispositivo());
        }       
        return elenco;
    }
    
    public ArrayList<DispositivoIntelligente> ottieniElencoDispositivi(){
        return this.scenario.getDispositivi();
    }
    
    
    //qualsiasi o devo controllare sia iscritto?
    public DispositivoIntelligente apriDispositivo(String nomeDisp){
        return scenario.apriDispositivo(nome);
    }
    
    public boolean salvaSimulazione(){
        if(salvato) return false;
        String q= "";
        if(this.id == -1){
            q = this.buildQuerySalvaInsert();
            this.id = DomoSymApplicationController.appCtrl.getDBController().executeInsert(q);
        }
        else{
            q = this.buildQuerySalva();
            DomoSymApplicationController.appCtrl.getDBController().executeUpdate(q);
        }
        System.out.println("\n"+q+" - "+this.id);
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
        String s = "''";
        if(this.simula != null) s = this.simula.getNome();
        return "UPDATE simulazione SET modalita='"+(this.mod ? 1 : 0)+"',simula='"+s+"',idScenario='"+this.scenario.getInfoScenario().getId()+"' WHERE id='"+this.id+"'";
    }
    
    private String buildQuerySalvaInsert(){
        String s = "''";
        if(this.simula != null) s = this.simula.getNome();
        return "INSERT INTO simulazione(idScenario,modalita,simula) VALUES ('"+this.scenario.getInfoScenario().getId()+"','"+(this.mod ? 1 : 0)+"',"+s+")";
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
    public Programma creaNuovoProgramma(String nomeProgramma, boolean tipo){
        if( nomeProgramma.isEmpty() ||this.controllaNomeProgramma(nomeProgramma) ) return null;
        Programma p = null;
        if(tipo) p =new ProgrammaSpecifico(nomeProgramma);
        else  p =new ProgrammaGenerico(nomeProgramma);
        this.programmi.add(p);        
        p.setSimulazione(this);
        this.setSalvato(false);
        return p;
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
        this.setSimula((ProgrammaSpecifico)p);
        this.setSalvato(false);
        p.setSalvato(false);
        return true;
    }
    
    public boolean eliminaProgramma(String nomeProgramma){
        Programma p = this.apriProgramma(nomeProgramma);        
        p.elimina();       
        this.programmi.remove(p);
        if(p.equals(this.simula)){
            this.simula= null;
            ((ProgrammaSpecifico)p).setAttivato(false);
        }        
        setSalvato(false);
        return true;
    }
    
    public boolean modificaNomeProgramma(Programma p,String nome){
        if(this.controllaNomeProgramma(nome) && !p.getNome().equals(nome)) return false;
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
    
    public ComandoProgramma creaNuovoComando(String nomeComando,Programma prog){
        if(controllaNomeComando(nomeComando,prog)) return null;
        ComandoProgramma c = new ComandoProgramma(nomeComando);
        prog.aggiungiComando(c);
        c.setProgramma(prog);
        c.setSalvato(false);
        prog.setSalvato(false);       
        return c;
    }
    
    public boolean controllaNomeComando(String nomeComando,Programma prog){
        for(ComandoProgramma com: prog.comandi){
            if(com.getNome().equals(nomeComando)) return true;
        }
        return false;
    }
    
    public ComandoProgramma apriComando(String nomeComando,Programma prog){
        for(ComandoProgramma com: prog.getComandi()){
            if(com.getNome().equals(nomeComando)) return com;
        }
        return null;
    }
    
    public boolean aggiungiSottoprogramma(ComandoProgramma c, ProgrammaGenerico sottop){      
        if(sottop == null || c.getProgramma().equals(sottop)) return false;        
        return this.impostaAzione(c, sottop);
    }
    
    public ArrayList<ComandoProgramma> ottieniElencoComandiSequenza(Programma p){
        return p.comandi;
    }
    
    public boolean cambiaNomeComando(ComandoProgramma c, String nuovoNome){
        Programma p = c.getProgramma();
        if(this.controllaNomeComando(nuovoNome,p) && !c.getNome().equals(nuovoNome)) return false;
        c.setNome(nuovoNome);
        c.setSalvato(false);
        return true;
    }
    
    public void salvaComando(ComandoProgramma c){
        c.salvaComando();
    }
    
    public ArrayList<ProgrammaGenerico> richiediElencoProgrammiGenerici(){
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
        p.setSalvato(false);
        return true;
    }
    
    public void impostaDurata(ComandoProgramma com,int durata){
        com.modificaDurata(durata);
        com.setSalvato(false);
    }
    
    public boolean impostaAzione(ComandoProgramma com,AzioneComando az){        
        com.setAzione(az);
        com.setSalvato(false);
        return true;
    }
    
    public void impostaCondizioneAttivazione(ComandoProgramma com,CondizioneAttivazione cond){
        com.setCondizione(cond);
        com.setSalvato(false);
    }
   
    public ArrayList<Evento> ottieniListaEventi(){        
        ArrayList<Evento> elenco = new ArrayList<>();        
        for(DispositivoIntelligente disp: this.ottieniElencoDispositivi()){
            elenco.addAll(disp.richiediEventiDisponibili());
        }
        return elenco;
    }

    public ArrayList<AzioneComando> getAzioniComandoDisponibili() {        
        ArrayList<AzioneComando> azioni = new ArrayList<>();
        for(ProgrammaGenerico prog : this.richiediElencoProgrammiGenerici()){
            azioni.add((AzioneComando)prog);
        }
        return azioni;
    }
    
    public void aggiungiADispIscritti(String nomeDispositivo){
        DispositivoIntelligente disp = this.apriDispositivo(nomeDispositivo);
        DispositivoIscritto in = new DispositivoIscritto(disp);
        this.dispIscritti.add(in);
        this.setSalvato(false);
    }
    
    public void impostaAvvisi(DispositivoIntelligente disp, Evento ev){
        for(DispositivoIscritto in : this.dispIscritti){
            if(in.getDispositivo().getNome().equals(disp.getNome())) in.impostaAvviso(ev);
        }
        this.setSalvato(false);
    }
    
    public void impostaAllarmi(DispositivoIntelligente disp, Evento ev){
        for(DispositivoIscritto in : this.dispIscritti){
            if(in.getDispositivo().getNome().equals(disp.getNome())) in.impostaAllarme(ev);
        }
        this.setSalvato(false);
    }    
    
  
}
