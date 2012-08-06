/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import DAO.PokemonDAO;
import DAO.PokemonLiberadoDAO;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javaPlay2.GameEngine;
import javaPlay2.GameStateController;
import javaPlay2.Imagem;
import javaPlay2.Keyboard;
import javaPlay2.Keys;
import javax.swing.JOptionPane;
import model.Pokemon;
import model.PokemonLiberado;
import util.Util;

/**
 *
 * @author maike_p_santos
 */
public class CharacterSelect implements GameStateController {

    private Imagem Cenario;
    private String player1;
    private String inimigo;
    private int xSelecionado;
    private int ySelecionado;
    private int xDraw;
    private int yDraw;
    int id;
    ArrayList<PokemonLiberado> listaDePokemonLiberado;
    ArrayList<Pokemon> listaDePokemon;
    ArrayList<String> nomes;
    Imagem pokemonImage;

    public CharacterSelect(String p1) {
        this.ySelecionado = 1;
        this.player1 = p1;
        this.xDraw = 75;
        this.yDraw = 75;
    }

    public void load() {

        try {
            this.Cenario = new Imagem("resources/Cenario/493pokemons2 preto.png");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

    }

    public void step(long timeElapsed) {
        this.teclas();
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 700);
        this.Cenario.draw(g, 0, 0);

        g.setColor(Color.white);
        g.drawString(" P1, escolha o personagem", 325, 420);

        g.drawRect(this.xDraw - 8, this.yDraw - 8, 80, 80);

        this.desenhaImagens(g);
        this.desenhaStats(g);
    }

    public void unload() {
    }

    public void start() {
        this.sorteiaInimigo();

        this.listaDePokemon = PokemonDAO.getLista();
        this.listaDePokemonLiberado = PokemonLiberadoDAO.getListaPokemon(1);

        this.nomes = new ArrayList<String>();

        for (Pokemon p : this.listaDePokemon) {
            this.nomes.add(p.getNome());
        }
//////////////        for (PokemonLiberado p : this.listaDePokemonLiberado) {
//////////////            this.id = p.getIdPokemon();
//////////////
//////////////            PokemonLiberado pl = PokemonLiberadoDAO.getPokemon(id);
//////////////            this.nomes.add(pl.getNome());            
//////////////            
//////////////        }


    }

    public void stop() {
    }

    public String getPlayer1() {
        return this.player1;
    }

    public String getInimigo() {
        return this.inimigo;
    }

    public int getYSelecionado() {
        return this.ySelecionado;
    }

    public int getXDraw() {
        return this.xDraw;
    }

    public void sorteiaInimigo() {
        this.nomes = new ArrayList<String>();
        this.listaDePokemon = PokemonDAO.getLista();
        this.listaDePokemonLiberado = PokemonLiberadoDAO.getListaPokemon(1);
        for (Pokemon p : this.listaDePokemon) {
            this.nomes.add(p.getNome());
        }
        int n = Util.random(this.nomes.size() + 1);
        while (n >= this.nomes.size()) {
            n = Util.random(this.nomes.size() + 1);
        }
        //System.out.println("inimigo no charSelect: "+this.nomes.get(n));
        this.inimigo = this.nomes.get(n);

    }

    public void iniciaJogo() {
        GameEngine.getInstance().setNextGameStateController(3);
    }
    
    public void desenhaStats(Graphics g){
        int i = this.xSelecionado + 10 * (this.ySelecionado - 1);
        if (i <= this.nomes.size()) {
            if (i < 10) {
                g.drawString(this.nomes.get(i) + "", 100, 100);
                Pokemon p = PokemonDAO.getPokemonPeloNome(this.nomes.get(i));
                g.setColor(Color.white);
                g.fillRect(100, 160, p.getHpBase(), 20);
                g.fillRect(100, 185, p.getAtkbase(), 20);
                g.fillRect(100, 210, p.getDefBase(), 20);
                g.fillRect(100, 235, p.getSpdbase(), 20);
                g.setColor(Color.black);
                g.drawString("HP: " + p.getHpBase(), 100, 175);
                g.drawString("ATK: " + p.getAtkbase(), 100, 200);
                g.drawString("DEF: " + p.getDefBase(), 100, 225);
                g.drawString("SPD: " + p.getSpdbase(), 100, 250);
            } else {
                g.drawString(this.nomes.get(i - 1) + "", 100, 100);
                Pokemon p = PokemonDAO.getPokemonPeloNome(this.nomes.get(i - 1));
                g.setColor(Color.white);
                g.fillRect(100, 160, p.getHpBase(), 20);
                g.fillRect(100, 185, p.getAtkbase(), 20);
                g.fillRect(100, 210, p.getDefBase(), 20);
                g.fillRect(100, 235, p.getSpdbase(), 20);
                g.setColor(Color.black);
                g.drawString("HP: " + p.getHpBase(), 100, 175);
                g.drawString("ATK: " + p.getAtkbase(), 100, 200);
                g.drawString("DEF: " + p.getDefBase(), 100, 225);
                g.drawString("SPD: " + p.getSpdbase(), 100, 250);
            }

        }
    }
    
    public void desenhaImagens(Graphics g){
        
        int x1 = 0;
        int x2 = 0;
        int cont1 = 0;
        int cont2 = 1;
        int y1 = 0;
        int y2 = 0;

        for (Pokemon p : this.listaDePokemon) {
            cont1++;
            if (cont1 > 9) {
                cont1 = 0;
                x1 = 0;
                y1 += 75;
            }

            String nome = p.getNome();


            try {
                this.pokemonImage = new Imagem("resources/personagens/" + nome + "/" + nome + "_Selected.gif");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
                System.exit(1);
            }

            this.pokemonImage.draw(g, 75 + x1, 75 + y1);
            x1 += 75;

        }

        for (PokemonLiberado pl : this.listaDePokemonLiberado) {

            int i = this.listaDePokemonLiberado.size();
            if (pl.getIdPokemon() > 9 * cont2) {
                cont2++;
                x2 = 0;
                y2 += 75;
            }

            String nome = pl.getNome();

            try {
                this.pokemonImage = new Imagem("resources/personagens/" + nome + "/" + nome + "_Down.gif");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
                System.exit(1);
            }

            this.pokemonImage.draw(g, 75 + x2 * (pl.getIdPokemon() - 1), 75 + y2);
            x2 += 75;
        }
    }
    
    public void teclas(){
        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if (teclado.keyDown(Keys.ESQUERDA)) {
            if (this.xSelecionado > 0) {
                this.xSelecionado -= 1;
            } else if (this.xSelecionado <= 0) {
                this.xSelecionado = 8;
            }

            if (this.xDraw > 75) {
                this.xDraw -= 75;
            } else {
                this.xDraw = 675;
            }

            Util.sleep(150);
        }
        if (teclado.keyDown(Keys.DIREITA)) {
            if (this.xSelecionado < 8) {
                this.xSelecionado += 1;
            } else if (this.xSelecionado >= 0) {
                this.xSelecionado = 0;
            }

            if (this.xDraw < 675) {
                this.xDraw += 75;
            } else {
                this.xDraw = 75;
            }

            Util.sleep(150);
        }

        if (teclado.keyDown(Keys.CIMA)) {
            if (this.ySelecionado > 1) {
                this.ySelecionado -= 1;
            } else if (this.ySelecionado <= 1) {
                this.ySelecionado = 3;
            }

            if (this.yDraw > 75) {
                this.yDraw -= 75;
            } else {
                this.yDraw = 275;
            }

            Util.sleep(150);
        }
        if (teclado.keyDown(Keys.BAIXO)) {
            if (this.ySelecionado < 3) {
                this.ySelecionado += 1;
            } else if (this.ySelecionado >= 1) {
                this.ySelecionado = 1;
            }

            if (this.yDraw < 275) {
                this.yDraw += 75;
            } else {
                this.yDraw = 75;
            }

            Util.sleep(150);
        }
        
        
        int n = this.xSelecionado + 10 * (this.ySelecionado - 1);
        if (n >= 10) {
            n -= 1;
        }

        Pokemon p = this.listaDePokemon.get(n);
        String nome = p.getNome();
        PokemonLiberado pl = PokemonLiberadoDAO.getPokemonPeloNome(nome);

        if (teclado.keyDown(Keys.ESPACO) && pl.getNome() != null) {
            int i = this.xSelecionado + 10 * (this.ySelecionado - 1);
            if (i <= this.nomes.size()) {
                if (i < 10) {
                    this.player1 = this.nomes.get(i);
                } else {
                    this.player1 = this.nomes.get(i - 1);
                }

                Util.sleep(500);
                this.sorteiaInimigo();
                while (this.inimigo.equals(this.getPlayer1())) {
                    this.sorteiaInimigo();
                }
                this.iniciaJogo();
            }


        } 
        
    }
}
