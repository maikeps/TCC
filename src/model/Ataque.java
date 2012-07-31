/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author maike_p_santos
 */
public class Ataque {
    
    protected int id;
    protected String nome;
    protected int atk;
    protected int elemento;
    
    public Ataque(){
        
    }
    
    public Ataque(int id, String nome, int atk, int elemento){
        this.id = id;
        this.nome = nome;
        this.atk = atk;
        this.elemento = elemento;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getElemento() {
        return elemento;
    }

    public void setElemento(int elemento) {
        this.elemento = elemento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
