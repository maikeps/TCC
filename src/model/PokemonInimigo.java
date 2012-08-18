/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author maike_p_santos
 */
public class PokemonInimigo {

    int idPokemon;
    String tipo;
    int atk;
    int def;
    int spd;
    int hp;
    int lvl;
    String nome;

    public PokemonInimigo() {
    }

    public PokemonInimigo(int idPokemon, String tipo, int atk, int def, int spd, int hp, int lvl, String nome) {
        this.idPokemon = idPokemon;
        this.tipo = tipo;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        this.hp = hp;
        this.lvl = lvl;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
