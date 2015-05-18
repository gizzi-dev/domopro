/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomoPro;

import java.util.ArrayList;

/**
 *
 * @author Gianmarco
 */
public interface Collocazione {
    
    public ArrayList<Risorsa> getRisorse();
    
    public void collectRisorseAccessibili(ArrayList<Risorsa> risorse);
    
    public void addRisorsa(Risorsa risorsa);
    
    public void removeRisorsa(Risorsa risorsa);
}
