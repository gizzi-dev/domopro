/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.backyard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Gianmarco
 */
public class InfoScenario {
    private String nome;
    private int id;
    private UserInfo autore;
    private ScenarioSimulazione scen; // come faccio ad arrivare agli scenari simulazione senza?
    
    public InfoScenario(int id,String nome, UserInfo autore){
        this.nome =nome;
        this.id = id;
        this.autore= autore;        
    }
    
    //da controllare
    public static ArrayList<InfoScenario> elencoScenari(UserInfo uInfo) throws SQLException{
        String query = buildQueryElenco(uInfo.getId());
        System.out.println(query);
        ResultSet risultati = BackYardApplicationController.getDBController().executeQuery(query);
        ArrayList<InfoScenario> elenco = new ArrayList<InfoScenario>();
        while (risultati.next()) {
            String id = risultati.getString("id");
            String name = risultati.getString("nome");           
            elenco.add(new InfoScenario(Integer.parseInt(id),name,uInfo));
        }
        return elenco; 
    }
    
    public static String buildQueryElenco(int id){
        return new String("SELECT * FROM scenario WHERE idAutore="+id);
    }
    
    private String[] buildQueryElimina(int id){
        String [] s = new String[11];
        s[0] ="DELETE FROM scenario WHERE id="+id;
        s[1] ="DELETE FROM azione WHERE idScenario="+id;
        s[2] ="DELETE FROM dispositivo WHERE idScenario="+id;
        s[3] ="DELETE FROM evento WHERE idScenario="+id;
        s[4] ="DELETE FROM piano WHERE idScenario="+id;
        s[5] ="DELETE FROM relazioneaz WHERE idScenario="+id;
        s[6] ="DELETE FROM relazionedisp WHERE idScenario="+id;
        s[7] ="DELETE FROM risorsa WHERE idScenario="+id;
        s[8] ="DELETE FROM stanza WHERE idScenario="+id;
        s[9] ="DELETE FROM utilizzoris WHERE idScenario="+id;
        s[10]="DELETE FROM alloggio WHERE idScenario="+id;
        return s;
    }
    
    private String[] buildQueryDuplica(int id,int newId){
        String [] s = new String[10];       
        s[0]="INSERT INTO alloggio (idScenario) SELECT '"+newId+"' FROM alloggio WHERE idScenario = '"+id+"'";
        s[1]="INSERT INTO azione (nome,durata,programmabile,nomeDisp,idScenario) SELECT nome,durata,programmabile,nomeDisp,"+newId+" FROM azione WHERE idScenario = '"+id+"'";
        s[2]="INSERT INTO dispositivo (nome,tipo,nomeAlloggio,nomePiano,nomeStanza,idScenario) SELECT nome,tipo,nomeAlloggio,nomePiano,nomeStanza,"+newId+" FROM dispositivo WHERE idScenario = '"+id+"'";
        s[3]="INSERT INTO evento (nome,abilitato,allarme,idAzione,idScenario) SELECT nome,abilitato,allarme,idAzione,"+newId+" FROM evento WHERE idScenario = '"+id+"'";
        s[4]="INSERT INTO piano (nome,livello,idScenario) SELECT nome,livello,"+newId+" FROM piano WHERE idScenario = '"+id+"'";
        s[5]="INSERT INTO relazioneaz (nomeAzC,nomeAzS,idScenario) SELECT nomeAzC,nomeAzS,"+newId+" FROM relazioneaz WHERE idScenario = '"+id+"'";
        s[6]="INSERT INTO relazionedisp (nomeCom,nomeSot,idScenario) SELECT nomeCom,nomeSot,"+newId+" FROM relazionedisp WHERE idScenario = '"+id+"'";
        s[7]="INSERT INTO risorsa (nome,limite,limiteTot,giorniRinnovo,idAlloggio,nomePiano,nomeStanza,nomeDisp,idScenario) SELECT nome,limite,limiteTot,giorniRinnovo,idAlloggio,nomePiano,nomeStanza,nomeDisp,"+newId+" FROM risorsa WHERE idScenario = '"+id+"'";
        s[8]="INSERT INTO stanza (nome,nomePiano,idScenario) SELECT nome,nomePiano,"+newId+" FROM stanza WHERE idScenario = '"+id+"'";
        s[9]="INSERT INTO utilizzoris (nomeAzione,nomeDisp,nomeRis,val,idScenario) SELECT nomeAzione,nomeDisp,nomeRis,val,"+newId+" FROM utilizzoris WHERE idScenario = '"+id+"'";
        return s;
    }
    
    public InfoScenario duplica(String nuovoNome){
        String query = this.buildQueryCrea(nuovoNome, BackYardApplicationController.getAppController().getUtente());
        int newId = BackYardApplicationController.getDBController().executeInsert(query);
        if(newId>0){
            String []query2 = this.buildQueryDuplica(id, newId);        
            for(int i=0;i<query2.length;i++)
                BackYardApplicationController.getDBController().executeUpdate(query2[i]);
            return new InfoScenario(newId,nuovoNome,this.getAutore());
        }
        return null;
    }
    
    public void elimina(){
        String[] query = this.buildQueryElimina(id);
        for(int i=0;i<query.length;i++)
            BackYardApplicationController.getDBController().executeUpdate(query[i]);
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getNome(){
        return nome;
    }
    
    public UserInfo getAutore(){
        return this.autore;
    }
    
    public int getId(){
        return this.id;
    }
    /*
    prima creo la entri nella tabella degli scenari; una volta ottenuto l'id vado a salvare le altre informazioni(ossia i contenuti dello scenario)
    */
    public String buildQueryCrea(String nuovoNome, UserInfo utente) {
        return "INSERT INTO scenario (nome, idAutore) VALUES ('"+nuovoNome+"','"+utente.getId()+"')";
    }
    
    public String toString(){
        return this.nome;
    }
    
}
