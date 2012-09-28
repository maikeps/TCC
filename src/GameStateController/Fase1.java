/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import Ataques.*;
import DAO.AtaqueDAO;
import DAO.PokemonDAO;
import DAO.PokemonDerrotadoDAO;
import DAO.PokemonLiberadoDAO;
import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import Personagens.Personagem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay2.GameEngine;
import javaPlay2.GameStateController;
import javaPlay2.Scene;
import javaPlayExtras.PerlinNoise2D;
import javax.swing.JOptionPane;
import tcc.Inimigo;
import tcc.Player;
import model.Pokemon;
import model.PokemonDerrotado;
import model.PokemonLiberado;

/**
 *
 * @author maike_p_santos
 */
public class Fase1 implements GameStateController {

    CharacterSelect CharSelect;
    Player player;
    ArrayList<Inimigo> inimigo;
    ArrayList<Ataque> ataquesPlayer;
    ArrayList<Ataque> ataquesInimigo;
    Personagem p;
    Personagem p2;
    Font f;
    Inimigo inimigoMaisPerto;
    double distanciaAteOInimigoMaisPerto = 9999;
    Scene cenario;

    public Fase1(CharacterSelect CharSelect) {
        this.CharSelect = CharSelect;
    }

    public void load() {
        this.ataquesPlayer = new ArrayList<Ataque>();
        this.ataquesInimigo = new ArrayList<Ataque>();
        this.inimigo = new ArrayList<Inimigo>();

    }

    public void step(long timeElapsed) {

        System.out.println(this.player.personagem.getPontoMin() + " - " + this.player.personagem.getPontoMax());
        
        this.verificaInimigoMaisPerto();

//        //this.xMouse = this.player.getPersonagem().xMouse;
//        this.xMouse = this.player.getXMouse();
//        this.yMouse = this.player.getYMouse();

        this.verificaSePlayerEstaMorto();
        this.verificaSeInimigoEstaMorto();


        this.player.step(timeElapsed);
        for (Inimigo inimigo : this.inimigo) {
            inimigo.setXPlayer(this.player.personagem.getX());
            inimigo.setYPlayer(this.player.personagem.getY());
            //inimigo.personagem.setX(inimigo.personagem.getX() + player.offsetx);
            //inimigo.personagem.setY(inimigo.personagem.getY() + player.offsety);
            inimigo.step(timeElapsed);
        }


//        this.player.setX(this.player.getPersonagem().spriteAtual.pegaLargura()/2);
//        this.player.setY(this.player.getPersonagem().spriteAtual.pegaAltura()/2);
//        
//        this.inimigo.setX(this.player.getPersonagem().spriteAtual.pegaLargura()/2);
//        this.inimigo.setY(this.player.getPersonagem().spriteAtual.pegaAltura()/2);
//        

        for (Ataque a : this.ataquesPlayer) {
            a.step(timeElapsed);
        }
        for (Ataque a : this.ataquesInimigo) {
            a.step(timeElapsed);
        }

        this.lancaAtaques(); //metodo que lança os ataques tanto do player como do inimigo

        for (Inimigo inimigo : this.inimigo) {
            inimigo.setXPlayer(this.player.getX()); //atualiza as informacoes do player para o inimigo
            inimigo.setYPlayer(this.player.getY()); //atualiza as informacoes do player para o inimigo
        }

        this.verificaColisao();
        cenario.step(timeElapsed);



    }

    public void draw(Graphics g) {

        g.fillRect(0, 0, GameEngine.getInstance().getGameCanvas().getWidth(), GameEngine.getInstance().getGameCanvas().getHeight());

        cenario.draw(g, player.offsetx, player.offsety);

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

        //desenha os personagens e os ataques
        for (Inimigo inimigo : this.inimigo) {
            inimigo.draw(g);
        }
        this.player.draw(g);

        for (Ataque a : this.ataquesPlayer) {
            a.draw(g);
        }
        for (Ataque a : this.ataquesInimigo) {
            a.draw(g);
        }

        this.desenhaHealthBar(g);

        for (Ataque a : this.ataquesPlayer) {
            a.draw(g);
        }
        g.setColor(Color.black);

        for (Ataque a : this.ataquesInimigo) {
            if (a.acertou == true) {
                if (a.getContador() >= 25) {
                    // return;
                } else {
                    g.setColor(Color.red);
                    g.drawString("" + a.getDano(), a.getX(), a.getY());
                }
            }
        }
        for (Ataque a : this.ataquesPlayer) {
            if (a.acertou == true) {
                if (a.getContador() >= 25) {
                    // return;
                } else {
                    g.setColor(Color.white);
                    g.drawString("" + a.getDano(), a.getX(), a.getY());
                }
            }
        }
        for (int i = 0; i < this.inimigo.size(); i++) {
            double dist = this.inimigo.get(i).calculaDistanciaAtePonto(this.player.personagem.getX(), this.player.personagem.getY());
            //g.drawString("distancia: " + dist, 100, 100 + 50 * i);
            g.drawString("destX: " + this.inimigo.get(i).getDistanciaX(), 100, 100 + 50 * i);
        }
        g.drawString("xPlayer:" + this.player.personagem.getX(), 500, 500);

        this.desenhaLinhaAteInimigoMaisPerto(g);
    }

    public void start() {
        this.carregaMapa();


        this.criaPlayer1(1000, 1000);
        //cria 2 inimigos
        int rand = util.Util.random(45) + 1;
        rand += 15; //minimo 15, max 60 inimigos
        for (int i = 1; i <= rand; i++) {
            this.criaInimigo(this.CharSelect.getInimigo());
            this.CharSelect.sorteiaInimigo();
        }
        this.verificaInimigoMaisPerto();


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
                String s = "Ataques." + a.getNome();
                try {
                    Class cls = Class.forName(s);
                    Class[] parameters = new Class[]{int.class, int.class, int.class, int.class, double.class, Personagem.class};
                    java.lang.reflect.Constructor con = cls.getConstructor(parameters);
                    Object o = con.newInstance(new Object[]{this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()});
                    this.ataquesPlayer.add((Ataque) o);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: classe " + ex.getMessage() + " não encontrada");
                    System.exit(1);
                } catch (IllegalAccessException ex2) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex2.getMessage());
                    System.exit(1);
                } catch (InstantiationException ex3) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex3.getMessage());
                    System.exit(1);
                } catch (NoSuchMethodException ex4) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex4.getMessage());
                    System.exit(1);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                    System.exit(1);
                } catch (InvocationTargetException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                    System.exit(1);
                } catch (SecurityException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                    System.exit(1);
                }
                // this.ataques.add(new Class.(this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()));

                this.player.personagem.setCooldownAtual();
            }
        }

        this.player.atacou = false;

        //---------------------\\

        for (int i = 0; i < this.inimigo.size(); i++) {
            Inimigo inimigo = this.inimigo.get(i);
////////////////            if (inimigo.getPersonagem().getAtaque() == null) {
////////////////                System.out.println("problema, man");
////////////////            }
            if (inimigo.atacou == true) {
                if (inimigo.personagem.podeAtirar()) {
                    model.Ataque a = AtaqueDAO.getPoder(inimigo.getPersonagem().getNome());
                    String s = "Ataques." + a.getNome();
                    try {
                        Class cls = Class.forName(s);
                        Class[] parameters = new Class[]{int.class, int.class, int.class, int.class, double.class, Personagem.class};
                        java.lang.reflect.Constructor con = cls.getConstructor(parameters);
                        Object o = con.newInstance(new Object[]{inimigo.getX(), inimigo.getY(), player.getX(), player.getY(), inimigo.getAngulo(), inimigo.getPersonagem()});
                        this.ataquesInimigo.add((Ataque) o);
                    } catch (ClassNotFoundException ex) {
                        this.inimigo.remove(i);
                        this.CharSelect.sorteiaInimigo();
                        this.criaInimigo(this.CharSelect.getInimigo());
                        JOptionPane.showMessageDialog(null, "ERROR: classe " + ex.getMessage() + " não encontrada, vamos trocar o pokemon.");
                        //System.exit(1);
                    } catch (IllegalAccessException ex2) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + ex2.getMessage());
                        System.exit(1);
                    } catch (InstantiationException ex3) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + ex3.getMessage());
                        System.exit(1);
                    } catch (NoSuchMethodException ex4) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + ex4.getMessage());
                        System.exit(1);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                        System.exit(1);
                    } catch (InvocationTargetException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                        System.exit(1);
                    } catch (SecurityException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
                        System.exit(1);
                    }
                    // this.ataques.add(new Class.(this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()));

                    inimigo.personagem.setCooldownAtual();
                }
            }
            inimigo.atacou = false;
        }
    }

    public void criaPlayer1(int xSpawn, int ySpawn) {

        PokemonLiberado pokemon = PokemonLiberadoDAO.getPokemonPeloNome(CharSelect.getPlayer1());
        Pokemon poke = PokemonDAO.getPokemonPeloNome(CharSelect.getPlayer1());

        String nome = pokemon.getNome();
        int id = pokemon.getIdPokemon();
        int atk = poke.getAtkBase();
        int def = poke.getDefBase();
        int spd = poke.getSpdBase();
        int hp = poke.getHpBase();
        int lvl = pokemon.getLvl();



        //fazer update na tabela PokemonLiberado com os stats novos
        //arrumar por que nao ta zerando os stats
        //aumenta cada vez que eu iniciar o jogo

        hp += (((hp + 1 / 8 + 50) * lvl) / 50 + 10);
        atk += ((atk + 1 / 8 + 50) * lvl) / 50 + 5;
        def += ((def + 1 / 8 + 50) * lvl) / 50 + 5;
        spd += ((spd + 1 / 8 + 50) * lvl) / 50 + 5;

        String sql = "update pokemonLiberado set "
                + "hp = " + hp + ", "
                + "atk = " + atk + ", "
                + "def = " + def + ", "
                + "spd = " + spd + " "
                + "where idPokemon = " + id;

        MySQL bd = new MySQL();
        boolean bool = bd.executaUpdate(sql);

        this.p = new Personagem(id, nome, atk, def, spd, hp, lvl);

        this.player = new Player(this.p, xSpawn, ySpawn);



    }

    public void criaInimigo(String nome) {
        Pokemon pokemon = PokemonDAO.getPokemonPeloNome(nome);

        int id = pokemon.getId();
        int atk = pokemon.getAtkBase();
        int def = pokemon.getDefBase();
        int spd = pokemon.getSpdBase();
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

        int x = util.Util.random(this.cenario.getWidth());
        int y = util.Util.random(this.cenario.getHeight());
        Inimigo inimigo = new Inimigo(this.p2, this.player, x, y);
        this.inimigoMaisPerto = inimigo;
        this.inimigo.add(inimigo);
    }

    public void desenhaHealthBar(Graphics g) {
        // HealthBar do player
        int hpInicial = this.player.getPersonagem().getHpInicial();
        int hp = this.player.getHp();
        int lvl = this.player.getPersonagem().getLvl();


//////////        JProgressBar barraHpPlayer = new JProgressBar();
//////////        barraHpPlayer.setLocation(100, 600);
//////////        barraHpPlayer.setString("HP: " + hp + "/" + hpInicial);
//////////        barraHpPlayer.setForeground(Color.green);
//////////        barraHpPlayer.setMaximum(hpInicial);
//////////        barraHpPlayer.setPreferredSize(new Dimension(100, 10));
//////////        barraHpPlayer.setValue(hp);
//////////        barraHpPlayer.setVisible(true);
//////////        GameEngine.getInstance().getGameCanvas().add(barraHpPlayer);
//////////        GameEngine.getInstance().getGameCanvas().pack();
//////////        
//////////        
//////        

        g.setColor(Color.white);
        g.drawString("LVL " + lvl, 98 - this.player.offsetx, 568 - this.player.offsety);
        g.drawString("" + this.CharSelect.getPlayer1(), 98 - this.player.offsetx, 588 - this.player.offsety);
        g.fillRect(98 - this.player.offsetx, 598 - this.player.offsety, hpInicial + 4, 24);
        g.setColor(Color.green);
        g.fillRect(100 - this.player.offsetx, 600 - this.player.offsety, hp, 20);
        g.drawString("HP: " + hp + "/" + hpInicial, 100 - this.player.offsetx, 650 - this.player.offsety);

        // HealthBar do inimigo
        int hpInicialInimigo = this.inimigoMaisPerto.getPersonagem().getHpInicial();
        int hpInimigo = this.inimigoMaisPerto.getHp();
        int lvlInimigo = player.getPersonagem().getLvl();
        g.setColor(Color.white);
        g.drawString("LVL " + lvlInimigo, 598 - this.player.offsetx, 68 - this.player.offsety);
        g.drawString("" + this.inimigoMaisPerto.getPersonagem().getNome(), 598 - this.player.offsetx, 88 - this.player.offsety);
        g.fillRect(598 - this.player.offsetx, 98 - this.player.offsety, hpInicialInimigo + 4, 24);
        g.setColor(Color.green);
        g.fillRect(600 - this.player.offsetx, 100 - this.player.offsety, hpInimigo, 20);
        g.drawString("HP: " + hpInimigo + "/" + hpInicialInimigo, 600 - this.player.offsetx, 150 - this.player.offsety);
    }

    //o problema esta na imagem do ataque, por que se fizer colisao de player com inimigo funciona
    //so da erro se a imagem for rotacionada
    //ember funciona perfeitamente
    public void verificaColisao() {
        //colisao ataque inimigo com player
        for (Inimigo inimigo : this.inimigo) {
            for (Ataque a : this.ataquesInimigo) {
                int x1 = this.player.getX(), x2 = this.player.getPersonagem().spriteAtual.pegaLargura(), y1 = this.player.getY(), y2 = this.player.getPersonagem().spriteAtual.pegaAltura();

                if (a.getShape().intersects(x1, y1, x2, y2)) {
                    if (a.desativado == false) {
                        int lvl = inimigo.personagem.getLvl();
                        int danoDoAtk = a.getDanoBruto();
                        int atkDoPokemon = inimigo.personagem.getAtk();
                        int defDoOponente = this.player.personagem.getDef();
                        int r = 100 - util.Util.random(15);
                        int multiplicador = 1; //fazer busca no banco
                        int dano = (((((((lvl * 2 / 5) + 2) * danoDoAtk * atkDoPokemon / 50) / defDoOponente) + 2) * r / 100) * multiplicador);
                        a.setDano(dano);
                        System.out.println(this.CharSelect.getPlayer1() + " took " + dano + " damage!");
                        this.player.personagem.perdeHp(dano);
                    }
                    a.setAcertou(true);
                    a.desativado();
                    //pra mandar pro sei-la-o-que garbage collector
                    // a = null;
                }


//////////////            Point colisao = this.player.personagem.spriteAtual.temColisao(a.imagem);
//////////////            if (colisao != null) {
//////////////                if (a.desativado == false) {
//////////////                    int lvl = this.inimigo.personagem.getLvl();
//////////////                    int danoDoAtk = a.getDano();
//////////////                    int atkDoPokemon = this.inimigo.personagem.getAtk();
//////////////                    int defDoOponente = this.player.personagem.getDef();
//////////////                    int r = 100 - util.Util.random(15);
//////////////                    int multiplicador = 1; //fazer busca no banco
//////////////                    int dano = (((((((lvl * 2 / 5) + 2) * danoDoAtk * atkDoPokemon / 50) / defDoOponente) + 2) * r / 100) * multiplicador);
//////////////                    System.out.println(this.CharSelect.getPlayer1() + " took " + dano + " damage!");
//////////////                    this.player.personagem.perdeHp(dano);
//////////////                }
//////////////                a.acertou = true;
//////////////                a.desativado();
//////////////                //pra mandar pro sei-la-o-que garbage collector
//////////////                a = null;
//////////////            }
            }
        }

        //colisao ataque player com inimigo

        for (Inimigo inimigo : this.inimigo) {
            for (Ataque a : this.ataquesPlayer) {
                int x1 = inimigo.getX(), x2 = inimigo.getPersonagem().spriteAtual.pegaLargura(), y1 = inimigo.getY(), y2 = inimigo.getPersonagem().spriteAtual.pegaAltura();

                if (a.getShape().intersects(x1, y1, x2, y2)) {
                    if (a.desativado == false) {
                        int lvl = this.player.personagem.getLvl();
                        int danoDoAtk = a.getDanoBruto();
                        int atkDoPokemon = this.player.personagem.getAtk();
                        int defDoOponente = inimigo.personagem.getDef();
                        int r = 100 - util.Util.random(15);
                        int multiplicador = 1; //fazer busca no banco
                        int dano = (((((((lvl * 2 / 5) + 2) * danoDoAtk * atkDoPokemon / 50) / defDoOponente) + 2) * r / 100) * multiplicador);
                        a.setDano(dano);
                        System.out.println(inimigo.getPersonagem().getNome() + " took " + dano + " damage!");
                        inimigo.personagem.perdeHp(dano);



                        //faz update no campo totalDanoCausado

                        int idPlayer = this.player.personagem.getId();
                        PokemonLiberado pokeLiberado = PokemonLiberadoDAO.getPokemon(idPlayer);
                        int danoTotal = pokeLiberado.getTotalDanoCausado();
                        int danoTotalDepois = danoTotal + dano;


                        MySQL banco = new MySQL();
                        String sql = "update pokemonLiberado set totalDanoCausado = " + danoTotalDepois + " where idPokemon = " + idPlayer;
                        boolean bool = banco.executaUpdate(sql);

                    }
                    a.setAcertou(true);
                    a.desativado();
                    //a = null;
                }



////////////
////////////            Point colisao = this.inimigo.personagem.spriteAtual.temColisao(a.imagem);
////////////            if (colisao != null) {
////////////                if (a.desativado == false) {
////////////                    int lvl = this.player.personagem.getLvl();
////////////                    int danoDoAtk = a.getDano();
////////////                    int atkDoPokemon = this.player.personagem.getAtk();
////////////                    int defDoOponente = this.inimigo.personagem.getDef();
////////////                    int r = 100 - util.Util.random(15);
////////////                    int multiplicador = 1; //fazer busca no banco
////////////                    int dano = (((((((lvl * 2 / 5) + 2) * danoDoAtk * atkDoPokemon / 50) / defDoOponente) + 2) * r / 100) * multiplicador);
////////////                    System.out.println(this.CharSelect.getInimigo() + " took " + dano + " damage!");
////////////                    System.out.println(atkDoPokemon);
////////////                    System.out.println(defDoOponente);
////////////                    this.inimigo.personagem.perdeHp(dano);
////////////
////////////
////////////
////////////                    //faz update no campo totalDanoCausado
////////////
////////////                    int idPlayer = this.player.personagem.getId();
////////////                    PokemonLiberado pokeLiberado = PokemonLiberadoDAO.getPokemon(idPlayer);
////////////                    int danoTotal = pokeLiberado.getTotalDanoCausado();
////////////                    int danoTotalDepois = danoTotal + dano;
////////////
////////////
////////////                    MySQL banco = new MySQL();
////////////                    String sql = "update pokemonLiberado set totalDanoCausado = " + danoTotalDepois + " where idPokemon = " + idPlayer;
////////////                    boolean bool = banco.executaUpdate(sql);
////////////
////////////                }
////////////                a.acertou = true;
////////////                a.desativado();
////////////                a = null;
////////////            }
            }
        }
    }

    public void verificaSePlayerEstaMorto() {
        if (this.player.personagem.estaMorto()) {
            //reseta os stats
            Pokemon p = PokemonDAO.getPokemon(this.player.personagem.getId());
            PokemonLiberado pl = PokemonLiberadoDAO.getPokemon(this.player.personagem.getId());
            int atk = p.getAtkBase();
            int def = p.getDefBase();
            int spd = p.getSpdBase();
            int hp = p.getHpBase();
            int lvl = 1;
            int exp = 0;
            int vezesDerrotasParaNPC = pl.getVezesDerrotasParaNPC() + 1;
            int lvlQueChegou = this.player.personagem.getLvl();

            MySQL banco = new MySQL();
            String sql = "update pokemonLiberado "
                    + "set atk = " + atk + ", "
                    + "def = " + def + ", "
                    + "spd = " + spd + ", "
                    + "hp = " + hp + ", "
                    + "lvl = " + lvl + ", "
                    + "exp = " + exp + ", "
                    + "vezesDerrotasParaNPC = " + vezesDerrotasParaNPC + ", "
                    + "lvlQueChegou = " + lvlQueChegou + " "
                    + "where idPokemon = " + this.player.personagem.getId();
            boolean bool = banco.executaUpdate(sql);

            JOptionPane.showMessageDialog(null, "Você perdeu, escolha outro personagem e tente novamente.");
            System.exit(1);
        }
    }

    public void verificaSeInimigoEstaMorto() {
        for (int i = 0; i < this.inimigo.size(); i++) {
            Inimigo inimigo = this.inimigo.get(i);
            if (inimigo.personagem.estaMorto()) {
                int idPlayer = this.player.personagem.getId();
                int idInimigo = inimigo.personagem.getId();
                int lvlInimigo = inimigo.personagem.getLvl();

                //pega as informacoes do inimigo e calcula a exp a ser adquirida
                Pokemon pokeInimigo = PokemonDAO.getPokemon(inimigo.personagem.getId());
                int expBase = pokeInimigo.getBaseExp();
                int expGanha = (expBase * lvlInimigo) / 7;

                //altera o campo exp do pokemonLiberado no banco
                PokemonLiberado pokeLiberado = PokemonLiberadoDAO.getPokemon(this.player.personagem.getId());
                int exp = expGanha + pokeLiberado.getExp();
                int lvlPlayer = pokeLiberado.getLvl();

                MySQL banco = new MySQL();
                String sql = "update PokemonLiberado set exp = " + exp + " where idPokemon = " + this.player.personagem.getId();
                boolean bool = banco.executaUpdate(sql);

                //verifica se o pokemon ganhou um level
                sql = "select * from experiencia where lvl = " + (lvlPlayer + 1);
                ConjuntoResultados linhas = banco.executaSelect(sql);
                int expProxLvlPlayer = 0;
                if (linhas.next()) {
                    expProxLvlPlayer = linhas.getInt("exp");
                }

                //se a exp do player for maior qe a necessaria para passar de level
                //passa de level
                if (exp >= expProxLvlPlayer) {
                    sql = "update PokemonLiberado set lvl = " + (lvlPlayer + 1) + " where idPokemon = " + this.player.personagem.getId();
                    bool = banco.executaUpdate(sql);
                    int x = this.player.personagem.getX();
                    int y = this.player.personagem.getY();
                    this.criaPlayer1(x, y);
                }

                //}

                //update o numero de kill do player
                int killsAntes = pokeLiberado.getInimigosDerrotados();
                int killsDepois = killsAntes + 1;
                sql = "update pokemonLiberado set InimigosDerrotados = " + killsDepois + " where idPokemon = " + this.player.personagem.getId();
                bool = banco.executaUpdate(sql);

                //update o numero de vezes que o inimigo foi derrotado
                //se o idInimigo for maior que 9, ou seja, depois do squirtle
                //aumenta o numero de vezesDerrotado
                //por que os 9 primeiros pokemons nao podem ser liberados
                //por morte, mas sim por evolucao.
                if (idInimigo > 9) {
                    PokemonDerrotado pokeDerrotado = PokemonDerrotadoDAO.getPokemon(idInimigo);
                    int vezesAntes = pokeDerrotado.getVezesDerrotado();
                    int vezesDepois = vezesAntes + 1;
                    if (!(vezesAntes >= pokeInimigo.getRaridade())) {
                        sql = "update pokemonDerrotado set vezesDerrotado = " + vezesDepois + " where idPokemon = " + idInimigo;
                        bool = banco.executaUpdate(sql);
                    }
                }



                //mostra mensagem na tela
                JOptionPane.showMessageDialog(null, pokeInimigo.getNome() + " fainted, you got " + expGanha + " experience.");


                //se o level do pokemon for maior ou igual ao level de sua evolução
                //evolui
                Pokemon pokePlayer = PokemonDAO.getPokemonPeloNome(CharSelect.getPlayer1());
                //if(pokePlayer.getLevelQueEvolui() != null){
                if (this.player.personagem.getLvl() >= pokePlayer.getLevelQueEvolui()) {
                    this.evolui();
                }

                //se o player ja matou o pokemon o numero minimo de vezes
                //libera o pokemon
                PokemonDerrotado pokeDerrotado = PokemonDerrotadoDAO.getPokemon(idInimigo);
                if (pokeDerrotado.getVezesDerrotado() >= pokeInimigo.getRaridade()) {
                    sql = "insert into pokemonLiberado (idJogador, idPokemon, atk, def, spd, hp) values "
                            + "(1, "
                            + "" + idInimigo + ", "
                            + "" + pokeInimigo.getAtkBase() + ", "
                            + "" + pokeInimigo.getDefBase() + ", "
                            + "" + pokeInimigo.getSpdBase() + ", "
                            + "" + pokeInimigo.getHpBase() + ");";
                    bool = banco.executaInsert(sql);
                }

                //sorteia o inimigo novamente
                this.CharSelect.sorteiaInimigo();
                //cria outro inimigo
                this.criaInimigo(this.CharSelect.getInimigo());
                //e deleta o antigo da lista
                this.inimigo.remove(i);
            }

        }
    }

    public void evolui() {
        Pokemon pokePlayer = PokemonDAO.getPokemonPeloNome(CharSelect.getPlayer1());
        MySQL banco = new MySQL();
        int idPlayer = this.player.personagem.getId();

        //faz a pesquisa no banco para ver se a evolucao ja foi liberada
        PokemonLiberado procuraPokeLiberado = PokemonLiberadoDAO.getPokemon(pokePlayer.getId() + 1);
        //se o pokemon foi liberado, muda o nome do player para o novo pokemon
        if (procuraPokeLiberado.getNome() != null) {
            this.CharSelect.setPlayer1(procuraPokeLiberado.getNome());
            this.criaPlayer1(this.player.personagem.getX(), this.player.personagem.getY());
        } else {
            //senao, faz o insert no banco para liberar o pokemon
            //futuramente aumentar o contador na tabela pokemonDerrotadoa
            //para ver se o pokemon pode ser liberado
            Pokemon pokeASerLiberado = PokemonDAO.getPokemon(idPlayer + 1);
            String sql = "insert into PokemonLiberado (idJogador, idPokemon, atk, def, spd, hp) values "
                    + "(1, "
                    + "" + pokeASerLiberado.getId() + ", "
                    + "" + pokeASerLiberado.getAtkBase() + ", "
                    + "" + pokeASerLiberado.getDefBase() + ", "
                    + "" + pokeASerLiberado.getSpdBase() + ", "
                    + "" + pokeASerLiberado.getHpBase() + ");";
            System.out.println(sql);
            boolean bool = banco.executaInsert(sql);
            this.CharSelect.setPlayer1(pokeASerLiberado.getNome());
            this.criaPlayer1(this.player.personagem.getX(), this.player.personagem.getY());

        }


        String sql = "update pokemonLiberado set"
                + "  atk =" + pokePlayer.getAtkBase()
                + ", def =" + pokePlayer.getDefBase()
                + " , spd =" + pokePlayer.getSpdBase()
                + ", hp =" + pokePlayer.getHpBase()
                + ", lvl = 1"
                + ", exp = 0"
                + " where idPokemon = " + pokePlayer.getId();


        boolean bool = banco.executaUpdate(sql);
    }

    public void verificaInimigoMaisPerto() {
        this.distanciaAteOInimigoMaisPerto = 9999;
        for (Inimigo inimigo : this.inimigo) {
            //ve qual é o inimigo mais proximo do player
            int xPlayer = this.player.getPersonagem().getX();
            int yPlayer = this.player.getPersonagem().getY();
            int xInimigo = inimigo.getX();
            int yInimigo = inimigo.getY();
            double distancia = util.Util.calculaDistancia(xPlayer, yPlayer, xInimigo, yInimigo);
            if (distancia < this.distanciaAteOInimigoMaisPerto) {
                this.inimigoMaisPerto = inimigo;
                this.distanciaAteOInimigoMaisPerto = distancia;
            }
        }
    }

    public void carregaMapa() {
        System.out.println("Started.");
        int size = 128; // tamanho da imagem (1024x1024)
        PerlinNoise2D pn2d = new PerlinNoise2D(size, 0.2f, 5, 20000f, new Random());
        float[][] vals = pn2d.get();//retorna os valores do noise
        BufferedImage img = new BufferedImage(size + 1, size + 1, BufferedImage.TYPE_INT_ARGB);

        String sArray[] = new String[size + 1];

        for (int x = 0; x < vals.length; x++) {
            System.out.println("x: " + x);
            String s = "";
            sArray[x] = "";
            for (int y = 0; y < vals[x].length; y++) {
                String hexStr = "0x" + (Integer.toHexString(Color.GREEN.getRGB()));


                img.setRGB(x, y, ((int) vals[x][y]) | 0xFF721138);//comeca a desenhar a img

                if (img.getRGB(x, y) > 0xFF725f53) {
                    sArray[x] += "2,";
                } else if (img.getRGB(x, y) < 0xFF725f3d) {
                    sArray[x] += "3,";
                } else {
                    sArray[x] += "1,";
                }
            }
        }
        try {
            PerlinNoise2D.limpaTxt(new File("resources/texto.txt"));
        } catch (IOException ex) {
            Logger.getLogger(PerlinNoise2D.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] a = new String[4];
        a[0] = "3";
        a[1] = "resources/tiles/tiles avulsos/grass.png";
        a[2] = "resources/tiles/tiles avulsos/water.png";
        a[3] = "resources/tiles/tiles avulsos/jungle_grass.png";
        PerlinNoise2D.saveTxtTeste(new File("resources/texto.txt"), a, true);

        PerlinNoise2D.saveTxtTeste(new File("resources/texto.txt"), sArray, true);
        try {
            PerlinNoise2D.saveImg(new File("resources/Heightmap.png"), img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished.");



        this.cenario = new Scene();
        try {
            cenario.loadFromFile("resources/texto.txt");
        } catch (InterruptedException ex) {
            Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desenhaLinhaAteInimigoMaisPerto(Graphics g) {
        int xPlayer = this.player.personagem.getX();
        int yPlayer = this.player.personagem.getY();
        int xInimigo = this.inimigoMaisPerto.personagem.getX();
        int yInimigo = this.inimigoMaisPerto.personagem.getY();

        g.setColor(Color.YELLOW);
        g.drawLine(xPlayer, yPlayer, xInimigo, yInimigo);

        double angulo = util.Util.calculaAngulo(xInimigo, xPlayer, yInimigo, yPlayer);
        if (angulo > 315 && angulo <= 45) { //direita
            g.fillRect(400+xPlayer, yPlayer, 25, 25);
        } else if (angulo > 45 && angulo <= 135) { //cima
            g.fillRect(xPlayer, yPlayer-300, 25, 25);
        } else if (angulo > 135 && angulo <= 225) { //esquerda
            g.fillRect(xPlayer-400, yPlayer, 25, 25);
        } else if (angulo > 225 && angulo <= 315) { //baixo
            g.fillRect(xPlayer, 300+yPlayer, 25, 25);
        }


    }
}
