/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author maike_p_santos
 */
public class PokemonDerrotado {
    
    int id;
    int idPokemon;
    int vezesDerrotado;
    
    public PokemonDerrotado(){
        
    }
    
    public PokemonDerrotado(int id, int idPokemon, int vezesDerrotado){
        this.id = id;
        this.idPokemon = idPokemon;
        this.vezesDerrotado = vezesDerrotado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }

    public int getVezesDerrotado() {
        return vezesDerrotado;
    }

    public void setVezesDerrotado(int vezesDerrotado) {
        this.vezesDerrotado = vezesDerrotado;
    }
    
    
    
}
