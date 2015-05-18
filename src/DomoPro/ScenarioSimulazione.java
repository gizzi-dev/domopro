

package DomoPro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gianmarco
 */
public class ScenarioSimulazione {
    private String nome;
    private Alloggio planimetria;
    private boolean salvato; 
    private InfoScenario info;
    private ArrayList<DispositivoIntelligente> dispositivi;  
    private UserInfo autore;
    private static ScenarioSimulazione istanza= null;
    private static ScenarioSimulazione importo;
    
    public static ScenarioSimulazione crea(String nome,UserInfo info){
        return new ScenarioSimulazione(nome,info); 
    }    

    /**
     * se salvato è true sto scaricando lo scenario da aprire che quindi va ad occupare
     * la variabile statica nella classe scenario simulazione. il valore false viene usato per caricare
     * uno scenario al solo fine di importare gli elementi.
     * in questo caso andrà ad occupare la variabile statica import
     * @param info
     * @param salvato
     * @return 
     */
    public static ScenarioSimulazione load(InfoScenario info, boolean salvato) throws Exception{
        ScenarioSimulazione s = new ScenarioSimulazione(info,salvato);   
        s.loadAlloggio();
        s.loadDispositivi();
        return s;        
    }
    
    private ScenarioSimulazione(String nome, UserInfo info){            
        this.nome=nome;
        this.autore = info;
        this.salvato = false; 
        this.dispositivi = new ArrayList<DispositivoIntelligente>(); 
        this.planimetria = new Alloggio();
        this.istanza = this;
        this.info = new InfoScenario(-1,nome,info);
    }
    
    private ScenarioSimulazione(InfoScenario info,boolean salvato) throws Exception{
        this.salvato = false;       
        this.dispositivi = new ArrayList<DispositivoIntelligente>();        
        this.info=info;  
        if(salvato)istanza = this;
        else importo = this;
        loadAlloggio();
        loadDispositivi();
    }
    
    //da fare
    private ArrayList<String> buildQuerySalva(int id){
        ArrayList<String> s = new ArrayList<String>();  
        s.add("INSERT INTO alloggio (idScenario) VALUES('"+id+"')");
        s.addAll(this.salvaAlloggio());
        s.addAll(this.salvaDispositivi());
        return s;
    }
    
    /*
    luoghi, risorse
    */
    private String[] buildQueryLoadAlloggio(){
        String[] query = new String [4];
        query[0] ="SELECT * FROM alloggio WHERE 'idScen'="+this.info.getId();
        query[1] ="SELECT * FROM piano WHERE 'idScen'="+this.info.getId();
        query[2] ="SELECT * FROM stanza WHERE 'idScen'="+this.info.getId();
        query[3] ="SELECT * FROM risorsa WHERE 'idScen'="+this.info.getId();
        return query;
    }
    
    /*
    risorse,azioni,eventi
    */
    private String[] buildQueryLoadDispositivi(){
        String[] query = new String [4];
        query[0]= "SELECT * FROM dispositivo WHERE idScen ="+this.info.getId();        
        query[1]= "SELECT * FROM azione WHERE 'idScen' ="+this.info.getId();   
        query[2]= "SELECT * FROM risorsa WHERE 'idScen'="+this.info.getId();
        query[3]= "SELECT * FROM relazionedisp WHERE 'idScen' ="+this.info.getId();       
        query[4]= "SELECT * FROM relazioneaz WHERE 'idScen' ="+this.info.getId()+" ORDER BY 'posizione' ASC";
        query[5]= "SELECT * FROM risorsa WHERE 'idScen' ="+this.info.getId();
        query[6]= "SELECT * FROM utilizzoris WHERE 'idScen' ="+this.info.getId();
        
        return null;
    }
    //alloggio, piani, stanze, risorse
    public ArrayList<String> salvaAlloggio(){
        ArrayList<String> query = new ArrayList<String>();
        query.add("INSERT INTO alloggio(idScenario) VALUES ('"+this.info.getId()+"')");
        for(Piano piano: this.getPiani()){
           query.add("INSERT INTO piano(nome,livello,idScenario) VALUES ('"+piano.getNome()+"','"+piano.getLivello()+"','"+this.info.getId()+"')"); 
            for(Stanza stanza:piano.getStanze()){
                query.add("INSERT INTO stanza(nome,nomePiano,idScenario) VALUES ('"+stanza.getNome()+"','"+piano.getNome()+"','"+this.info.getId()+"')");
            }
        }    
        for(Risorsa r: this.planimetria.getRisorseFornite()){
            query.add("INSERT INTO risorsa(nome,limite,limiteTot,giorniRinnovo,idAlloggio,nomePiano,nomeStanza,nomeDisp,idScenario)"
                    + " VALUES ('"+r.getNome()+"','"+r.getLimite()+"','"+r.getLimiteTot()+"','"+r.getGiorniRinnovo()+"',"
                    + "'"+((r.getCollocazione() instanceof Alloggio)? 1 : 0)+"',"
                    + "'"+this.nomePiano(r.getCollocazione())+"',"
                    + "'"+((r.getCollocazione() instanceof Stanza)? ((Stanza)r.getCollocazione()).getNome() : -1)+"',"
                    + "'-1','"+this.info.getId()+"')");                    
        }
        return query;
    }
    
    private String nomePiano(Collocazione c){
        if(c instanceof Piano) return ((Piano)c).getNome();
        else if(c instanceof Stanza) return ((Stanza)c).getPiano().getNome();
        else return "-1";
    }
    
    //disp,azioni,ris,relAzioni,relDisp,utilizzoRis
    private ArrayList<String> salvaDispositivi(){
        ArrayList<String> query = new ArrayList<String>();
        for(DispositivoIntelligente disp : this.dispositivi){
            query.add("INSERT INTO dispositivo (nome,nomeAlloggio,nomePiano,nomeStanza,idScenario) VALUES "
                    + "('"+disp.getNome()+"','"+((disp.getLuogo() instanceof Alloggio)? 1 : 0)+"',"
                    + "'"+((disp.getLuogo() instanceof Piano)? ((Piano)disp.getLuogo()).getNome() : -1)+"',"
                    + "'"+((disp.getLuogo() instanceof Stanza)? ((Stanza)disp.getLuogo()).getNome() : -1)+"',"
                    + "'-1'"
                    + "'"+this.info.getId()+"')");
            //Relazione tra dispositivi
            for(DispositivoIntelligente sottodisp: disp.getSottoDispositivi()){
                query.add("INSERT INTO dispositivo (nomeCom,nomeSot,idScenario) VALUES"
                    + " ('"+disp.getNome()+"',"
                    + "'"+sottodisp.getNome()+"',"
                    + "'"+this.info.getId()+"')");
            }
            //Azioni
            for(Azione az: disp.getAzioni()){
                query.add("INSERT INTO azione (nome,durata,programmabile,nomeDisp,idScenario) VALUES "
                        + "('"+az.getNome()+"',"
                        + "'"+az.getDurata()+"',"
                        + "'"+az.getProgrammabile()+"',"
                        + "'"+az.getDispositivo().getNome()+"',"
                        + "'"+this.info.getId()+"')");
                //Utilizzo Risorse
                for(Entry<Risorsa,Double> utilizzo: az.getUtilizzoRisorse().entrySet()){
                   Risorsa ris = utilizzo.getKey();
                   double val = utilizzo.getValue();
                   query.add("INSERT INTO utilizzoris (nomeAzione,nomeRis,nomeDisp,val,idScenario) VALUES "
                           + "('"+az.getNome()+"',"
                           + "'"+ris.getNome()+"',"
                           + "'"+disp.getNome()+"',"
                           + "'"+val+"',"
                           + "'"+this.info.getId()+"')");
                }
                //Relazione tra azioni
                for(Azione sottoaz: az.getSottoAzioni()){
                    query.add("INSERT INTO relazioneaz (nomeAzC,nomeAzS,idScenario) VALUES "
                           + "('"+az.getNome()+"',"
                           + "'"+sottoaz.getNome()+"',"                          
                           + "'"+this.info.getId()+"')");
                }
            }
            //Risorse fornite dal dispositivo
            for(Risorsa r: disp.getRisorse()){
                 query.add("INSERT INTO risorsa(nome,limite,lmiteTot,giorniRinnovo,idAlloggio,nomePiano,nomeStanza,nomeDisp,idScenario)"
                    + " VALUES ('"+r.getNome()+"','"+r.getLimite()+"','"+r.getLimiteTot()+"','"+r.getGiorniRinnovo()+"',"
                    + "'0',"
                    + "'-1',"
                    + "'-1',"
                    + "'"+disp.getNome()+"',"     
                    + "'"+this.info.getId()+"')");  
            }
        }
        return query;
    }
    
    
    /*
        popolo con i risultati alloggio
        *Qui si deve creare l'alloggio e quindi caricare gli oggetti che lo compongono, luoghi, risorse) l'operazione
        richiede diverse invocazioni la cui struttura è tuttavia ripetitiva.
        */
    public void loadAlloggio() throws Exception{
        try {
            String[] query = this.buildQueryLoadAlloggio();
            ResultSet[] risultati = new ResultSet[query.length];
            for(int i = 0;i<query.length;i++){
                risultati[i] = BackYardApplicationController.getDBController().executeQuery(query[i]);
                BackYardApplicationController.getDBController().stampaResult(risultati[i]);
                if(i==0){                
                    this.planimetria = new Alloggio();
                }
                else if(i==1){
                    while(risultati[1].next()){
                        String nome = risultati[1].getString("nome");
                        int livello = risultati[1].getInt("livello");
                        Piano piano = new Piano(nome,livello);                        
                        this.planimetria.addPiano(piano);
                        piano.deleteStanza(piano.getStanze().get(0));
                    }
                }
                else if(i==2){
                    while(risultati[2].next()){
                        String nome = risultati[2].getString("nome");
                        String nomePiano = risultati[2].getString("nomePiano");
                        Stanza stanza = new Stanza(nome);
                        for(Piano piano: this.getPiani()){
                            if(piano.getNome().equals(nomePiano)){
                                piano.addStanza(stanza);
                            }
                        }
                    }
                }
                else if(i==3){
                    while(risultati[3].next()){
                        String nome = risultati[3].getString("nome");
                        int limite = risultati[3].getInt("limite");
                        int limitetot = risultati[3].getInt("limiteTot");
                        int giorniRinnovo = risultati[3].getInt("giorniRinnovo");
                        boolean alloggio = risultati[2].getBoolean("idAlloggio");
                        String nomePiano = risultati[3].getString("nomePiano");
                        String nomeStanza= risultati[3].getString("nomeStanza");                        
                        Contesto cont = null;
                        if(alloggio) cont = this.planimetria;
                        else if(this.controllaNomePiano(nome)){
                            for(Piano piano: getPiani()){
                                if(piano.getNome().equals(nomePiano)) cont = (Contesto) piano;
                            }
                        }
                        else{
                            for(Piano piano: getPiani()){
                                for(Stanza stanza: piano.getStanze()){
                                    if(stanza.getNome().equals(nomeStanza))cont = (Contesto) stanza;
                                }
                            }
                        }
                        if(cont !=null){
                            Risorsa ris = this.creaRisorsa(nome, limite, limitetot, giorniRinnovo, cont);                        
                            if(cont instanceof Alloggio) ((Alloggio)cont).addRisorsa(ris);
                            else if(cont instanceof Piano) ((Piano)cont).addRisorsa(ris);
                            else if(cont instanceof Stanza) ((Stanza)cont).addRisorsa(ris);
                        }
                    }
                }
            }
            this.setSalvato(true);
        } catch (SQLException ex) {
            System.err.println("Errore nel caricare l'alloggio\n"+ex);
        }        
    }
    
    
        /*
        qui si devono creare i vari dispositivi e caricare gli oggetti a loro collegati(risorse,azioni,eventi)
        l'operazione richiede diverse invocazioni
        */
    /*
    ris,azioni,eventi,disp
    */
    private void loadDispositivi() throws Exception{
        try{
            String[] query = this.buildQueryLoadDispositivi();
            ResultSet[] risultati = new ResultSet[query.length];
            for(int i = 0;i<query.length;i++){
                risultati[i] = BackYardApplicationController.getDBController().executeQuery(query[i]);
                if(i==0){
                    //Dispositivi
                    while(risultati[0].next()){
                        String nome = risultati[i].getString("nome");
                        String tipo = risultati[i].getString("tipo");       
                        boolean alloggio = risultati[i].getBoolean("nomeAlloggio");       
                        String nomePiano =  risultati[i].getString("nomePiano");    
                        String nomeStanza =  risultati[i].getString("nomePiano");    
                        if(this.creaDispositivo(nome)){
                            DispositivoIntelligente disp = this.apriDispositivo(nome);
                            //disp.setTipo(tipo);
                            if(alloggio) disp.setDove(planimetria);
                            else if(nomePiano != "-1" && nomeStanza == "-1"){
                                disp.setDove(this.trovaPianoPerNome(nomePiano));
                            }
                            else if(nomePiano !="-1" && nomeStanza != "-1"){
                                for(Stanza stanza: this.richiediDettagliPiano(nomePiano).getStanze()){
                                    if(stanza.getNome().equals(nomeStanza)) disp.setDove(stanza);
                                }
                            }
                        }
                    }
                }        
                //Azioni
                else if(i==1){
                    String nome =risultati[i].getString("nome");
                    int durata = risultati[i].getInt("durata");
                    boolean prog = risultati[i].getBoolean("programmabile");
                    String nomeDisp = risultati[i].getString("nomeDisp");
                    DispositivoIntelligente disp = this.apriDispositivo(nome);
                    disp.creaNuovaAzione(nome);
                    Azione az = disp.richiediDettagliAzione(nome);                    
                    az.setDurata(durata);
                    az.setProgrammabilita(prog);
                }
                //Risorse dei dispositivi
                else if(i==2){
                    while(risultati[1].next()){
                        String nome = risultati[1].getString("nome");
                        int limite = risultati[1].getInt("limite");
                        int limitetot = risultati[1].getInt("limiteTot");
                        int giorniRinnovo = risultati[1].getInt("giorniRinnovo");                        
                        String nomeDisp = risultati[3].getString("nomeDisp");
                        if(nomeDisp != null){                           
                            DispositivoIntelligente disp = this.apriDispositivo(nome);
                            Risorsa ris = this.creaRisorsa(nome, limite, limitetot, giorniRinnovo, (Contesto)disp);
                            disp.addRisorsa(ris);
                        }
                    }
                }
                //Sottodisp
                else if(i==3){
                    String nomeCom=risultati[i].getString("nomeCom");
                    String nomeSot=risultati[i].getString("nomeSot");
                    DispositivoIntelligente dispCom = this.apriDispositivo(nomeCom);
                    DispositivoIntelligente dispSot = this.apriDispositivo(nomeSot);
                    if(dispCom != null && dispSot != null ){
                        dispCom.getAsComplesso().addSottoDispositivo(dispSot);                        
                    }
                }
                //sottoAz
                else if(i==4){
                    String nomeAzC=risultati[i].getString("nomeAzC");
                    String nomeAzS=risultati[i].getString("nomeAzS");
                    int pos = risultati[i].getInt("pos");
                    Azione azC = null;
                    Azione azS = null;
                    for(DispositivoIntelligente disp: this.dispositivi){
                        for(Azione az:disp.getAzioni()){
                            if(az.getNome().equals(nomeAzC))azC = az;
                            else if(az.getNome().equals(nomeAzS)) azS = az;
                        }
                    }
                    if(azC != null && azS != null){
                        azC.getAsComplessa().addSottoAzioneToPos(azS, pos);
                    }                    
                }
                //Utilizzo Risorse
                else if(i==5){
                    String nomeAzione = risultati[i].getString("nomeAzione");
                    String nomeDisp = risultati[i].getString("nomeDisp");
                    String nomeRisorsa =risultati[i].getString("nomeRis");
                    int val = risultati[i].getInt("val");
                    DispositivoIntelligente disp = this.apriDispositivo(nomeDisp);
                    if(disp != null){
                        Azione az = disp.richiediDettagliAzione(nomeAzione);
                        if(az!= null){
                            Risorsa ris = this.getRisorsaDaNome(nomeRisorsa);
                            if(ris != null ) disp.aggiungiUtilizzoRisorsa(az, ris, val);
                        }
                    }                            
                }                
            }
        }catch (SQLException ex) {
                    Logger.getLogger(ScenarioSimulazione.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public Alloggio getAlloggio(){
        return planimetria;
    }
    
    public boolean getSalvato(){
        return salvato;
    }
    
    public Risorsa getRisorsaDaNome(String nomeRis){
        ArrayList<Risorsa> ris = new ArrayList<Risorsa>();
        this.planimetria.collectRisorseAccessibili(ris);
        for(DispositivoIntelligente disp: dispositivi){
            ris.addAll(disp.getRisorse());
        }
        for(Risorsa r : ris){
            if(r.getNome().equals(nomeRis)) return r;
        }
        return null;
    }
    
    
    public ArrayList<DispositivoIntelligente> getDispositivi(){
        return dispositivi;
    }
    
    public void setNome(String nome){
        this.nome=nome;
    }
    
    public void setAlloggio(Alloggio alloggio){
        this.planimetria=alloggio;
    }
    
    public void setSalvato(boolean bol){
        this.salvato=bol;
    }    
    
    public boolean salva(){
        if(salvato) return false;
        if(getInfoScenario() != null){
            this.info.elimina();
        }
        String query = info.buildQueryCrea(this.nome,BackYardApplicationController.getAppController().getUtente());
        int nuovoid = BackYardApplicationController.getDBController().executeInsert(query);
        ArrayList<String> query2 = this.buildQuerySalva(nuovoid);
        for(String s: query2)
            BackYardApplicationController.getDBController().executeUpdate(s);
        info.setId(nuovoid);
        return true;
    }
   
    
    private Piano trovaPianoPerNome(String nome){
        for(Piano piano: getPiani()){
            if(piano.getNome().equals(nome)) return piano;
        }
        return null;
    }
    
    private boolean controllaNomeDispotivo(String nome){
        return (this.apriDispositivo(nome) != null ? true : false);
    }
    
    private boolean controllaNomePiano(String nome){
        return (trovaPianoPerNome(nome) != null ? true : false);
    }    
    
    /**
     * Controllo che il livello a cui voglio inserire il piano sia disponibile
     * @param lvl
     * @return 
     */
    private boolean controllaLivelloPiano(int lvl){
        for(Piano p: this.planimetria.getPiani()){
            if(lvl == p.getLivello()) return false;
        }
        return true;
    }
    
    /**
     * Controlo se al piano1 esiste già una stanza che si chiama "nome"
     * @param piano1
     * @param nome
     * @return 
     */
    private boolean controllaNomeStanza(Piano piano1,String nome){
        for(Piano piano: getPiani()){
            if(piano1.getNome().equals(piano.getNome())){
                for(Stanza stanza: piano.getStanze())
                    if(stanza.getNome().equals(nome)) return true;
            }
        }
        return false;
    }
    
    /**
     * Controlo se esiste già una risorsa con quel nome
     * @param nome
     * @return 
     */
    private boolean controllaNomeRisorsa(String nome){
        for(Risorsa risorsa: planimetria.getRisorse()){
            if(risorsa.getNome().equals(nome)) return true;
        }
        return false;
    }
    
    public InfoScenario getInfoScenario(){
        return this.info;
    }
    
    public void setInfoScenario(InfoScenario info){
        this.info = info;
    }
    
    /**
     * Prendo la lista dei piani della planimetria
     * @return lista piani 
     */
    public ArrayList<Piano> getPiani(){
        return planimetria == null ? new ArrayList<Piano>() : this.planimetria.getPiani();
    }    
    
    public boolean eliminaDispositivo(String nome) throws Exception{
        DispositivoIntelligente disp = this.apriDispositivo(nome);
        if(disp == null) throw new Exception("Non esiste un dispositivo con quel nome");
        if(disp.isSottoDispositivo())throw new Exception("E' usato da un altro dispositivo");
        this.dispositivi.remove(disp);
        setSalvato(false);
        return true;
    }
    
    public boolean duplicaDispositivo(String nome, String nuovoNome ) throws Exception{
        DispositivoIntelligente disp = this.apriDispositivo(nome);
        if(this.controllaNomeDispotivo(nuovoNome) && disp != null) throw new Exception("Impossibile Duplicare");
        DispositivoIntelligente dispNuovo = disp.clone();
        dispNuovo.setNome(nuovoNome);
        this.dispositivi.add(dispNuovo);
        setSalvato(false);
        return true;
    }
    
    
    public boolean modificaDispositivo(String nome, String nuovoNome) throws Exception{
        DispositivoIntelligente disp = this.apriDispositivo(nome);
        if(disp == null && this.controllaNomeDispotivo(nuovoNome)) throw new Exception("Impossibile modificare il dispositivo");
        disp.setNome(nuovoNome);
        setSalvato(false);
        return true;
    }
    
    public boolean modificaRisorsa(Risorsa risorsa, String nome, int limite) throws Exception{
        if(!this.controllaNomeRisorsa(nome)) throw new Exception();
        risorsa.setNome(nome);
        risorsa.setlimite(limite);
        setSalvato(false);
        return true;
    }
    
    public boolean modificaRisorsa(Risorsa risorsa, String nome, int limite,int limitetot,int rinnovo) throws Exception{
        if(!this.controllaNomeRisorsa(nome)) throw new Exception();
        risorsa.setNome(nome);
        risorsa.setlimite(limite);
        risorsa.setlimiteTot(limitetot);
        risorsa.setRinnovo(rinnovo);
        setSalvato(false);
        return true;
    }
    
    /**
     * Cerco tutti i luoghi in cui posso collocare un dispositivo
     * @return lista di luoghi
     */
    public ArrayList<Luogo> richiediCollocazionePerDispositivo(){
        return planimetria.getLuoghi();        
    }
    
    /**
     * Cerco tutti i luoghi e i dispositivi in cui posso collocare una risorsa
     * @return 
     */
    public ArrayList<Collocazione> richiediCollocazionePerRisorsa(){
        ArrayList<Collocazione> luoghi=new ArrayList<Collocazione>();
        luoghi.addAll(planimetria.getLuoghi());
        luoghi.addAll(this.dispositivi);
        return luoghi;        
    }
    
    public boolean assegnaCollocazione(DispositivoIntelligente disp,Luogo luogo){
        if(disp != null && luogo != null){
            if(disp.isSpostabile(luogo)){
                disp.setDove(luogo);
                setSalvato(false);
                return true;
            }
        }
        return false;
    }
    
    public boolean assegnaCollocazione(Risorsa risorsa,Collocazione colloc) throws Exception{
        if(risorsa == null || colloc == null) throw new Exception();
        boolean utilizzata= risorsa.isUtilizzata();
        if(!utilizzata){
            risorsa.getCollocazione().removeRisorsa(risorsa);
            colloc.addRisorsa(risorsa);
            risorsa.setCollocazione(colloc);
            setSalvato(false);
        }
        return !utilizzata;
    }
    
    public ArrayList<DispositivoIntelligente> richiediSottoDispositivi(DispositivoIntelligente dispositivo){
        return dispositivo == null ? null :dispositivo.getSottoDispositivi();
    }
    
    /**
     * Lista di tutti i dispositivi che non sono usati da altri dispositivi
     * @param dispositivo
     * @return 
     */
    public ArrayList<DispositivoIntelligente> richiediDispositiviUsabili(DispositivoIntelligente dispositivo){
        ArrayList<DispositivoIntelligente> elenco = new ArrayList<DispositivoIntelligente>();
        for(DispositivoIntelligente d: this.dispositivi){
           if(!d.usa(dispositivo)) elenco.add(d);
        }
        return elenco;
    }
    
    public void aggiungiComponente(DispositivoIntelligente disp,DispositivoIntelligente sottodisp){
        if(!disp.isDIComplesso()){
            disp.getAsComplesso().addSottoDispositivo(sottodisp);            
        }
        else{
            ((DIComplesso)disp).addSottoDispositivo(sottodisp);
        }
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
    }
    
    public boolean eliminaComponente(DispositivoIntelligente disp,DispositivoIntelligente sottoDisp){         
        if(disp.removeSottoDispositivo(sottoDisp)) return false;
        if(disp.getSottoDispositivi().size() ==0){
            this.dispositivi.add(disp.getAsSemplice());
            this.dispositivi.remove(disp);                        
        }
        BackYardApplicationController.getAppController().getScenarioCorrente().setSalvato(false);
        return true;
    }
    
    /**
     * Aggiungo un piano, controllo prima se il livello è disponibile
     * @param piano
     * @return 
     */
    public boolean importaPiano(Piano piano){
        for(Piano p: this.getPiani()){
            if(!this.controllaLivelloPiano(piano.getLivello())) return false;
        }        
        return this.planimetria.addPiano(piano);
    }    
    
    public boolean importaDispositivo(DispositivoIntelligente disp){
        if(this.controllaNomeDispotivo(disp.getNome())) return false;
        this.dispositivi.add(disp);
        return true;
    }
    
    public Piano aggiungiPiano(String nome, int lvl) throws Exception{
        if(planimetria == null ||nome == null || this.controllaNomePiano(nome) || lvl<0) throw new Exception("Il piano esiste gia'");
        Piano piano = new Piano(nome,lvl);
        this.planimetria.addPiano(piano);
        setSalvato(false);
        return piano;
    }
    
    public boolean eliminaPiano(String nomePiano){
        Piano piano = this.trovaPianoPerNome(nomePiano);
        if(piano == null) return false;
        boolean usato= true;
        for(DispositivoIntelligente disp : this.dispositivi){
            if(disp.inLuogo(piano)) usato= true;
            ArrayList<Luogo> elenco =disp.getLuogo().getLuoghi();
            for(Luogo luogo: elenco){
                 if(disp.inLuogo(luogo)) usato= true;
            }
            usato= false;
        }
        if(!usato){
            this.planimetria.deletePiano(piano);
            setSalvato(false);
        }
        return !usato;
    }
    
    public Piano richiediDettagliPiano(String nome){
        return this.trovaPianoPerNome(nome);
    }
    
    public Stanza apriStanza(Piano p,String nome){
        return p.getStanza(nome);
    }
    
    public boolean modificaPiano(Piano piano,String nome, int lvl) throws Exception{
        if(!(piano != null && lvl > 0 && !this.controllaNomePiano(nome) && !this.controllaLivelloPiano(lvl))) throw new Exception();
        piano.setNome(nome);
        piano.setLivello(lvl);
        this.setSalvato(false);
        return true;
    }
    
    public Stanza aggiungiStanza(Piano piano,String nomeStanza) throws Exception{
        if(piano == null || nomeStanza == null || this.controllaNomeStanza(piano, nomeStanza)) throw new Exception();
        Stanza stanza = new Stanza(nomeStanza);
        piano.addStanza(stanza);
        setSalvato(false);
        return stanza;       
    }
    
    public boolean modificaStanza(Stanza stanza,String nome) throws Exception{
        if(stanza == null || nome == "" || nome == null || this.controllaNomeStanza(stanza.getPiano(), nome)) throw new Exception();        
        stanza.setNome(nome);   
        setSalvato(true);
        return true;        
    }    
    
    public boolean eliminaStanza(Stanza stanza){
        boolean usata = false;
        for(DispositivoIntelligente disp: this.dispositivi){
            if(disp.inLuogo(stanza)) usata = true;             
        }
        if(usata){
            Piano piano = stanza.getPiano();
            piano.deleteStanza(stanza);
        }
        setSalvato(false);
        return !usata;
    }
    
    public boolean spostaStanza(Stanza stanza,Piano nuovoPiano){
        boolean valido = this.controllaNomeStanza(nuovoPiano, stanza.getNome());
        boolean usata = false;
        for(DispositivoIntelligente disp: this.dispositivi){
            if(disp.inLuogo(stanza)) usata = true;
        }
        if(!valido || usata) return false;
        else if(valido && !usata){
            Piano piano = stanza.getPiano();
            piano.deleteStanza(stanza);
            nuovoPiano.addStanza(stanza);
            setSalvato(true);
        }
        return true;
    }    
    
    /**
     * Viene creato un elenco di oggetti Risorsa che può contenere: tutte le risorse associate alla planimetria 
     * di s e ai dispositivi di s (se filtrare = falso) oppure solo le risorse associate al contesto (se filtrare = vero)
     * @param contesto
     * @param filtrare
     * @return una lista di risorse
     */
    public ArrayList<Risorsa> richiediRisorseEsistenti(Contesto contesto, boolean filtrare){
        if(filtrare) return contesto.getRisorseFornite();
        ArrayList<Risorsa> risultato = new ArrayList<Risorsa>();
        this.planimetria.collectRisorseAccessibili(risultato);
        for(DispositivoIntelligente disp: this.dispositivi){
            risultato.addAll(disp.getRisorse());
        }
        return risultato;
    }
    
    public Risorsa creaRisorsa(String nome,int limite, Collocazione coll) throws Exception{
        if(this.controllaNomeRisorsa(nome))  throw new Exception("Esiste gia' una risorsa con questo nome");
        Risorsa ris = new Risorsa(nome,coll,limite);
        coll.addRisorsa(ris);
        setSalvato(false);
        return ris;
    }
    public Risorsa creaRisorsa(String nome,int limite,int limiteTot,int rinnovo,Collocazione coll) throws Exception{
       if(this.controllaNomeRisorsa(nome)) throw new Exception("Esiste gia' una risorsa con questo nome");
       Risorsa ris = new Risorsa(nome,coll,limiteTot,limite,rinnovo);
       coll.addRisorsa(ris);
       setSalvato(false);
       return ris;
    }
    
    public boolean creaDispositivo(String nome) throws Exception{
        if(!this.controllaNomeDispotivo(nome)) throw new Exception("Dispositivo Esistente");     
        this.dispositivi.add(new DISemplice(nome));
        this.setSalvato(false);
        return true;
    }
    
    public DispositivoIntelligente apriDispositivo(String nome){
        for(DispositivoIntelligente disp: this.dispositivi){
            if(disp.getNome().equals(nome)) return disp;
        }
        return null;
    }
    
    
    
    
}
