/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.backyard;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Gianmarco
 */
public class AzioneSemplice extends Azione {
    
    public AzioneSemplice(String nome){
        super(nome);
    }

    @Override
    public AzioneComplessa getAsComplessa() {        
        AzioneComplessa az = new AzioneComplessa(this.getNome());
        az.setDispositivo(this.getDispositivo());
        az.setDurata(this.getDurata());
        az.setEventi(this.getEventi());
        az.setProgrammabilita(this.getProgrammabile());
        az.setUtilizzoRisorse(this.getUtilizzoRisorse());
        az.setUsataIn(this.getUsataIn());
        for(Azione azi: this.getUsataIn()){
            azi.shallowReplace(this, az);
        }
        for(Map.Entry<Risorsa,Double> ris: this.getUtilizzoRisorse().entrySet()){
            ris.getKey().removeUtilizzo(az);                
            ris.getKey().addUtilizzo(az);               
        }
        return az;      
    }
    
    public AzioneSemplice getAsSemplice(){
       return this;
        
    }
    
    public void shallowReplace(Azione azione1,Azione azione2){
        
    }
    
    public ArrayList<Azione> getSottoAzioni(){       
        return null;
    }  
    
    
}
