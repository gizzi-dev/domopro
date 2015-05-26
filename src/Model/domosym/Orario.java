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
public class Orario implements CondizioneAttivazione{
    private String ora;
    private String minuti;
    private String secondi;
    
    public Orario(String ora,String min,String sec){       
       this.ora= ora;
       this.minuti= min;
       this.secondi= sec;
    }
    
    public Orario(String ora){
        this.ora = ora.substring(0,2);
        this.minuti = ora.substring(3,5);
        this.secondi = ora.substring(6,8);        
    }
    
    public boolean validate(){
        try{
            if(Integer.parseInt(ora)<0 || Integer.parseInt(ora)>23) return false;
            if(Integer.parseInt(minuti)<0 || Integer.parseInt(minuti)>59) return false;
            if(Integer.parseInt(secondi)<0 || Integer.parseInt(minuti)>59) return false;
        }catch(NumberFormatException e){
            return false;
        }
        
        return true;
    }
    
    public String toString(){
        return ora+":"+minuti+":"+secondi;
    }  
    
    
}
