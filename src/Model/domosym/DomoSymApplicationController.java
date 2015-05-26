/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.domosym;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.backyard.*;

/**
 *
 * @author Gianmarco
 */
public class DomoSymApplicationController {
    public static DomoSymApplicationController appCtrl;
    private Simulazione simCorrente;
    private static DBController dbController;
    private UserInfo utente;
    private ArrayList<InfoScenario> elencoScenari;
    
    
    public DomoSymApplicationController(UserInfo uInfo){
        this.appCtrl = this;
        elencoScenari = new ArrayList<InfoScenario>();   
        dbController = new DBController();
        utente = uInfo;
    }
    
    public DBController getDBController(){
        return this.dbController;
    }
    
    public void setSimCorrente(Simulazione sim){
        this.simCorrente = sim;
    } 
    
    public Simulazione getSimCorrente(){
        return this.simCorrente;
    }
    
    
    public Simulazione apriSimulazione(InfoScenario qualeScenario) throws Exception{
        ScenarioSimulazione scen = ScenarioSimulazione.load(qualeScenario, true);
        Simulazione sim = Simulazione.load(scen);
        setSimCorrente(sim);
        return sim;        
    }  
    
    public ArrayList<InfoScenario> OttieniScenariDiSimulazione(UserInfo uInfo) throws SQLException{        
        if(uInfo.checkInquilino()){
            appCtrl.setUtente(uInfo);           
            ArrayList<InfoScenario> elenco = InfoScenario.elencoScenariCompleto(uInfo);
            System.out.println(elenco);
            this.setElencoScenari(elenco);
            return elenco;
        }
        return new ArrayList<InfoScenario>();
    }
    
    public void setUtente(UserInfo uInfo){
        this.utente = uInfo;
    }
    
     
    public void setElencoScenari(ArrayList<InfoScenario> elenco){
        this.elencoScenari = elenco;
    }
    
    public void salvaSimulazione(){
        this.simCorrente.salvaSimulazione();
    }
}
