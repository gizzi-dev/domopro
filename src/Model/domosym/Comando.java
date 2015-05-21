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
public class Comando {    
    private AzioneComando azCom;        
    
    
    public AzioneComando getAzioneComando(){
        return this.azCom;
    }         
    
    public void setAzione(AzioneComando az){
        this.azCom = az;
    }
    
   
}
