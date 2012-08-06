/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import Ataques.*;
import DAO.PokemonDAO;
import DAO.PokemonInimigoDAO;
import DAO.PokemonLiberadoDAO;
import Insert.PokemonInimigoInsert;
import MySQL.MySQL;
import Personagens.Charmander;
import Personagens.PersonagemTeste;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javaPlay2.GameStateController;
import tcc.Inimigo;
import tcc.Player;


import model.Pokemon;
import model.PokemonInimigo;
import model.PokemonLiberado;

/**
 *
 * @author maike_p_santos
 */
public class Fase1 implements GameStateController {

    CharacterSelect CharSelect;
    Player player;
    Inimigo inimigo;
    ArrayList<Ataque> ataques;
    Charmander charmander;
    Charmander charmander2;
    PersonagemTeste p;
    PersonagemTeste p2;
    
    public Fase1(CharacterSelect CharSelect){
        this.CharSelect = CharSelect;
    }

    public void load() {
        this.ataques = new ArrayList<Ataque>();
    }

    public void step(long timeElapsed) {
        this.player.step(timeElapsed);
        this.inimigo.step(timeElapsed);

        for (Ataque a : this.ataques) {
            a.step(timeElapsed);
        }

        this.lancaAtaques();

        this.inimigo.setXPlayer(this.player.getX());
        this.inimigo.setYPlayer(this.player.getY());

    }

    public void draw(Graphics g) {
        g.fillRect(0, 0, 1000, 1000);

        this.player.draw(g);
        this.inimigo.draw(g);

        for (Ataque a : this.ataques) {
            a.draw(g);
        }

        g.setColor(Color.red);
        g.drawString("" + this.player.personagem.getCooldownAtual(), 500, 500);
    }

    public void start() {
       this.criaPlayer1();
       this.criaInimigo();
    }

    public void stop() {
    }

    public void unload() {
    }

    public void lancaAtaques() {
        if (this.player.atacou == true) {
            //problema aqui, cooldownAtual ta sempre menor que zero
           if (this.player.personagem.podeAtirar()) {
                this.ataques.add(new DragonRage(this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()));
                this.player.personagem.setCooldownAtual();
            }
        }
        this.player.atacou = false;

        if (this.inimigo.atacou == true) {
            if (this.inimigo.personagem.podeAtirar()) {
                this.ataques.add(new DragonRage(this.inimigo.getX(), this.inimigo.getY(), this.player.getX(), this.player.getY(), this.inimigo.getAngulo(), this.inimigo.getPersonagem()));
               // this.inimigo.personagem.setCooldownAtual();
                this.inimigo.personagem.cooldownAtual = this.inimigo.personagem.cooldown;
            }
        }
        this.inimigo.atacou = false;

    }
    
    
    
    public void criaPlayer1(){
        
        PokemonLiberado pokemon = PokemonLiberadoDAO.getPokemonPeloNome(CharSelect.getPlayer1());
        
        String nome = pokemon.getNome();
        int id = pokemon.getIdPokemon();
        int atk = pokemon.getAtk();
        int def = pokemon.getDef();
        int spd = pokemon.getSpd();
        int hp = pokemon.getHp();
        int lvl = pokemon.getLvl();
        
        
        //fazer update na tabela PokemonLiberado com os stats novos
        
        
        hp += (((hp + 1/8 + 50) * lvl)/50 + 10);
        atk += ((atk + 1/8 + 50) * lvl)/50 + 5;
        def += ((def + 1/8 + 50) * lvl)/50 + 5;
        spd += ((spd + 1/8 + 50) * lvl)/50 + 5;
        
        this.p = new PersonagemTeste(id, nome, atk, def, spd, hp);
        
        this.player = new Player(p);
        
        
        
    }
    
    public void criaInimigo(){
        Pokemon pokemon = PokemonDAO.getPokemonPeloNome(CharSelect.getInimigo());
        
        String nome = pokemon.getNome();
        int id = pokemon.getId();
        int atk = pokemon.getAtkbase();
        int def = pokemon.getDefBase();
        int spd = pokemon.getSpdbase();
        int hp = pokemon.getHpBase();
        
        PokemonLiberado pl = PokemonLiberadoDAO.getPokemonPeloNome(CharSelect.getPlayer1());
        int lvl = pl.getLvl();
        
        hp += (((hp + 1/8 + 50) * lvl)/50 + 10);
        atk += ((atk + 1/8 + 50) * lvl)/50 + 5;
        def += ((def + 1/8 + 50) * lvl)/50 + 5;
        spd += ((spd + 1/8 + 50) * lvl)/50 + 5;
        
        String sql = "insert into pokemonInimigo "
                + "(idPokemon, tipo, atk, def, spd, hp, lvl) values"
                + "(\""+id+"\", \"minion\", \""+atk+"\", "
                + "\""+def+"\", \""+spd+"\", \""+hp+"\", \""+lvl+"\")";
        
        MySQL bd = new MySQL();
        boolean bool = bd.executaInsert(sql);
        
        this.p2 = new PersonagemTeste(id, nome, atk, def, spd, hp);
        
        this.inimigo = new Inimigo(this.p2, this.player);

    }
}
