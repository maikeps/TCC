/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import Ataques.*;
import DAO.AtaqueDAO;
import DAO.PokemonDAO;
import DAO.PokemonDerrotadoDAO;
import DAO.PokemonInimigoDAO;
import DAO.PokemonLiberadoDAO;
import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import Personagens.Personagem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Point;
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
import model.PokemonDerrotado;
import model.PokemonInimigo;
import model.PokemonLiberado;
import pixelPerfect.GameObjectImagePixelPerfect;

/**
 *
 * @author maike_p_santos
 */
public class Fase1 implements GameStateController {

    CharacterSelect CharSelect;
    Player player;
    Inimigo inimigo;
    ArrayList<Ataque> ataquesPlayer;
    ArrayList<Ataque> ataquesInimigo;
    Personagem p;
    Personagem p2;
    Font f;

    public Fase1(CharacterSelect CharSelect) {
        this.CharSelect = CharSelect;
    }

    public void load() {
        this.ataquesPlayer = new ArrayList<Ataque>();
        this.ataquesInimigo = new ArrayList<Ataque>();
    }

    public void step(long timeElapsed) {

        this.verificaSePlayerEstaMorto();
        this.verificaSeInimigoEstaMorto();


        this.player.step(timeElapsed);
        this.inimigo.step(timeElapsed);


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

        this.inimigo.setXPlayer(this.player.getX()); //atualiza as informacoes do player para o inimigo
        this.inimigo.setYPlayer(this.player.getY()); //atualiza as informacoes do player para o inimigo


        this.verificaColisao();
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


        //desenha os personagens e os ataques
        this.player.draw(g);
        this.inimigo.draw(g);

        for (Ataque a : this.ataquesPlayer) {
            a.draw(g);
        }
        for (Ataque a : this.ataquesInimigo) {
            a.draw(g);
        }

        this.desenhaHealthBar(g);

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

        if (this.inimigo.atacou == true) {
            if (this.inimigo.personagem.podeAtirar()) {
                model.Ataque a = AtaqueDAO.getPoder(this.CharSelect.getInimigo());
                String s = "Ataques." + a.getNome();
                try {
                    Class cls = Class.forName(s);
                    Class[] parameters = new Class[]{int.class, int.class, int.class, int.class, double.class, Personagem.class};
                    java.lang.reflect.Constructor con = cls.getConstructor(parameters);
                    Object o = con.newInstance(new Object[]{this.inimigo.getX(), this.inimigo.getY(), this.player.getX(), this.player.getY(), this.inimigo.getAngulo(), this.inimigo.getPersonagem()});
                    this.ataquesInimigo.add((Ataque) o);
                } catch (ClassNotFoundException ex) {
                    this.CharSelect.sorteiaInimigo();
                    this.criaInimigo();
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

                this.inimigo.personagem.setCooldownAtual();
            }
        }
        this.inimigo.atacou = false;
    }

    public void criaPlayer1() {

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

        this.player = new Player(this.p);



    }

    public void criaInimigo() {
        Pokemon pokemon = PokemonDAO.getPokemonPeloNome(CharSelect.getInimigo());

        String nome = pokemon.getNome();
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

    //o problema esta na imagem do ataque, por que se fizer colisao de player com inimigo funciona
    //so da erro se a imagem for rotacionada
    //ember funciona perfeitamente
    public void verificaColisao() {
        //colisao ataque inimigo com player
        for (Ataque a : this.ataquesInimigo) {
            Point colisao = this.player.personagem.spriteAtual.temColisao(a.imagem);
            if (colisao != null) {
                if (a.desativado == false) {
                    int lvl = this.inimigo.personagem.getLvl();
                    int danoDoAtk = a.getDano();
                    int atkDoPokemon = this.inimigo.personagem.getAtk();
                    int defDoOponente = this.player.personagem.getDef();
                    int r = 100 - util.Util.random(15);
                    int multiplicador = 1; //fazer busca no banco
                    int dano = (((((((lvl * 2 / 5) + 2) * danoDoAtk * atkDoPokemon / 50) / defDoOponente) + 2) * r / 100) * multiplicador);
                    System.out.println(this.CharSelect.getPlayer1() + " took " + dano + " damage!");
                    this.player.personagem.perdeHp(dano);
                }
                a.acertou = true;
                a.desativado();
                //pra mandar pro sei-la-o-que garbage collector
                a = null;
            }
        }

        //colisao ataque player com inimigo
        for (Ataque a : this.ataquesPlayer) {
            Point colisao = this.inimigo.personagem.spriteAtual.temColisao(a.imagem);
            if (colisao != null) {
                if (a.desativado == false) {
                    int lvl = this.player.personagem.getLvl();
                    int danoDoAtk = a.getDano();
                    int atkDoPokemon = this.player.personagem.getAtk();
                    int defDoOponente = this.inimigo.personagem.getDef();
                    int r = 100 - util.Util.random(15);
                    int multiplicador = 1; //fazer busca no banco
                    int dano = (((((((lvl * 2 / 5) + 2) * danoDoAtk * atkDoPokemon / 50) / defDoOponente) + 2) * r / 100) * multiplicador);
                    System.out.println(this.CharSelect.getInimigo() + " took " + dano + " damage!");
                    System.out.println(atkDoPokemon);
                    System.out.println(defDoOponente);
                    this.inimigo.personagem.perdeHp(dano);



                    //faz update no campo totalDanoCausado

                    int idPlayer = this.player.personagem.getId();
                    PokemonLiberado pokeLiberado = PokemonLiberadoDAO.getPokemon(idPlayer);
                    int danoTotal = pokeLiberado.getTotalDanoCausado();
                    int danoTotalDepois = danoTotal + dano;


                    MySQL banco = new MySQL();
                    String sql = "update pokemonLiberado set totalDanoCausado = " + danoTotalDepois + " where idPokemon = " + idPlayer;
                    boolean bool = banco.executaUpdate(sql);

                }
                a.acertou = true;
                a.desativado();
                a = null;
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
        if (this.inimigo.personagem.estaMorto()) {
            int idPlayer = this.player.personagem.getId();
            int idInimigo = this.inimigo.personagem.getId();
            int lvlInimigo = this.inimigo.personagem.getLvl();

            //pega as informacoes do inimigo e calcula a exp a ser adquirida
            Pokemon pokeInimigo = PokemonDAO.getPokemon(this.inimigo.personagem.getId());
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
                this.criaPlayer1();
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
            
            //sorteia o inimigo novamente
            this.CharSelect.sorteiaInimigo();
            //e cria outro inimigo
            this.criaInimigo();
            
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
            this.criaPlayer1();
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
            this.criaPlayer1();

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
}
