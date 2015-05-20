/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.backyard;

import java.util.ArrayList;

/**
 *
 * @author Gianmarco
 */
public class DISemplice extends DispositivoIntelligente{
    
    public DISemplice(String nome){
        super(nome);
    }
    
    public DISemplice getAsSemplice(){
        return (DISemplice)this;          
    }

    @Override
    public DIComplesso getAsComplesso() {        
        DIComplesso disp2 = new DIComplesso(this.getNome());
        disp2.setTipo(this.getTipo());
        disp2.setDove(this.getLuogo());        
        for(DispositivoIntelligente d: this.getUsatoIn()){
            ((DIComplesso)d).shallowReplace(this,disp2);
        }
        for(Azione az:this.getAzioni()){
            az.setDispositivo(disp2);
        }
        return disp2;
    }
}
