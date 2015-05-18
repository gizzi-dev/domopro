/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomoPro;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Gianmarco
 */
public class BackYardApplicationController {
    private static BackYardApplicationController appCtrl;
    private ArrayList<InfoScenario> elencoScenari;
    private ScenarioSimulazione scenarioCorrente;
    private static DBController dbController;
    private UserInfo utente;
    
    public BackYardApplicationController(UserInfo uInfo){
        this.appCtrl = this;
        elencoScenari = new ArrayList<InfoScenario>();   
        dbController = new DBController();
        utente = uInfo;
    }
    
    public static BackYardApplicationController getAppController(){
        return BackYardApplicationController.appCtrl;
    }
    
    public static void createAppController(DBController db){        
        
    }
    
    public void setUtente(UserInfo info){
        this.utente = info;
    }
    public UserInfo getUtente(){
        return this.utente;
    }
    
    
    public void setScenarioCorrente(ScenarioSimulazione scen){
        this.scenarioCorrente = scen;
    }
    
    public ScenarioSimulazione getScenarioCorrente(){
        return this.scenarioCorrente;
    }
    public static DBController getDBController(){
        return BackYardApplicationController.dbController;
    }    
    
    public boolean controllaNomeSCenario(String nome){
        for(InfoScenario info: elencoScenari){
            if(info.getNome().equals(nome)) return true;
        }
        return false;
    }
    
    
    public ArrayList<InfoScenario> OttieniScenariDiSimulazione(UserInfo uInfo) throws SQLException{
        appCtrl.setUtente(uInfo);
        if(uInfo.checkTecnico()){
            ArrayList<InfoScenario> elenco = InfoScenario.elencoScenari(uInfo);
            this.setElencoScenari(elenco);
            return elenco;
        }
        return null;
    }
    
    public void setElencoScenari(ArrayList<InfoScenario> elenco){
        this.elencoScenari = elenco;
    }
    
    public ArrayList<InfoScenario> eliminaScenario(InfoScenario qualeScenario){
        qualeScenario.elimina();
        BackYardApplicationController.getAppController().elencoScenari.remove(qualeScenario);
        return BackYardApplicationController.getAppController().elencoScenari;
    }
    
    
    public ArrayList<InfoScenario> duplicaScenario(InfoScenario scInfo,String nuovoNome) throws Exception{
        if(!this.controllaNomeSCenario(nuovoNome)) throw new Exception();
        this.elencoScenari.add(scInfo.duplica(nuovoNome));
        return this.elencoScenari;
    }
    
   
    public ScenarioSimulazione creaNuovoScenario(UserInfo uInfo,String nome) throws Exception{
        this.setUtente(uInfo);
        if(!utente.checkTecnico() && this.controllaNomeSCenario(nome))throw new Exception();
        ScenarioSimulazione instance =ScenarioSimulazione.crea(nome, uInfo);        
        setScenarioCorrente(instance);
        return instance;       
    }
    
    public void salvaScenario(){
        getScenarioCorrente().salva();
    }
    
    public void apriScenario(InfoScenario qualeScenario) throws Exception{
        setScenarioCorrente(ScenarioSimulazione.load(qualeScenario, true));
        
    }    
    
    public ArrayList<Importabile> ottieniElementiImportabili(InfoScenario daScenario,Contesto contesto) throws Exception{
        ArrayList<Importabile> lista = new ArrayList<Importabile>();
        ScenarioSimulazione scen = ScenarioSimulazione.load(daScenario, false);
        if(contesto instanceof Alloggio){
            lista.addAll(scen.getPiani());
        }
        if(contesto instanceof DispositivoIntelligente){
            lista.addAll(scen.getDispositivi());
        }
        return lista;
    }    
    
    public boolean importa(ScenarioSimulazione daScen,ScenarioSimulazione aScen,Importabile elem){
        Importabile importato = elem.clone();
        boolean valido = false;
        if(importato instanceof Piano){
            valido =aScen.importaPiano((Piano)importato);
        }
        else if(importato instanceof DispositivoIntelligente){
            valido = aScen.importaDispositivo((DispositivoIntelligente)importato);
        }
        if(valido)aScen.setSalvato(false);
        return valido;        
    }
}
