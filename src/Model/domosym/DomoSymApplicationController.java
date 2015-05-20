/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.domosym;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.backyard.*;

/**
 *
 * @author Gianmarco
 */
public class DomoSymApplicationController {
    protected static DomoSymApplicationController appCtrl;
    private Simulazione simCorrente;
    private static DBController dbController;
    
    
    
    
    
    
    
    
    
    public DBController getDBController(){
        return this.dbController;
    }
    
    public void setSimCorrente(Simulazione sim){
        this.simCorrente = sim;
    }    
    
    
    public Simulazione apriSimulazione(InfoScenario qualeScenario) throws Exception{
        ScenarioSimulazione scen = ScenarioSimulazione.load(qualeScenario, true);
        Simulazione sim = Simulazione.load(scen);
        setSimCorrente(sim);
        return sim;        
    }     
    
}
