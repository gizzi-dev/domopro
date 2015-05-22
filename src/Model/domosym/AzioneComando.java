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
public interface AzioneComando {
    
    
    public void rimuoviDaComando(Comando com);
    
    public boolean setDurata(int durata);

    public int getDurata();
    
}
