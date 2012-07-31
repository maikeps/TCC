/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author maike_p_santos
 */
public class Pokemon {
    
    protected int id;
    protected String nome;
    protected int raridade;
    protected int atkbase;
    protected int defBase;
    protected int spdbase;
    protected int hpBase;
    protected int levelQueEvolui;
    protected int elementoPrimario;
    protected int elementoSecundario;
    
    public Pokemon(){
        
    }
    
    public Pokemon(int id, String nome, int raridade, int atkBase, int defBase, int spdBase, int hpBase, int levelQueEvolui, int elementoPrimario, int elementoSecundario){
        this.id = id;
        this.nome = nome;
        this.raridade = raridade;
        this.atkbase = atkBase;
        this.defBase = defBase;
        this.spdbase = spdBase;
        this.hpBase = hpBase;
        this.levelQueEvolui = levelQueEvolui;
        this.elementoPrimario = elementoPrimario;
        this.elementoSecundario = elementoSecundario;
    }

    public int getAtkbase() {
        return atkbase;
    }

    public void setAtkbase(int atkbase) {
        this.atkbase = atkbase;
    }

    public int getDefBase() {
        return defBase;
    }

    public void setDefBase(int defBase) {
        this.defBase = defBase;
    }

    public int getElementoPrimario() {
        return elementoPrimario;
    }

    public void setElementoPrimario(int elementoPrimario) {
        this.elementoPrimario = elementoPrimario;
    }

    public int getElementoSecundario() {
        return elementoSecundario;
    }

    public void setElementoSecundario(int elementoSecundario) {
        this.elementoSecundario = elementoSecundario;
    }

    public int getHpBase() {
        return hpBase;
    }

    public void setHpBase(int hpBase) {
        this.hpBase = hpBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevelQueEvolui() {
        return levelQueEvolui;
    }

    public void setLevelQueEvolui(int levelQueEvolui) {
        this.levelQueEvolui = levelQueEvolui;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRaridade() {
        return raridade;
    }

    public void setRaridade(int raridade) {
        this.raridade = raridade;
    }

    public int getSpdbase() {
        return spdbase;
    }

    public void setSpdbase(int spdbase) {
        this.spdbase = spdbase;
    }

    
    
    
}
