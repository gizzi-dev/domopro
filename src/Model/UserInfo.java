/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Gianmarco
 */
public class UserInfo {
    public static Object tipoUtente;
    private int id;
    private String nome;
    private String password;
    private  tipoUtente tipoutente;
    
    public UserInfo(int id, String nome){
        this.id=id;
        this.nome = nome;
    }
    
    public UserInfo(String nome, tipoUtente tipo){
        this.nome = nome;
        this.tipoutente = tipo;
        this.id=1;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int getId(){
        return this.id;
    }
    
    public boolean checkTecnico(){       
        return tipoutente.TECNICO.equals(this.tipoutente);        
    }
}
