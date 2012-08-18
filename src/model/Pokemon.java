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
    protected int atkBase;
    protected int defBase;
    protected int spdBase;
    protected int hpBase;
    protected int levelQueEvolui;
    protected int elementoPrimario;
    protected int elementoSecundario;
    protected int baseExp;
    
    public Pokemon(){
        
    }
    
    public Pokemon(int id, String nome, int raridade, int atkBase, int defBase, int spdBase, int hpBase, int levelQueEvolui, int elementoPrimario, int elementoSecundario){
        this.id = id;
        this.nome = nome;
        this.raridade = raridade;
        this.atkBase = atkBase;
        this.defBase = defBase;
        this.spdBase = spdBase;
        this.hpBase = hpBase;
        this.levelQueEvolui = levelQueEvolui;
        this.elementoPrimario = elementoPrimario;
        this.elementoSecundario = elementoSecundario;
    }

    public int getAtkBase() {
        return atkBase;
    }

    public void setAtkBase(int atkBase) {
        this.atkBase = atkBase;
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
        return this.raridade;
    }

    public void setRaridade(int raridade) {
        this.raridade = raridade;
    }

    public int getSpdBase() {
        return spdBase;
    }

    public void setSpdBase(int spdBase) {
        this.spdBase = spdBase;
    }

    public int getBaseExp() {
        return baseExp;
    }

    public void setBaseExp(int baseExp) {
        this.baseExp = baseExp;
    }

    
    
    
}
