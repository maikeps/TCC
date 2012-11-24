/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Ataques.Ataque;
import DAO.*;
import Itens.*;
import MySQL.*;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Pokemon;
import model.PokemonDerrotado;
import model.PokemonLiberado;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import tcc.Biomas;
import tcc.CenarioComColisao;
import tcc.Inimigo;
import tcc.Personagem;
import tcc.Player;
import tcc.TileInfo;

/**
 *
 * @author maike_p_santos
 */
public class Fase1 extends BasicGameState {

    public static final int ID = 6;
    StateBasedGame game;
    CharacterSelect characterSelect;
    Player player;
    ArrayList<Inimigo> listaInimigos;
    Inimigo boss;
    ArrayList<Ataque> ataquesPlayer;
    ArrayList<Ataque> ataquesInimigo;
    ArrayList<Item> listaItens;
    ArrayList<Bau> listaBaus;
    Personagem personagem;
    boolean atacou;
    Inimigo inimigoMaisPerto;
    double distanciaAteInimigoMaisPerto;
    CenarioComColisao cenarioComColisao;
    int[] tilesColisao = {2}; // 2 = agua
    int lvlInicialPlayer;
    Music musica;
    Sound somSelect;
    ArrayList<String> listaNomes;
    ArrayList<Pokemon> listaPokemons;
    Image portal;
    public static boolean portalSurgiu;
    int xPortal;
    int yPortal;
    public static boolean podeComecar = false;
    public static Biomas bioma;
    int maxInimigos = 25;
    public static boolean primeiraVezQueCriaPlayer = true;
    boolean playerUpou = false;
    int contLevelUp;
    boolean evoluiu = false;
    boolean jogoParado = false;
    boolean evoluir = false;
    boolean bossApareceu = false;

    public Fase1(CharacterSelect characterSelect) {
        this.characterSelect = characterSelect;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;

        System.out.println("Fase1 loaded.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        if (this.podeComecar) {
            if (this.jogoParado) {
            } else {
                if (!this.musica.playing()) {
                    this.musica.play();
                }

                boolean val = new Random().nextInt(1000) <= 1;
                if (val) {
                    this.adicionaBau();
                }

                //val = new Random().nextInt(100) <= 10;
                //  if (val) {
                this.adicionaInimigo();
                //  }

                for (Inimigo inimigo : this.listaInimigos) {
                    inimigo.setXPlayer(this.player.getX());
                    inimigo.setYPlayer(this.player.getY());
                    inimigo.update(gc, game, i);
                    if (this.cenarioComColisao.temColisaoComTile(inimigo, 2)) {
                        inimigo.setVelocidade(2);
                    } else {
                        inimigo.setVelocidade(3);
                    }
                }

                for (int cont = 0; cont < this.ataquesPlayer.size(); cont++) {
                    this.ataquesPlayer.get(cont).update(gc, game, i);
                    if (Math.abs(this.ataquesPlayer.get(cont).getX() - this.player.getX()) >= gc.getWidth() / 2 + 100 && Math.abs(this.ataquesPlayer.get(cont).getY() - this.player.getY()) >= gc.getHeight() / 2 + 100) {
                        this.ataquesPlayer.remove(cont);
                    }
                }
                for (Ataque a : this.ataquesInimigo) {
                    a.update(gc, game, i);
                }

                for (Bau bau : this.listaBaus) {
                    bau.update(gc, game, i);
                    if (bau.abriu) {
                        this.abreBau(bau);
                    }
                }
                for (int n = 0; n < this.listaItens.size(); n++) {
                    this.listaItens.get(n).update(gc, game, i);
                    if (this.listaItens.get(n).tempoDesdeCriacao >= 1000) {
                        this.listaItens.remove(n);
                    }
                }

                this.cenarioComColisao.update(i, tilesColisao);


                for (int cont = 0; cont < this.listaBaus.size(); cont++) {
                    if (this.listaBaus.get(cont).abriu || this.cenarioComColisao.temColisaoComTile(this.listaBaus.get(cont), 2)) {
                        this.listaBaus.remove(cont);
                    }
                }

                this.verificaInimigoMaisPerto();
                this.lancaAtaques();
                this.verificaColisao();
                this.verificaSePlayerEstaMorto();
                this.verificaSeInimigoEstaMorto();
                this.verificaColisaoComBaus();
                this.verificaColisaoComItens();
                if (this.portalSurgiu) {
                    this.verificaColisaoComPortal();
                }
                //  this.verificaSeGanhaLevel();

                this.player.update(gc, game, i);
                if (this.cenarioComColisao.temColisaoComTile(this.player.personagem, 2)) {
                    this.player.setVelocidade(2);
                } else {
                    this.player.setVelocidade(5);
                }

                if (this.evoluir) {
                    this.evolui();
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        if (this.podeComecar == false) {
            this.inicializa(gc);
        } else {

            g.setColor(Color.white);

            int diferencaLvl = this.player.personagem.getLvl() - this.lvlInicialPlayer;
            if (diferencaLvl >= 5) {
                if (this.bossApareceu == false && this.portalSurgiu == false) {
                    this.criaBoss();
                }
            }

            this.cenarioComColisao.render(gc, game, g, this.player.offsetx, this.player.offsety, this.player.getX(), this.player.getY());

            if (this.portalSurgiu) {
                boolean temColisao = false;
                ArrayList<TileInfo> tilesColisao = this.cenarioComColisao.getScene().getTilesFromRect(new Point(this.xPortal, this.yPortal), new Point(this.xPortal + this.portal.getWidth(), this.xPortal + this.portal.getHeight()));
                for (TileInfo tile : tilesColisao) {
                    Shape s = new Rectangle(this.xPortal, this.yPortal, this.portal.getWidth(), this.portal.getHeight());
                    if (tile.getRetangle().intersects(s)) {
                        this.criaPortal();
                        temColisao = true;
                    }
                }
                if (!temColisao) {
                    g.drawImage(portal, this.xPortal, this.yPortal);
                }
            }

            for (Bau bau : this.listaBaus) {
                bau.render(gc, game, g);
            }
            for (Item item : this.listaItens) {
                item.render(gc, game, g);
            }

            this.player.render(gc, game, g);

            for (Inimigo inimigo : this.listaInimigos) {
                if (Math.abs(inimigo.getX() - this.player.getX()) <= gc.getWidth() / 2 + 100 && Math.abs(inimigo.getY() - this.player.getY()) <= gc.getHeight() / 2 + 100) {
                    inimigo.render(gc, game, g);
                }
            }

            for (Ataque a : this.ataquesPlayer) {
                if (!a.desativado) {
                    if (Math.abs(a.getX() - this.player.getX()) <= gc.getWidth() / 2 + 100 && Math.abs(a.getY() - this.player.getY()) <= gc.getHeight() / 2 + 100) {
                        a.render(gc, game, g);
                    }
                }
            }
            for (Ataque a : this.ataquesInimigo) {
                if (!a.desativado) {
                    if (Math.abs(a.getX() - this.player.getX()) <= gc.getWidth() / 2 + 200 && Math.abs(a.getY() - this.player.getY()) <= gc.getHeight() / 2 + 100) {
                        a.render(gc, game, g);
                    }
                }
            }

            this.desenhaHealthBar(g);
            this.desenhaDano(g);
            this.desenhaEfeitoDeItem(g);
            this.desenhaExperienciaGanha(g);
            this.desenhaStatsGanhos(g);

            this.perguntaSeQuerEvoluir(gc, g);
            if (this.bossApareceu) {
                this.procuraBoss(gc, g);
            }
            if(this.portalSurgiu){
                this.procuraPortal(gc, g);
            }
        }
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_P || key == Input.KEY_ESCAPE) {
            this.somSelect.play();
            this.game.enterState(PauseScreen.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        //cheats :D 
        if (key == Input.KEY_SPACE) {
            this.characterSelect.sorteiaInimigo();
            this.criaInimigo(this.characterSelect.getInimigo());

            if (this.jogoParado) {
                this.jogoParado = false;
            } else {
                this.jogoParado = true;
            }
        }

        if (this.jogoParado) {
            if (key == Input.KEY_ENTER) {
                this.evoluir = true;
                this.jogoParado = false;
            }
            if (key == Input.KEY_BACK) {
                this.evoluir = false;
                this.jogoParado = false;
            }
        }
    }

    public void lancaAtaques() {
        if (this.player.atacou == true) {
            if (this.player.personagem.podeAtirar()) {

                model.Ataque a = AtaqueDAO.getPoder(this.characterSelect.getPlayer1());
                String s = "Ataques." + a.getNome();
                try {
                    Class cls = Class.forName(s);
                    Class[] parameters = new Class[]{int.class, int.class, int.class, int.class, float.class, Personagem.class};
                    java.lang.reflect.Constructor con = cls.getConstructor(parameters);
                    Object o = con.newInstance(new Object[]{this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()});
                    this.ataquesPlayer.add((Ataque) o);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "ERROR: classe " + ex.getMessage() + " não encontrada");
                    System.exit(1);
                } catch (IllegalAccessException ex2) {
                    ex2.printStackTrace();
                    JOptionPane.showMessageDialog(null, "2ERROR: " + ex2.getMessage());
                    System.exit(1);
                } catch (InstantiationException ex3) {
                    ex3.printStackTrace();
                    JOptionPane.showMessageDialog(null, "3ERROR: " + ex3.getMessage());
                    System.exit(1);
                } catch (NoSuchMethodException ex4) {
                    ex4.printStackTrace();
                    JOptionPane.showMessageDialog(null, "4ERROR: " + ex4.getMessage());
                    System.exit(1);
                } catch (IllegalArgumentException ex5) {
                    ex5.printStackTrace();
                    JOptionPane.showMessageDialog(null, "5ERROR: " + ex5.getMessage());
                    System.exit(1);
                } catch (InvocationTargetException ex6) {
                    ex6.printStackTrace();
                    JOptionPane.showMessageDialog(null, "6ERROR: " + ex6.getMessage());
                    System.exit(1);
                } catch (SecurityException ex7) {
                    ex7.printStackTrace();
                    JOptionPane.showMessageDialog(null, "7ERROR: " + ex7.getMessage());
                    System.exit(1);
                }


                this.player.personagem.setCooldownAtual();
            }
        }

        this.player.atacou = false;
        //---------------------\\

        for (int i = 0; i < this.listaInimigos.size(); i++) {
            if (this.listaInimigos.get(i).atacou == true) {
                if (this.listaInimigos.get(i).personagem.podeAtirar()) {

                    model.Ataque a = AtaqueDAO.getPoder(this.listaInimigos.get(i).personagem.getNome());
                    String s = "Ataques." + a.getNome();
                    try {
                        Class cls = Class.forName(s);
                        Class[] parameters = new Class[]{int.class, int.class, int.class, int.class, float.class, Personagem.class};
                        java.lang.reflect.Constructor con = cls.getConstructor(parameters);
                        Object o = con.newInstance(new Object[]{this.listaInimigos.get(i).getX(), this.listaInimigos.get(i).getY(), this.listaInimigos.get(i).getDestX(), this.listaInimigos.get(i).getDestY(), this.listaInimigos.get(i).getAngulo(), this.listaInimigos.get(i).getPersonagem()});
                        this.ataquesInimigo.add((Ataque) o);
                    } catch (ClassNotFoundException ex) {
                        this.listaInimigos.remove(i);
                        this.characterSelect.sorteiaInimigo();
                        this.criaInimigo(this.characterSelect.getInimigo());
                        //   JOptionPane.showMessageDialog(null, "ERROR: classe " + ex.getMessage() + " não encontrada, vamos trocar o pokemon.");
                    } catch (IllegalAccessException ex2) {
                        ex2.printStackTrace();
                        JOptionPane.showMessageDialog(null, "2ERROR: " + ex2.getMessage());
                        System.exit(1);
                    } catch (InstantiationException ex3) {
                        ex3.printStackTrace();
                        JOptionPane.showMessageDialog(null, "3ERROR: " + ex3.getMessage());
                        System.exit(1);
                    } catch (NoSuchMethodException ex4) {
                        ex4.printStackTrace();
                        JOptionPane.showMessageDialog(null, "4ERROR: " + ex4.getMessage());
                        System.exit(1);
                    } catch (IllegalArgumentException ex5) {
                        ex5.printStackTrace();
                        JOptionPane.showMessageDialog(null, "5ERROR: " + ex5.getMessage());
                        System.exit(1);
                    } catch (InvocationTargetException ex6) {
                        ex6.printStackTrace();
                        System.out.println(ex6.getCause());
                        JOptionPane.showMessageDialog(null, "6ERROR: Metodo invocado joga uma exception");
                        System.exit(1);
                    } catch (SecurityException ex7) {
                        ex7.printStackTrace();
                        JOptionPane.showMessageDialog(null, "7ERROR: " + ex7.getMessage());
                        System.exit(1);
                    }
                    // this.ataques.add(new Class.(this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.player.getPersonagem()));

                    this.listaInimigos.get(i).personagem.setCooldownAtual();
                }
            }

            this.listaInimigos.get(i).atacou = false;
        }
    }

    public void criaPlayer(int xSpawn, int ySpawn) {
        PokemonLiberado pokemon = PokemonLiberadoDAO.getPokemonPeloNome(this.characterSelect.getPlayer1());
        Pokemon poke = PokemonDAO.getPokemonPeloNome(this.characterSelect.getPlayer1());

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
        //???


        int hpLvlAnterior = hp + ((hp + 1 / 8 + 50) * (lvl - 1) / 50 + 10);
        int atkLvlAnterior = atk + ((atk + 1 / 8 + 50) * (lvl - 1) / 50 + 5);
        int defLvlAnterior = def + ((def + 1 / 8 + 50) * (lvl - 1) / 50 + 5);
        int spdLvlAnterior = spd + ((spd + 1 / 8 + 50) * (lvl - 1) / 50 + 5);

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

        if (this.primeiraVezQueCriaPlayer == true || this.evoluiu == true) {
            //  xSpawn -= this.game.getContainer().getWidth() / 2 - this.player.personagem.animacaoAtual.getImage().getWidth() / 2;
            //   ySpawn -= this.game.getContainer().getHeight() / 2 - this.player.personagem.animacaoAtual.getImage().getHeight() / 2;
            this.personagem = new Personagem(id, nome, atk, def, spd, hp, lvl);

            this.player = new Player(this.personagem, xSpawn, ySpawn);
            this.primeiraVezQueCriaPlayer = false;
            this.evoluiu = false;
        } else {
            this.player.personagem.setNome(nome);
            this.player.personagem.setAtk(atk);
            this.player.personagem.setDef(def);
            this.player.personagem.setSpd(spd);
            this.player.personagem.setHpInicial(hp);
            this.player.personagem.setHp(hp);
            this.player.personagem.setLvl(lvl);
            this.player.setX(xSpawn);
            this.player.setY(ySpawn);

            this.player.expNivelAtual = PokemonLiberadoDAO.getExperiencia(this.player.personagem.getLvl());
            this.player.expProxNivel = PokemonLiberadoDAO.getExperiencia(this.player.personagem.getLvl() + 1);
        }

//        personagem.setHpLvlAnterior(this.player.personagem.getHpInicial());
//        personagem.setAtkLvlAnterior(this.player.personagem.getAtk());
//        personagem.setDefLvlAnterior(this.player.personagem.getDef());
//        personagem.setSpdLvlAnterior(this.player.personagem.getSpd());



        this.player.personagem.setHpLvlAnterior(hpLvlAnterior);
        this.player.personagem.setAtkLvlAnterior(atkLvlAnterior);
        this.player.personagem.setDefLvlAnterior(defLvlAnterior);
        this.player.personagem.setSpdLvlAnterior(spdLvlAnterior);

        this.player.personagem.setHpBase(hp);
        this.player.personagem.setAtkBase(atk);
        this.player.personagem.setDefBase(def);
        this.player.personagem.setSpdBase(spd);

        for (Inimigo i : this.listaInimigos) {
            i.player = this.player;
        }
        this.cenarioComColisao.adicionaObjeto(this.player.personagem);

        if (this.lvlInicialPlayer == 0) {
            this.lvlInicialPlayer = lvl;
        }


        this.player.personagem.larguraMapa = this.cenarioComColisao.getScene().getWidth();
        this.player.personagem.alturaMapa = this.cenarioComColisao.getScene().getHeight();

        Stats.personagem = this.player.personagem;
    }

    public void criaInimigo(String nome) {
        int index = 0;
        index = this.listaNomes.indexOf(nome);

        Pokemon pokemon = this.listaPokemons.get(index);
        String[] elementoCerto = new String[4];
        if (this.bioma == Biomas.GRASS) {
            elementoCerto[0] = "Grass";
            elementoCerto[1] = "Normal";
            elementoCerto[2] = "Flying";
        } else if (this.bioma == Biomas.DESERT) {
            elementoCerto[0] = "Fire";
            elementoCerto[1] = "Dragon";
            elementoCerto[2] = "Flying";
        } else if (this.bioma == Biomas.BEACH) {
            elementoCerto[0] = "Water";
            elementoCerto[1] = "Electric";
            elementoCerto[2] = "Flying";
        } else if (this.bioma == Biomas.SWAMPLAND) {
            elementoCerto[0] = "Poison";
            elementoCerto[1] = "Dark";
            elementoCerto[2] = "Shadow";
        } else if (this.bioma == Biomas.ICELAND) {
            elementoCerto[0] = "Ice";
            elementoCerto[1] = "Water";
        } else if (this.bioma == Biomas.FOREST) {
            elementoCerto[0] = "Grass";
            elementoCerto[1] = "Bug";
            elementoCerto[2] = "Flying";
        } else if (this.bioma == Biomas.MOUNTAIN) {
            elementoCerto[0] = "Dragon";
            elementoCerto[1] = "Rock";
            elementoCerto[2] = "Ground";
            elementoCerto[3] = "Steel";
        } else if (this.bioma == Biomas.ABANDONED_CASTLE) {
            elementoCerto[0] = "Fighting";
            elementoCerto[1] = "Ghost";
            elementoCerto[2] = "Psychic";
            elementoCerto[3] = "Dark";
        }

        boolean ok = false;
        for (int i = 0; i < elementoCerto.length; i++) {
            if (pokemon.getElementoPrimarioString().equals(elementoCerto[i])) {
                ok = true;
            }
        }
        if (!ok) {
            return;
        }

        int id = pokemon.getId();
        int atk = pokemon.getAtkBase();
        int def = pokemon.getDefBase();
        int spd = pokemon.getSpdBase();
        int hp = pokemon.getHpBase();
        int lvl;


        if (this.player.getPersonagem().getLvl() >= 5) {
            int diferenca = util.Util.random(5);//maximo de 5 levels de diferenca
            lvl = this.lvlInicialPlayer + diferenca;
            //lvl = this.player.getPersonagem().getLvl() + diferenca;
        } else {
            lvl = this.player.getPersonagem().getLvl();
        }
        //se o pokemon ja passou do level minimo para evoluir, nao cria
        if (pokemon.getLevelQueEvolui() <= lvl) {
            ok = false;
        }
        //se o pokemon nao tem evolução, nao cria
        if (pokemon.getLevelQueEvolui() == 0) {
            if (this.listaPokemons.get(index - 1).getLevelQueEvolui() > lvl) {
                ok = false;
            }
        }
        //se o level do pokemon for menor que o necessario para evoluir, nao cria
        if (index != 0) {
            if (this.listaPokemons.get(index - 1).getLevelQueEvolui() > lvl) {
                ok = false;
            }
        }
        if (!ok) {
            return;
        }

        hp += (((hp + 1 / 8 + 50) * lvl) / 50 + 10);
        atk += ((atk + 1 / 8 + 50) * lvl) / 50 + 5;
        def += ((def + 1 / 8 + 50) * lvl) / 50 + 5;
        spd += ((spd + 1 / 8 + 50) * lvl) / 50 + 5;
        hp = hp * 2 / 3;
        atk = atk * 2 / 3;
        def = def * 2 / 3;
        spd = spd * 2 / 3;
//        String sql = "insert into pokemonInimigo "
//                + "(idPokemon, tipo, atk, def, spd, hp, lvl) values"
//                + "(\"" + id + "\", \"minion\", \"" + atk + "\", "
//                + "\"" + def + "\", \"" + spd + "\", \"" + hp + "\", \"" + lvl + "\")";
//
//        MySQL bd = new MySQL();
//        boolean bool = bd.executaInsert(sql);

        this.personagem = new Personagem(id, nome, atk, def, spd, hp, lvl);
        //    int x = util.Util.random(this.cenarioColisao.getScene().getWidth()+1);
        //    int y = util.Util.random(this.cenarioColisao.getScene().getHeight()+1);
        int x = util.Util.random(this.cenarioComColisao.getScene().getWidth());
        int y = util.Util.random(this.cenarioComColisao.getScene().getHeight());
//        double distancia = util.Util.calculaDistancia(x, y, this.player.getX(), this.player.getY());
//            while (distancia < 1000) {
//                x = util.Util.random(this.cenarioComColisao.getScene().getWidth());
//                y = util.Util.random(this.cenarioComColisao.getScene().getHeight());
//            }
        Inimigo inimigo = new Inimigo(this.personagem, this.player, x, y);

        this.inimigoMaisPerto = inimigo;
        inimigo.personagem.larguraMapa = this.cenarioComColisao.getScene().getWidth();
        inimigo.personagem.alturaMapa = this.cenarioComColisao.getScene().getHeight();
        inimigo.tipo = "Minion";


        this.listaInimigos.add(inimigo);
    }

    public void desenhaHealthBar(Graphics g) {
        // HealthBar do player
        int hpInicial = this.player.getPersonagem().getHpInicial();
        int hp = (int) this.player.getHp();
        int lvl = this.player.getPersonagem().getLvl();
        double porcentoHp = (100 * hp) / hpInicial;

        g.setColor(new Color(0f, 0f, 0f, 0.6f));
        g.fillRoundRect(40 - this.player.offsetx, 460 - this.player.offsety, 170, 120, 5);
        g.setColor(Color.white);
        g.drawString("" + this.characterSelect.getPlayer1(), 60 - this.player.offsetx, 490 - this.player.offsety);
        g.drawString("LVL " + lvl, 60 - this.player.offsetx, 470 - this.player.offsety);
        g.setColor(Color.gray);
        g.fillRect(92 - this.player.offsetx, 512 - this.player.offsety, 100, 20);
        g.setColor(Color.green);
        g.fillRect(92 - this.player.offsetx, 512 - this.player.offsety, (int) porcentoHp, 20);
        g.setColor(Color.white);
        g.drawString("HP: " + hp + "/" + hpInicial, 60 - this.player.offsetx, 512 - this.player.offsety);

        // Barra de Experiencia do Player
        int expBarraTotal = this.player.expProxNivel - this.player.expNivelAtual;
        int expBarraAtual = this.player.getPersonagem().getExp() - this.player.expNivelAtual;

        double porcento = (100 * expBarraAtual) / expBarraTotal;
        g.setColor(Color.gray);
        g.fillRoundRect(92 - this.player.offsetx, 542 - this.player.offsety, 100, 10, 10);
        g.setColor(Color.blue);
        g.fillRoundRect(92 - this.player.offsetx, 542 - this.player.offsety, (int) porcento, 10, 10);
        g.setColor(Color.white);

        // HealthBar do inimigo
        if (this.distanciaAteInimigoMaisPerto <= 750) {
            int hpInicialInimigo = this.inimigoMaisPerto.getPersonagem().getHpInicial();

            int hpInimigo = (int) this.inimigoMaisPerto.getHp();
            int lvlInimigo = this.inimigoMaisPerto.getPersonagem().getLvl();
            double porcentoHpInimigo = (100 * hpInimigo) / hpInicialInimigo;

            g.setColor(new Color(0f, 0f, 0f, 0.6f));
            g.fillRoundRect(590 - this.player.offsetx, 20 - this.player.offsety, 170, 120, 5);



            g.setColor(Color.white);
            g.drawString("" + this.inimigoMaisPerto.getPersonagem().getNome(), 610 - this.player.offsetx, 40 - this.player.offsety);
            g.drawString("LVL " + lvlInimigo, 610 - this.player.offsetx, 60 - this.player.offsety);


            g.setColor(Color.gray);
            g.fillRect(642 - this.player.offsetx, 80 - this.player.offsety, 100, 20);


            g.setColor(Color.green);
            g.fillRect(642 - this.player.offsetx, 80 - this.player.offsety, (int) porcentoHpInimigo, 20);
            g.setColor(Color.white);
            g.drawString("HP: ", 610 - this.player.offsetx, 82 - this.player.offsety);
            if (this.inimigoMaisPerto.tipo.equals("Boss")) {
                g.drawString("BOSS", 610 - this.player.offsetx, 105 - this.player.offsety);
            }
        }
    }

    public void verificaColisao() {
        //colisao ataque inimigo com player
        for (Inimigo inimigo : this.listaInimigos) {
            for (Ataque a : this.ataquesInimigo) {
                int x1 = this.player.getX(), x2 = this.player.getPersonagem().animacaoAtual.getImage().getWidth(), y1 = this.player.getY(), y2 = this.player.getPersonagem().animacaoAtual.getImage().getHeight();
                Shape s = new Rectangle(x1, y1, x2, y2);
                // if (a.getShape().intersects(x1, y1, x2, y2)) {
                if (a.getShape().intersects(s)) {
                    if (a.desativado == false) {
                        int lvl = inimigo.personagem.getLvl();
                        int danoDoAtk = a.getDanoBruto();
                        int atkDoPokemon = inimigo.personagem.getAtk();
                        int defDoOponente = this.player.personagem.getDef();
                        int r = 100 - util.Util.random(15);
                        int multiplicador = 1; //fazer busca no banco
                        int dano = (((((((lvl * 2 / 5) + 2) * danoDoAtk * atkDoPokemon / 50) / defDoOponente) + 2) * r / 100) * multiplicador);
                        a.setDano(dano);
                        this.player.personagem.perdeHp(dano);
                        try {
                            Sound som = new Sound("resources/sounds/misc/hit 2.wav");
                            som.play();


                        } catch (SlickException ex) {
                            Logger.getLogger(CharacterSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    a.setAcertou(true);
                    a.desativado();
                    //pra mandar pro sei-la-o-que garbage collector
                    // a = null;
                }
            }
        }

        //colisao ataque player com inimigo

        for (Inimigo inimigo : this.listaInimigos) {
            for (Ataque a : this.ataquesPlayer) {
                int x1 = inimigo.getX(), x2 = inimigo.getPersonagem().animacaoAtual.getImage().getWidth(), y1 = inimigo.getY(), y2 = inimigo.getPersonagem().animacaoAtual.getImage().getHeight();
                if (inimigo.tipo.equals("Boss")) {
                    x2 *= 2;
                    y2 *= 2;
                }
                Shape s = new Rectangle(x1, y1, x2, y2);
                if (a.getShape().intersects(s)) {
                    if (!(a.personagensAcertados.contains(inimigo.personagem))) {
                        a.personagensAcertados.add(inimigo.personagem);
                        // if (a.desativado == false) {
                        int lvl = this.player.personagem.getLvl();
                        int danoDoAtk = a.getDanoBruto();
                        int atkDoPokemon = this.player.personagem.getAtk();
                        int defDoOponente = inimigo.personagem.getDef();
                        int r = 100 - util.Util.random(15);
                        int multiplicador = 1; //fazer busca no banco
                        int dano = (((((((lvl * 2 / 5) + 2) * danoDoAtk * atkDoPokemon / 50) / defDoOponente) + 2) * r / 100) * multiplicador);
                        a.setDano(dano);
                        inimigo.personagem.perdeHp(dano);



                        //faz update no campo totalDanoCausado

                        int idPlayer = this.player.personagem.getId();
                        PokemonLiberado pokeLiberado = PokemonLiberadoDAO.getPokemon(idPlayer);
                        int danoTotal = pokeLiberado.getTotalDanoCausado();
                        int danoTotalDepois = danoTotal + dano;


                        MySQL banco = new MySQL();
                        String sql = "update pokemonLiberado set totalDanoCausado = " + danoTotalDepois + " where idPokemon = " + idPlayer;
                        boolean bool = banco.executaUpdate(sql);

                        try {
                            Sound som = new Sound("resources/sounds/misc/hit 2.wav");
                            som.play();


                        } catch (SlickException ex) {
                            Logger.getLogger(CharacterSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    a.setAcertou(true);
                    a.desativado();
                    //a = null;
                }
            }
        }
    }

    public void verificaSePlayerEstaMorto() {
        if (this.player.personagem.estaMorto()) {
            try {
                Sound s = new Sound("resources/sounds/misc/death.wav");
                s.play();


            } catch (SlickException ex) {
                Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
            }
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

            this.game.enterState(GameOver.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    public void verificaSeInimigoEstaMorto() {
        for (int i = 0; i < this.listaInimigos.size(); i++) {
            Inimigo inimigo = this.listaInimigos.get(i);
            if (inimigo.personagem.estaMorto()) {
                if (inimigo.tipo.equals("Boss")) {
                    this.bossApareceu = false;
                }
                try {
                    Sound s = new Sound("resources/sounds/misc/exp.wav");
                    s.play();


                } catch (SlickException ex) {
                    Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
                }
                int idPlayer = this.player.personagem.getId();
                int idInimigo = inimigo.personagem.getId();
                int lvlInimigo = inimigo.personagem.getLvl();

                //pega as informacoes do inimigo e calcula a exp a ser adquirida
                Pokemon pokeInimigo = PokemonDAO.getPokemon(inimigo.personagem.getId());
                int expBase = pokeInimigo.getBaseExp();
                int expGanha = (expBase * lvlInimigo) / 7;
                // if (inimigo.tipo.equals("Boss")) {
                expGanha *= 5;
                // }
                this.player.expGanha = expGanha;

                //altera o campo exp do pokemonLiberado no banco
                PokemonLiberado pokeLiberado = PokemonLiberadoDAO.getPokemon(this.player.personagem.getId());
                int exp = expGanha + pokeLiberado.getExp();
                int lvlPlayer = pokeLiberado.getLvl();

                this.player.personagem.setExp(exp);
                this.player.contExpGanha = 60;

                MySQL banco = new MySQL();
                String sql = "update PokemonLiberado set exp = " + exp + " where idPokemon = " + this.player.personagem.getId();
                boolean bool = banco.executaUpdate(sql);
                this.player.personagem.setExp(exp);

                //verifica se o pokemon ganhou um level
                this.verificaSeGanhaLevel();

//                sql = "select * from experiencia where lvl = " + (lvlPlayer + 1);
//                ConjuntoResultados linhas = banco.executaSelect(sql);
//                int expProxLvlPlayer = 0;
//                if (linhas.next()) {
//                    expProxLvlPlayer = linhas.getInt("exp");
//                }

                //se a exp do player for maior qe a necessaria para passar de level
                //passa de level
//                if (exp >= expProxLvlPlayer) {
//                    sql = "update PokemonLiberado set lvl = " + (lvlPlayer + 1) + " where idPokemon = " + this.player.personagem.getId();
//                    bool = banco.executaUpdate(sql);
//
//                    int x = this.player.getX();
//                    int y = this.player.getY();
//                    this.criaPlayer(x, y);
//
//                }

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
                //  JOptionPane.showMessageDialog(null, pokeInimigo.getNome() + " fainted, you got " + expGanha + " experience.");


                //se o level do pokemon for maior ou igual ao level de sua evolução
                //evolui
                Pokemon pokePlayer = PokemonDAO.getPokemonPeloNome(this.characterSelect.getPlayer1());
                //if(pokePlayer.getLevelQueEvolui() != null){
                if (this.player.personagem.getLvl() >= pokePlayer.getLevelQueEvolui()) {
                    this.jogoParado = true;
                    //this.evolui();
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
                this.characterSelect.sorteiaInimigo();
                //cria outro inimigo
                this.criaInimigo(this.characterSelect.getInimigo());
                //e deleta o antigo da lista
                this.listaInimigos.remove(i);

                if (inimigo.tipo.equals("Boss")) {
                    this.criaPortal();
                }
            }

        }
    }

    public void evolui() {
        Pokemon pokePlayer = PokemonDAO.getPokemonPeloNome(this.characterSelect.getPlayer1());
        MySQL banco = new MySQL();
        int idPlayer = this.player.personagem.getId();

        if (pokePlayer.getLevelQueEvolui() > 0) {
            this.evoluiu = true;
            //faz a pesquisa no banco para ver se a evolucao ja foi liberada
            PokemonLiberado procuraPokeLiberado = PokemonLiberadoDAO.getPokemon(pokePlayer.getId() + 1);
            //se o pokemon foi liberado, muda o nome do player para o novo pokemon
            String sql = "";
            if (procuraPokeLiberado.getNome() != null) {
                sql = "update pokemonLiberado set"
                        + " lvl = " + this.player.personagem.getLvl()
                        + ", exp = " + this.player.personagem.getExp()
                        + " where idPokemon = " + procuraPokeLiberado.getIdPokemon();
                boolean bool = banco.executaUpdate(sql);

                this.characterSelect.setPlayer1(procuraPokeLiberado.getNome());
                this.criaPlayer(this.player.getX(), this.player.getY());
            } else {
                //senao, faz o insert no banco para liberar o pokemon
                //futuramente aumentar o contador na tabela pokemonDerrotadoa
                //para ver se o pokemon pode ser liberado
                Pokemon pokeASerLiberado = PokemonDAO.getPokemon(idPlayer + 1);
                sql = "insert into PokemonLiberado (idJogador, idPokemon, atk, def, spd, hp, lvl, exp) values "
                        + "(1, "
                        + "" + pokeASerLiberado.getId() + ", "
                        + "" + pokeASerLiberado.getAtkBase() + ", "
                        + "" + pokeASerLiberado.getDefBase() + ", "
                        + "" + pokeASerLiberado.getSpdBase() + ", "
                        + "" + pokeASerLiberado.getHpBase() + ","
                        + "" + this.player.personagem.getLvl() + ","
                        + "" + this.player.personagem.getExp() + ");";
                System.out.println(sql);
                boolean bool = banco.executaInsert(sql);
                this.characterSelect.setPlayer1(pokeASerLiberado.getNome());
                this.criaPlayer(this.player.getX(), this.player.getY());

            }


            sql = "update pokemonLiberado set"
                    + "  atk =" + pokePlayer.getAtkBase()
                    + ", def =" + pokePlayer.getDefBase()
                    + " , spd =" + pokePlayer.getSpdBase()
                    + ", hp =" + pokePlayer.getHpBase()
                    + ", lvl = 1"
                    + ", exp = 0"
                    + " where idPokemon = " + pokePlayer.getId();


            boolean bool = banco.executaUpdate(sql);
            this.evoluir = false;
        }
    }

    public void verificaInimigoMaisPerto() {
        this.distanciaAteInimigoMaisPerto = 9999;
        for (Inimigo inimigo : this.listaInimigos) {
            //ve qual é o inimigo mais proximo do player
            int xPlayer = this.player.getX();
            int yPlayer = this.player.getY();
            int xInimigo = inimigo.getX();
            int yInimigo = inimigo.getY();
            double distancia = util.Util.calculaDistancia(xPlayer, yPlayer, xInimigo, yInimigo);
            if (distancia < this.distanciaAteInimigoMaisPerto) {
                this.inimigoMaisPerto = inimigo;
                this.distanciaAteInimigoMaisPerto = distancia;
            }
        }
    }

    public void carregaMapa(Biomas bioma) {
        try {
            this.cenarioComColisao = new CenarioComColisao("resources/texto.txt");


        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desenhaLinhaAteInimigoMaisPerto(java.awt.Graphics g) {
        int xPlayer = (int) this.player.personagem.getX();
        int yPlayer = (int) this.player.personagem.getY();
        int xInimigo = (int) this.inimigoMaisPerto.personagem.getX();
        int yInimigo = (int) this.inimigoMaisPerto.personagem.getY();

        g.setColor(java.awt.Color.YELLOW);
        g.drawLine(xPlayer, yPlayer, xInimigo, yInimigo);

        double angulo = util.Util.calculaAngulo(xInimigo, xPlayer, yInimigo, yPlayer);
        if (angulo > 315 && angulo <= 45) { //direita
            g.fillRect(400 + xPlayer, yPlayer, 25, 25);
        } else if (angulo > 45 && angulo <= 135) { //cima
            g.fillRect(xPlayer, yPlayer - 300, 25, 25);
        } else if (angulo > 135 && angulo <= 225) { //esquerda
            g.fillRect(xPlayer - 100, yPlayer, 25, 25);
        } else if (angulo > 225 && angulo <= 315) { //baixo
            g.fillRect(xPlayer, 300 + yPlayer, 25, 25);
        }


    }

    public void adicionaBau() {
        if (this.listaBaus.size() < 10) {
            int x;
            int y;
            x = util.Util.random(this.cenarioComColisao.getScene().getWidth());
            y = util.Util.random(this.cenarioComColisao.getScene().getHeight());
//            double distancia = util.Util.calculaDistancia(x, y, this.player.getX(), this.player.getY());
//            while (distancia < 1000) {
//                x = util.Util.random(this.cenarioComColisao.getScene().getWidth());
//                y = util.Util.random(this.cenarioComColisao.getScene().getHeight());
//            }
            this.listaBaus.add(new Bau(x, y));
        }
    }

    public void verificaColisaoComBaus() {
        for (int i = 0; i < this.listaBaus.size(); i++) {
            if (this.listaBaus.get(i).getRetangulo().intersects(this.player.getPersonagem().getRetangulo())) {
                this.listaBaus.get(i).abriu = true;
            }
        }
    }

    public void verificaColisaoComItens() {

        for (int i = 0; i < this.listaItens.size(); i++) {
            if (this.listaItens.get(i).getRetangulo().intersects(this.player.getPersonagem().getRetangulo())) {
                switch (this.listaItens.get(i).getEfeito()) {
                    case CURA:
                        if (this.listaItens.get(i).pegou == false) {
                            this.player.getPersonagem().setHp(this.player.getPersonagem().getHp() + this.listaItens.get(i).getForca());
                        }
                        break;
                    case ENVENENA:
                        if (this.listaItens.get(i).pegou == false) {
                            this.player.getPersonagem().setHp(this.player.getPersonagem().getHp() + this.listaItens.get(i).getForca());
                        }
                        break;
                }
                this.listaItens.get(i).pegou = true;
            }
        }
    }

    public void desenhaDano(Graphics g) {
        for (int i = 0; i < this.ataquesInimigo.size(); i++) {
            if (this.ataquesInimigo.get(i).acertou == true) {
                if (this.ataquesInimigo.get(i).getContador() >= 25) {
                    this.ataquesInimigo.remove(this.ataquesInimigo.get(i));
                } else {
                    g.setColor(Color.red);
                    g.drawString("" + this.ataquesInimigo.get(i).getDano(), this.ataquesInimigo.get(i).getX(), this.ataquesInimigo.get(i).getY());
                }
            }
        }
        for (int i = 0; i < this.ataquesPlayer.size(); i++) {
            if (this.ataquesPlayer.get(i).acertou == true) {
                if (this.ataquesPlayer.get(i).getContador() >= 25) {
                    this.ataquesPlayer.remove(this.ataquesPlayer.get(i));
                } else {
                    g.setColor(Color.white);
                    g.drawString("" + this.ataquesPlayer.get(i).getDano(), this.ataquesPlayer.get(i).getX(), this.ataquesPlayer.get(i).getY());
                }
            }
        }

    }

    public void desenhaEfeitoDeItem(Graphics g) {
        for (int i = 0; i < this.listaItens.size(); i++) {
            if (this.listaItens.get(i).pegou) {
                if (this.listaItens.get(i).getContador() >= 25) {
                    this.listaItens.remove(this.listaItens.get(i));
                } else {
                    switch (this.listaItens.get(i).getEfeito()) {
                        case CURA:
                            g.setColor(Color.green);
                            break;
                        case ENVENENA:
                            g.setColor(Color.red);
                            break;
                    }
                    g.drawString("" + this.listaItens.get(i).getForca(), this.listaItens.get(i).getX(), this.listaItens.get(i).getY());
                }
            }
        }
    }

    public void adicionaInimigo() {
        if (this.listaInimigos.size() < this.maxInimigos) {
            this.characterSelect.sorteiaInimigo();
            this.criaInimigo(characterSelect.getInimigo());
        }
    }

    public void criaBoss() {
//
//        this.characterSelect.sorteiaInimigo();
//        String nome = this.characterSelect.getInimigo();
//        Pokemon pokemon = PokemonDAO.getPokemonPeloNome(nome);
//
//        int id = pokemon.getId();
//        int atk = pokemon.getAtkBase() * 3;
//        int def = pokemon.getDefBase() * 1;
//        int spd = pokemon.getSpdBase() * 1;
//        int hp = pokemon.getHpBase() * 5;
//
//        int lvl;
//
//        if (this.player.getPersonagem().getLvl() >= 5) {
//            int diferenca = util.Util.random(5);//maximo de 5 leveis de diferenca
//            lvl = this.lvlInicialPlayer + diferenca;
//        } else {
//            lvl = this.player.getPersonagem().getLvl();
//        }
//
//        hp += (((hp + 1 / 8 + 50) * lvl) / 50 + 10);
//        atk += ((atk + 1 / 8 + 50) * lvl) / 50 + 5;
//        def += ((def + 1 / 8 + 50) * lvl) / 50 + 5;
//        spd += ((spd + 1 / 8 + 50) * lvl) / 50 + 5;
//
//        String sql = "insert into pokemonInimigo "
//                + "(idPokemon, tipo, atk, def, spd, hp, lvl) values"
//                + "(\"" + id + "\", \"boss\", \"" + atk + "\", "
//                + "\"" + def + "\", \"" + spd + "\", \"" + hp + "\", \"" + lvl + "\")";
//
//        MySQL bd = new MySQL();
//        boolean bool = bd.executaInsert(sql);
//
//        this.personagem = new Personagem(id, nome, atk, def, spd, hp, lvl);
//
//        int x = util.Util.random(this.cenarioComColisao.getScene().getWidth());
//        int y = util.Util.random(this.cenarioComColisao.getScene().getHeight());
//
//        this.boss = new Inimigo(this.personagem, this.player, x, y);
//        this.inimigoMaisPerto = this.boss;
//        this.boss.tipo = "Boss";
//        this.listaInimigos.add(boss);
//        
//        
//        --------------------------------------------------------

        this.characterSelect.sorteiaInimigo();
        String nome = this.characterSelect.getInimigo();
        int index = 0;
        index = this.listaNomes.indexOf(nome);

        Pokemon pokemon = this.listaPokemons.get(index);
        String[] elementoCerto = new String[4];
        if (this.bioma == Biomas.GRASS) {
            elementoCerto[0] = "Grass";
            elementoCerto[1] = "Normal";
            elementoCerto[2] = "Flying";
        } else if (this.bioma == Biomas.DESERT) {
            elementoCerto[0] = "Fire";
            elementoCerto[1] = "Dragon";
            elementoCerto[2] = "Flying";
        } else if (this.bioma == Biomas.BEACH) {
            elementoCerto[0] = "Water";
            elementoCerto[1] = "Electric";
            elementoCerto[2] = "Flying";
        } else if (this.bioma == Biomas.SWAMPLAND) {
            elementoCerto[0] = "Poison";
            elementoCerto[1] = "Dark";
            elementoCerto[2] = "Shadow";
        } else if (this.bioma == Biomas.ICELAND) {
            elementoCerto[0] = "Ice";
            elementoCerto[1] = "Water";
        } else if (this.bioma == Biomas.FOREST) {
            elementoCerto[0] = "Grass";
            elementoCerto[1] = "Bug";
            elementoCerto[2] = "Flying";
        } else if (this.bioma == Biomas.MOUNTAIN) {
            elementoCerto[0] = "Dragon";
            elementoCerto[1] = "Rock";
            elementoCerto[2] = "Ground";
            elementoCerto[3] = "Steel";
        } else if (this.bioma == Biomas.ABANDONED_CASTLE) {
            elementoCerto[0] = "Fighting";
            elementoCerto[1] = "Ghost";
            elementoCerto[2] = "Psychic";
            elementoCerto[3] = "Dark";
        }

        boolean ok = false;
        for (int i = 0; i < elementoCerto.length; i++) {
            if (pokemon.getElementoPrimarioString().equals(elementoCerto[i])) {
                ok = true;
            }
        }
        if (!ok) {
            return;
        }

        int id = pokemon.getId();
        int atk = pokemon.getAtkBase() * 3;
        int def = pokemon.getDefBase() * 1;
        int spd = pokemon.getSpdBase() * 1;
        int hp = pokemon.getHpBase() * 5;
        int lvl;


        if (this.player.getPersonagem().getLvl() >= 5) {
            int diferenca = util.Util.random(5);//maximo de 5 levels de diferenca
            lvl = this.lvlInicialPlayer + diferenca;
            //lvl = this.player.getPersonagem().getLvl() + diferenca;
        } else {
            lvl = this.player.getPersonagem().getLvl();
        }
        //se o pokemon ja passou do level minimo para evoluir, nao cria
        if (pokemon.getLevelQueEvolui() <= lvl) {
            ok = false;
        }
        //se o pokemon nao tem evolução, nao cria
        if (pokemon.getLevelQueEvolui() == 0) {
            if (this.listaPokemons.get(index - 1).getLevelQueEvolui() > lvl) {
                ok = false;
            }
        }
        //se o level do pokemon for menor que o necessario para evoluir, nao cria
        if (index != 0) {
            if (this.listaPokemons.get(index - 1).getLevelQueEvolui() > lvl) {
                ok = false;
            }
        }
        if (!ok) {
            return;
        }

        hp += (((hp + 1 / 8 + 50) * lvl) / 50 + 10);
        atk += ((atk + 1 / 8 + 50) * lvl) / 50 + 5;
        def += ((def + 1 / 8 + 50) * lvl) / 50 + 5;
        spd += ((spd + 1 / 8 + 50) * lvl) / 50 + 5;
        hp = hp * 2 / 3;
        atk = atk * 2 / 3;
        def = def * 2 / 3;
        spd = spd * 2 / 3;
//        String sql = "insert into pokemonInimigo "
//                + "(idPokemon, tipo, atk, def, spd, hp, lvl) values"
//                + "(\"" + id + "\", \"minion\", \"" + atk + "\", "
//                + "\"" + def + "\", \"" + spd + "\", \"" + hp + "\", \"" + lvl + "\")";
//
//        MySQL bd = new MySQL();
//        boolean bool = bd.executaInsert(sql);

        this.personagem = new Personagem(id, nome, atk, def, spd, hp, lvl);
        //    int x = util.Util.random(this.cenarioColisao.getScene().getWidth()+1);
        //    int y = util.Util.random(this.cenarioColisao.getScene().getHeight()+1);
        int x = util.Util.random(this.cenarioComColisao.getScene().getWidth());
        int y = util.Util.random(this.cenarioComColisao.getScene().getHeight());
//        double distancia = util.Util.calculaDistancia(x, y, this.player.getX(), this.player.getY());
//            while (distancia < 1000) {
//                x = util.Util.random(this.cenarioComColisao.getScene().getWidth());
//                y = util.Util.random(this.cenarioComColisao.getScene().getHeight());
//            }
        this.boss = new Inimigo(this.personagem, this.player, x, y);

        this.inimigoMaisPerto = this.boss;
        this.boss.personagem.larguraMapa = this.cenarioComColisao.getScene().getWidth();
        this.boss.personagem.alturaMapa = this.cenarioComColisao.getScene().getHeight();
        this.boss.tipo = "Boss";
        System.out.println(this.boss.getX() + " - y: " + this.boss.getY());

        this.bossApareceu = true;

        this.listaInimigos.add(this.boss);
    }

    public void verificaSeGanhaLevel() {
        MySQL banco = new MySQL();
        int lvlPlayer = this.player.personagem.getLvl();
        int exp = this.player.personagem.getExp();

        String sql = "select * from experiencia where lvl = " + (lvlPlayer + 1);
        ConjuntoResultados linhas = banco.executaSelect(sql);
        int expProxLvlPlayer = 0;
        if (linhas.next()) {
            expProxLvlPlayer = linhas.getInt("exp");
        }


        // boolean bool;
        int cont = 0;//aumenta se o player upou mais de um level
//        while (exp >= expProxLvlPlayer) {
//            if (exp >= expProxLvlPlayer) {
//                cont++;
//            }
//            sql = "select * from experiencia where lvl = " + (lvlPlayer + cont);
//            linhas = banco.executaSelect(sql);
//            if (linhas.next()) {
//                expProxLvlPlayer = linhas.getInt("exp");
//            }
//            //if (exp >= expProxLvlPlayer) {
//            //sql = "update PokemonLiberado set lvl = " + (lvlPlayer + cont) + " where idPokemon = " + this.player.personagem.getId();
//            // bool = banco.executaUpdate(sql);
//            //    cont++;
//            //  }
//        }
        if (exp >= expProxLvlPlayer) {
            //if (cont != 0) {

            //sql = "update PokemonLiberado set lvl = " + (lvlPlayer + cont) + " where idPokemon = " + this.player.personagem.getId();
            sql = "update PokemonLiberado set lvl = " + (lvlPlayer + 1) + " where idPokemon = " + this.player.personagem.getId();
            boolean bool = banco.executaUpdate(sql);

            int x = this.player.getX();
            int y = this.player.getY();
            this.criaPlayer(x, y);
            this.playerUpou = true;
            this.contLevelUp = 1000;

            try {
                Sound som = new Sound("resources/sounds/misc/level up.wav");
                som.play();
            } catch (SlickException ex) {
                Logger.getLogger(CharacterSelect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void desenhaExperienciaGanha(Graphics g) {
        g.setColor(Color.blue);
        if (this.player.contExpGanha > 0) {
            g.drawString("+" + this.player.expGanha, 70 - g.getFont().getWidth("+" + this.player.expGanha) / 2 - this.player.offsetx, 545 - g.getFont().getHeight("+" + this.player.expGanha) / 2 - this.player.offsety);
        } else {
            this.player.contExpGanha = 0;
        }
    }

    public void desenhaStatsGanhos(Graphics g) {
        if (this.playerUpou) {
            g.setColor(Color.white);
            if (this.contLevelUp > 0) {
                this.contLevelUp--;



                int lvl = this.player.personagem.getLvl();
                int lvlAntigo = lvl - 1;
                int atk = this.player.personagem.getAtk();
                //  int atkAntigo = this.player.personagem.getAtkBase()+(((this.player.personagem.getAtkBase() + 1 / 8 + 50) * lvlAntigo) / 50 + 5);
                int atkAntigo = this.player.personagem.getAtkLvlAnterior();
                int def = this.player.personagem.getDef();
                //int defAntigo = this.player.personagem.getDefBase()+(((this.player.personagem.getDefBase() + 1 / 8 + 50) * lvlAntigo) / 50 + 5);
                int defAntigo = this.player.personagem.getDefLvlAnterior();
                int spd = this.player.personagem.getSpd();
                //int spdAntigo = this.player.personagem.getSpdBase()+(((this.player.personagem.getSpdBase() + 1 / 8 + 50) * lvlAntigo) / 50 + 5);
                int spdAntigo = this.player.personagem.getSpdLvlAnterior();
                int hp = this.player.personagem.getHpInicial();
                //int hpAntigo = this.player.personagem.getHpBase()+(((this.player.personagem.getHpBase() + 1 / 8 + 50) * lvlAntigo) / 50 + 10);
                int hpAntigo = this.player.personagem.getHpLvlAnterior();
                int diferencaHp = hp - hpAntigo;
                int diferencaAtk = atk - atkAntigo;
                int diferencaDef = def - defAntigo;
                int diferencaSpd = spd - spdAntigo;

                g.setColor(new Color(0f, 0f, 0f, 0.6f));
                g.fillRoundRect(590 - this.player.offsetx, 460 - this.player.offsety, 170, 120, 5);
                g.setColor(Color.white);
                g.drawString("Level Up!", 675 - g.getFont().getWidth("Level Up!") / 2 - this.player.offsetx, 475 - g.getFont().getHeight("Level Up!") / 2 - this.player.offsety);
                g.drawString("LVL " + lvlAntigo + " -> " + lvl, 600 - this.player.offsetx, 490 - this.player.offsety);
                g.drawString("HP  " + hpAntigo + " -> " + hp + " (+" + diferencaHp + ")", 600 - this.player.offsetx, 505 - this.player.offsety);
                g.drawString("ATK " + atkAntigo + " -> " + atk + " (+" + diferencaAtk + ")", 600 - this.player.offsetx, 520 - this.player.offsety);
                g.drawString("DEF " + defAntigo + " -> " + def + " (+" + diferencaDef + ")", 600 - this.player.offsetx, 535 - this.player.offsety);
                g.drawString("SPD " + spdAntigo + " -> " + spd + " (+" + diferencaSpd + ")", 600 - this.player.offsetx, 550 - this.player.offsety);
            } else {
                this.playerUpou = false;
            }
        }





    }

    private void criaPortal() {
        this.portalSurgiu = true;
        this.xPortal = util.Util.random(this.cenarioComColisao.getScene().getWidth());
        this.yPortal = util.Util.random(this.cenarioComColisao.getScene().getHeight());
    }

    private void verificaColisaoComPortal() {
        Shape portal = new Rectangle(this.xPortal, this.yPortal, this.portal.getWidth(), this.portal.getHeight());
        if (this.player.personagem.getRetangulo().intersects(portal)) {
            //troca de fase, recria tudo
//            this.listaBaus = null;
//            this.listaInimigos = null;
//            this.listaItens = null;
//            this.player = null;
//            this.personagem = null;
//            this.inimigoMaisPerto = null;
//            this.ataquesInimigo = null;
//            this.ataquesPlayer = null;
//            this.boss = null;
//            this.cenarioComColisao = null;
//            this.portalSurgiu = false;
//            this.tilesColisao = null;
            //troca de fase
            this.trocaDeFase();
        }
    }

    private void trocaDeFase() {
        ChangeLevel.reset(null);
        this.game.enterState(ChangeLevel.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }

    private void inicializa(GameContainer gc) {
        this.ataquesPlayer = new ArrayList<Ataque>();
        this.ataquesInimigo = new ArrayList<Ataque>();
        this.listaInimigos = new ArrayList<Inimigo>();
        this.listaItens = new ArrayList<Item>();
        this.listaBaus = new ArrayList<Bau>();
        this.listaNomes = new ArrayList<String>();
        this.listaPokemons = new ArrayList<Pokemon>();

        this.listaPokemons = PokemonDAO.getLista();
        for (Pokemon p : this.listaPokemons) {
            this.listaNomes.add(p.getNome());
        }

        this.carregaMapa(Biomas.FOREST);

        this.verificaInimigoMaisPerto();
        try {
            this.musica = new Music("resources/sounds/music/GS_trainer.wav");
            this.somSelect = new Sound("resources/sounds/misc/select.wav");
            this.portal = new Image("resources/tiles/tiles Avulsos/portal.png");


        } catch (SlickException ex) {
            Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
        }


        int x = util.Util.random(this.cenarioComColisao.getScene().getWidth() - gc.getWidth() / 2 - 50);
        int y = util.Util.random(this.cenarioComColisao.getScene().getHeight() - gc.getHeight() / 2 - 50);
        this.criaPlayer(x, y);
        this.cenarioComColisao.adicionaObjeto(this.player.personagem);
        this.player.personagem.larguraMapa = this.cenarioComColisao.getScene().getWidth();
        this.player.personagem.alturaMapa = this.cenarioComColisao.getScene().getHeight();

        this.criaInimigo(this.characterSelect.getInimigo());
        this.characterSelect.sorteiaInimigo();
        for (Inimigo inimigo : this.listaInimigos) {
            this.cenarioComColisao.adicionaObjeto(inimigo);
            inimigo.personagem.larguraMapa = this.cenarioComColisao.getScene().getWidth();
            inimigo.personagem.alturaMapa = this.cenarioComColisao.getScene().getHeight();
        }

        this.lvlInicialPlayer = this.player.personagem.getLvl();
        this.podeComecar = true;
    }

    private void abreBau(Bau bau) {
        int x = bau.xItem;
        int y = bau.yItem;
        switch (bau.efeito) {
            case CURA:
                this.listaItens.add(new Potion(x, y));
                break;
            case ENVENENA:
                this.listaItens.add(new Poison(x, y));
                break;
            case POTION_VAZIA:
                this.listaItens.add(new EmptyPotion(x, y));
                break;
        }
    }

    private void perguntaSeQuerEvoluir(GameContainer gc, Graphics g) {
        if (this.jogoParado && this.evoluir == true) {
            g.setColor(new Color(0f, 0f, 0f, 0.9f));
            g.fillRoundRect(gc.getWidth() / 2 - 200 - this.player.offsetx, gc.getHeight() / 2 - 120 - this.player.offsety, 400, 240, 5);

            Image pokemon;
            Image evolucao;
            try {
                pokemon = new Image("resources/personagens/" + this.player.personagem.getId() + " - " + this.player.personagem.getNome() + "/" + this.player.personagem.getNome() + "_Down.png");
                String nomeEvolucao = this.listaNomes.get(this.player.personagem.getId());//index 2 - numero 3, por exemplo
                evolucao = new Image("resources/personagens/" + (this.player.personagem.getId() + 1) + " - " + nomeEvolucao + "/" + nomeEvolucao + "_Down.png");
                pokemon.draw(gc.getWidth() / 2 - pokemon.getWidth() / 2 - 75 - this.player.offsetx, gc.getHeight() / 2 - pokemon.getHeight() / 2 - this.player.offsety);
                evolucao.draw(gc.getWidth() / 2 - pokemon.getWidth() / 2 + 75 - this.player.offsetx, gc.getHeight() / 2 - pokemon.getHeight() / 2 - this.player.offsety);
            } catch (SlickException ex) {
                Logger.getLogger(Fase1.class.getName()).log(Level.SEVERE, null, ex);
            }

            g.setColor(Color.white);
            g.drawString("--->", gc.getWidth() / 2 - g.getFont().getWidth("--->") / 2 - this.player.offsetx, gc.getHeight() / 2 - g.getFont().getHeight("--->") / 2 - this.player.offsety);
            g.drawString("Deseja evoluir?", gc.getWidth() / 2 - g.getFont().getWidth("Deseja evoluir?") / 2 - this.player.offsetx, gc.getHeight() / 2 - 100 - this.player.offsety);
            g.drawString("ENTER - Sim | BACKSPACE - Não", gc.getWidth() / 2 - g.getFont().getWidth("ENTER - Sim | BACKSPACE - Não") / 2 - this.player.offsetx, gc.getHeight() / 2 + 75 - this.player.offsety);
        }
    }

    public void aviso(GameContainer gc, Graphics g, String[] aviso) {
        g.setColor(new Color(0f, 0f, 0f, 0.7f));
        g.fillRoundRect(gc.getWidth() / 2 - 175 - this.player.offsetx, 20 + 3 + 20 - this.player.offsety, 350, 60, 5);
        g.setColor(Color.white);

        for (int i = 0; i < aviso.length; i++) {
            g.drawString(aviso[i], gc.getWidth() / 2 - 150 - this.player.offsetx, 50 + (25 * i) - this.player.offsety);
        }
    }

    public void procuraPortal(GameContainer gc, Graphics g){
        int xPlayer = this.player.getX();
        int yPlayer = this.player.getY();

        double anguloAtePortal = util.Util.calculaAngulo(xPortal, xPlayer, yPortal, yPlayer);
        String direcao = "";
        
        if (anguloAtePortal <= 67.5 && anguloAtePortal > 22.5) {
            direcao = "Nordeste";
        } else if (anguloAtePortal < 112.5 && anguloAtePortal > 67.5) {
            direcao = "Norte";
        } else if (anguloAtePortal <= 157.5 && anguloAtePortal > 112.5) {
            direcao = "Noroeste";
        } else if (anguloAtePortal < 202.5 && anguloAtePortal > 157.5) {
            direcao = "Oeste";
        } else if (anguloAtePortal <= 247.5 && anguloAtePortal > 202.5) {
            direcao = "Sudoeste";
        } else if (anguloAtePortal < 292.5 && anguloAtePortal > 247.5) {
            direcao = "Sul";
        } else if (anguloAtePortal <= 337.5 && anguloAtePortal > 292.5) {
            direcao = "Sudeste";
        } else if (anguloAtePortal < 22.5 || anguloAtePortal > 337.5) {
            direcao = "Leste";
        }

        String[] aviso = new String[2];
        aviso[0] = "Você ve uma luz estranha";
        aviso[1] = "a " + direcao + " de onde você está.";
        this.aviso(gc, g, aviso);
    }
    
    public void procuraBoss(GameContainer gc, Graphics g) {
        int xBoss = this.boss.getX();
        int yBoss = this.boss.getY();
        int xPlayer = this.player.getX();
        int yPlayer = this.player.getY();

        double anguloAteBoss = util.Util.calculaAngulo(xBoss, xPlayer, yBoss, yPlayer);
        String direcao = "";

        if (anguloAteBoss <= 67.5 && anguloAteBoss > 22.5) {
            direcao = "Nordeste";
        } else if (anguloAteBoss < 112.5 && anguloAteBoss > 67.5) {
            direcao = "Norte";
        } else if (anguloAteBoss <= 157.5 && anguloAteBoss > 112.5) {
            direcao = "Noroeste";
        } else if (anguloAteBoss < 202.5 && anguloAteBoss > 157.5) {
            direcao = "Oeste";
        } else if (anguloAteBoss <= 247.5 && anguloAteBoss > 202.5) {
            direcao = "Sudoeste";
        } else if (anguloAteBoss < 292.5 && anguloAteBoss > 247.5) {
            direcao = "Sul";
        } else if (anguloAteBoss <= 337.5 && anguloAteBoss > 292.5) {
            direcao = "Sudeste";
        } else if (anguloAteBoss < 22.5 || anguloAteBoss > 337.5) {
            direcao = "Leste";
        }

        String[] aviso = new String[2];
        aviso[0] = "Você ouve um barulho estranho";
        aviso[1] = "a " + direcao + " de onde você está.";
        this.aviso(gc, g, aviso);
    }
}
