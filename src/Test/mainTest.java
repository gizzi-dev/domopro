/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;
import DomoPro.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Gianmarco
 */
public class mainTest {
    
    public static void main(String[] args) throws Exception {
      UserInfo utente = new UserInfo(0,"utente");  
      BackYardApplicationController app = new BackYardApplicationController(utente);
      app.creaNuovoScenario(utente, "Prova");
      app.salvaScenario();
      
        /*try {
            
            ScenarioSimulazione s = app.creaNuovoScenario(utente, "Scenario di Prova");
            s.creaDispositivo("gino");
            System.out.println(s);
            s.setInfoScenario(new InfoScenario(0,s.getNome(),utente));
            Piano p0 =s.aggiungiPiano("Piano 0", 0);
            //s.salva();
            Piano p1= s.aggiungiPiano("Piano 1", 1);
            s.aggiungiStanza(p0, "Stanza1");
            Stanza stanza = s.apriStanza(p0,"Stanza Default");
            stanza.setNome("Prova stanza Cambio nome");
            s.creaRisorsa("Luce", 10,s.getAlloggio());            
            s.creaRisorsa("Acqua", 10,20, 0, stanza);
            
            
            
            
            for(String string: s.salvaAlloggio()){
                System.out.println(string);
                System.out.println(BackYardApplicationController.getDBController().executeInsert(string));
            }
            
        } catch (Exception ex) {
            Logger.getLogger(mainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
     */ 
    }
}
