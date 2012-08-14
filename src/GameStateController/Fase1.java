/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import Ataques.*;
import DAO.AtaqueDAO;
import DAO.PokemonDAO;
import DAO.PokemonInimigoDAO;
import DAO.PokemonLiberadoDAO;
import Insert.PokemonInimigoInsert;
import MySQL.MySQL;
import Personagens.Charmander;
import Personagens.Personagem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javaPlay2.GameStateController;
import javax.swing.JOptionPane;
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
    Personagem p;
    Personagem p2;
    
    public Fase1(CharacterSelect CharSelect){
        this.CharSelect = CharSelect;
    }

    public void load() {
        this.ataques = new ArrayList<Ataque>();
    }

    public void step(long timeElapsed) {
        this.player.step(timeElapsed);
        this.inimigo.step(timeElapsed);
        
        
//        this.player.setX(this.player.getPersonagem().spriteAtual.pegaLargura()/2);
//        this.player.setY(this.player.getPersonagem().spriteAtual.pegaAltura()/2);
//        
//        this.inimigo.setX(this.player.getPersonagem().spriteAtual.pegaLargura()/2);
//        this.inimigo.setY(this.player.getPersonagem().spriteAtual.pegaAltura()/2);
//        

        for (Ataque a : this.ataques) {
            a.step(timeElapsed);
        }

        this.lancaAtaques(); //metodo que lança os ataques tanto do player como do inimigo

        this.inimigo.setXPlayer(this.player.getX()); //atualiza as informacoes do player para o inimigo
        this.inimigo.setYPlayer(this.player.getY()); //atualiza as informacoes do player para o inimigo
    
    }

    public void draw(Graphics g) {
        //cria fonte
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("resources/fontes/PressStart2P.ttf"));
            f = f.deriveFont(16f);
            g.setFont(f);
        } catch (FontFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.exit(1);
        }
        
        //desenha retangulo preto em toda a tela
        g.fillRect(0, 0, 1000, 1000);

        //desenha os personagens e os ataques
        this.player.draw(g);
        this.inimigo.draw(g);

        for (Ataque a : this.ataques) {
            a.draw(g);
        }
        
        
        
        
        g.setColor(Color.white);
        g.fillRect(this.player.getX(), this.player.getY(), 10, 10);
        g.setColor(Color.black);

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
        //se o player atacou(clicou), verifica se pode atirar(cooldown <= 0) e adiciona o ataque à lista de ataques
        if (this.player.atacou == true) {
           if (this.player.personagem.podeAtirar()) {
                this.ataques.add(new DragonRage(this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()));
                this.player.personagem.setCooldownAtual();
            }
        }
        this.player.atacou = false;

        //se o inimigo atacou, verifica se pode atirar(cooldown <= 0) e adiciona o ataque à lista de ataques
        if (this.inimigo.atacou == true) {
            if (this.inimigo.personagem.podeAtirar()) {
                this.ataques.add(new DragonRage(this.inimigo.getX(), this.inimigo.getY(), this.player.getX(), this.player.getY(), this.inimigo.getAngulo(), this.inimigo.getPersonagem()));
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
        
        this.p = new Personagem(id, nome, atk, def, spd, hp);
        
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
        
        this.p2 = new Personagem(id, nome, atk, def, spd, hp);
        
        this.inimigo = new Inimigo(this.p2, this.player);

    }
}
