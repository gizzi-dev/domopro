/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.domosym;

import model.backyard.DispositivoIntelligente;
import model.backyard.Evento;

/**
 *
 * @author Gianmarco
 */
public class DispositivoIscritto {
    private DispositivoIntelligente  dispositivo;
    private Evento ev;
    private boolean avviso;
    
    public DispositivoIscritto(DispositivoIntelligente disp){
        this.dispositivo = disp;
    }
    
    public void impostaAvviso(Evento ev){
        this.ev = ev;
        avviso = true;
    }
    
    public void impostaAllarme(Evento ev){
        this.ev= ev;
        avviso = false;
    }
    
    public DispositivoIntelligente getDispositivo(){
        return this.dispositivo;
    }
    
    public boolean isAvviso(){
        return avviso;
    }
    
    public boolean isAllarme(){
        return !avviso;
    }
}
