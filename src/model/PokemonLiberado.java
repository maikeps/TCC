/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author maike_p_santos
 */
public class PokemonLiberado {
    
    protected int idJogador;
    protected int idPokemon;
    protected int lvlQueChegou;
    protected int faseQueChegou;
    protected int inimigosDerrotados;
    protected int vezesQueZerouOJogo;
    protected int vezesDerrotasParaNPC;
    protected int totalDanoCausado;
    protected int atk;
    protected int def;
    protected int spd;
    protected int hp;
    protected int lvl;
    protected int exp;
    protected String nome;
    
    public PokemonLiberado(){
        
    }
    
    public PokemonLiberado(String nome, int idJogador, int idPokemon, int lvlQueChegou, int faseQueChegou, int inimigosDerrotados, int vezesQueZerouOJogo, int vezesDerrotasParaNPC, int totalDanoCausado, int atk, int def, int spd, int hp, int lvl, int exp){
        this.idJogador = idJogador;
        this.idPokemon = idPokemon;
        this.lvlQueChegou = lvlQueChegou;
        this.faseQueChegou = faseQueChegou;
        this.inimigosDerrotados = inimigosDerrotados;
        this.vezesQueZerouOJogo = vezesQueZerouOJogo;
        this.vezesDerrotasParaNPC = vezesDerrotasParaNPC;
        this.totalDanoCausado = totalDanoCausado;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        this.hp = hp;
        this.lvl = lvl;
        this.exp = exp;
        this.nome = nome;
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
     }

    public int getFaseQueChegou() {
        return faseQueChegou;
    }

    public void setFaseQueChegou(int faseQueChegou) {
        this.faseQueChegou = faseQueChegou;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public int getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }

    public int getInimigosDerrotados() {
        return inimigosDerrotados;
    }

    public void setInimigosDerrotados(int inimigosDerrotados) {
        this.inimigosDerrotados = inimigosDerrotados;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getLvlQueChegou() {
        return lvlQueChegou;
    }

    public void setLvlQueChegou(int lvlQueChegou) {
        this.lvlQueChegou = lvlQueChegou;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getTotalDanoCausado() {
        return totalDanoCausado;
    }

    public void setTotalDanoCausado(int totalDanoCausado) {
        this.totalDanoCausado = totalDanoCausado;
    }

    public int getVezesDerrotasParaNPC() {
        return vezesDerrotasParaNPC;
    }

    public void setVezesDerrotasParaNPC(int vezesDerrotasParaNPC) {
        this.vezesDerrotasParaNPC = vezesDerrotasParaNPC;
    }

    public int getVezesQueZerouOJogo() {
        return vezesQueZerouOJogo;
    }

    public void setVezesQueZerouOJogo(int vezesQueZerouOJogo) {
        this.vezesQueZerouOJogo = vezesQueZerouOJogo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
    

    
}
