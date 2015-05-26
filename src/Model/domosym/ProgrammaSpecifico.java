/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.domosym;

/**
 *
 * @author Gianmarco
 */
public class ProgrammaSpecifico extends Programma{
    private boolean attivato;
    
    public ProgrammaSpecifico(String nome) {
        super(nome);
    }
    
    public void setAttivato(boolean b){
        this.attivato = b;
    }
    
    public boolean getAttivato(){        
        return attivato;
    }
    
    public  boolean isGenerico(){
        return false;
    }
    
    @Override
    public boolean usato(){
        return false;
    }

    @Override
    public String buildQuerySalva() {
        return "UPDATE programma SET nome='"+this.getNome()+"',idSimulazione='"+DomoSymApplicationController.appCtrl.getSimCorrente().getId()+"'"
                + ",specifico='1',attivato='"+(this.attivato ? 1 : 0)+"' WHERE id="+this.getId();
    }
    
    public String buildQuerySalvaInsert(){
        return "INSERT INTO programma(nome,idSimulazione,specifico,attivato)"
                + " VALUES ('"+this.getNome()+"','"+DomoSymApplicationController.appCtrl.getSimCorrente().getId()+"','1','"+(this.attivato ? 1 :0)+"')";
    }
    
    public String toString(){
        if(this.getSimulazione().simula != null && this.getSimulazione().simula.equals(this))
            return "<html><b style='color:red'>"+this.getNome()+"(S)(ACTIVE)</b></html>";
        else return "<html><b style='color:green'>"+this.getNome()+"(S)</b></html>";
    }
}
