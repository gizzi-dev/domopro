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
    
    
    
    @Override
    public boolean usato(){
        return false;
    }

    @Override
    public String buildQuerySalva() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
