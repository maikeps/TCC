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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javaPlay2.GameStateController;
import javaPlay2.Imagem;
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
    Personagem p;
    Personagem p2;
    Imagem img;
    Font f;

    public Fase1(CharacterSelect CharSelect) {
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
            this.f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("resources/fontes/PressStart2P.ttf"));
            this.f = this.f.deriveFont(10f);
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


        //desenha imagem do fundo(mapa)
        //nao vai ficar assim
        try {
            this.img = new Imagem("resources/map.png");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }
        img.drawZoomed(g, -50, -50, 5);

        //desenha os personagens e os ataques
        this.player.draw(g);
        this.inimigo.draw(g);

        for (Ataque a : this.ataques) {
            a.draw(g);
        }

        this.desenhaHealthBar(g);


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

                model.Ataque a = AtaqueDAO.getPoder(this.CharSelect.getPlayer1());
                String s = "Ataques."+a.getNome();
                try {
                    Class cls = Class.forName(s);
                    Class[] parameters = new Class[]{int.class, int.class, int.class, int.class, double.class, Personagem.class};
                    java.lang.reflect.Constructor con = cls.getConstructor(parameters);
                    Object o = con.newInstance(new Object[]{this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()});
                    System.out.println(o);
                    // Ataque at = (Ataque) cls.newInstance();
                   // at.setxInicial(this.player.getX());
                  //  at.setyInicial(this.player.getY());
                  //  at.setDestX(this.player.getDestX());
                   // at.setDestY(this.player.getDestY());
                   // at.setAngulo(this.player.getAngulo());
                   // at.setPersonagem(this.player.getPersonagem());
                    this.ataques.add((Ataque)o);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                    System.exit(1);
                } catch (IllegalAccessException ex2){
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex2.getMessage());
                    System.exit(1);
                } catch (InstantiationException ex3){
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex3.getMessage());
                    System.exit(1);
                } catch (NoSuchMethodException ex4){
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex4.getMessage());
                    System.exit(1);
                } catch (IllegalArgumentException ex){
                    
                }  catch (InvocationTargetException ex){
                    
                } catch(SecurityException ex){
                    
                }
               // this.ataques.add(new Class.(this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()));
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

    public void criaPlayer1() {

        PokemonLiberado pokemon = PokemonLiberadoDAO.getPokemonPeloNome(CharSelect.getPlayer1());

        String nome = pokemon.getNome();
        int id = pokemon.getIdPokemon();
        int atk = pokemon.getAtk();
        int def = pokemon.getDef();
        int spd = pokemon.getSpd();
        int hp = pokemon.getHp();
        int lvl = pokemon.getLvl();


        //fazer update na tabela PokemonLiberado com os stats novos


        hp += (((hp + 1 / 8 + 50) * lvl) / 50 + 10);
        atk += ((atk + 1 / 8 + 50) * lvl) / 50 + 5;
        def += ((def + 1 / 8 + 50) * lvl) / 50 + 5;
        spd += ((spd + 1 / 8 + 50) * lvl) / 50 + 5;

        this.p = new Personagem(id, nome, atk, def, spd, hp, lvl);

        this.player = new Player(p);



    }

    public void criaInimigo() {
        Pokemon pokemon = PokemonDAO.getPokemonPeloNome(CharSelect.getInimigo());

        String nome = pokemon.getNome();
        int id = pokemon.getId();
        int atk = pokemon.getAtkbase();
        int def = pokemon.getDefBase();
        int spd = pokemon.getSpdbase();
        int hp = pokemon.getHpBase();

        PokemonLiberado pl = PokemonLiberadoDAO.getPokemonPeloNome(CharSelect.getPlayer1());
        int lvl = pl.getLvl();

        hp += (((hp + 1 / 8 + 50) * lvl) / 50 + 10);
        atk += ((atk + 1 / 8 + 50) * lvl) / 50 + 5;
        def += ((def + 1 / 8 + 50) * lvl) / 50 + 5;
        spd += ((spd + 1 / 8 + 50) * lvl) / 50 + 5;

        String sql = "insert into pokemonInimigo "
                + "(idPokemon, tipo, atk, def, spd, hp, lvl) values"
                + "(\"" + id + "\", \"minion\", \"" + atk + "\", "
                + "\"" + def + "\", \"" + spd + "\", \"" + hp + "\", \"" + lvl + "\")";

        MySQL bd = new MySQL();
        boolean bool = bd.executaInsert(sql);

        this.p2 = new Personagem(id, nome, atk, def, spd, hp, lvl);

        this.inimigo = new Inimigo(this.p2, this.player);

    }

    public void desenhaHealthBar(Graphics g) {
        // HealthBar do player
        int hpInicial = this.player.getPersonagem().getHpInicial();
        int hp = this.player.getHp();
        int lvl = this.player.getPersonagem().getLvl();
        g.setColor(Color.white);
        g.drawString("LVL " + lvl, 98, 568);
        g.drawString("" + this.CharSelect.getPlayer1(), 98, 588);
        g.fillRect(98, 598, hpInicial + 4, 24);
        g.setColor(Color.green);
        g.fillRect(100, 600, hp, 20);
        g.drawString("HP: " + hp + "/" + hpInicial, 100, 650);

        // HealthBar do inimigo
        int hpInicialInimigo = this.inimigo.getPersonagem().getHpInicial();
        int hpInimigo = this.inimigo.getHp();
        int lvlInimigo = this.player.getPersonagem().getLvl();
        g.setColor(Color.white);
        g.drawString("LVL " + lvlInimigo, 598, 68);
        g.drawString("" + this.CharSelect.getInimigo(), 598, 88);
        g.fillRect(598, 98, hpInicialInimigo + 4, 24);
        g.setColor(Color.green);
        g.fillRect(600, 100, hpInimigo, 20);
        g.drawString("HP: " + hpInimigo + "/" + hpInicialInimigo, 600, 150);

    }
}
