/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay2.*;
import javaPlay2.GameEngine;
import javaPlay2.GameStateController;
import javaPlay2.Imagem;
import javaPlay2.Keyboard;
import javaPlay2.Keys;
import javaPlayExtras.AudioPlayer;
import javax.swing.JOptionPane;
import util.Util;

/**
 *
 * @author maike_p_santos
 */
public class CharacterSelect implements GameStateController {

    private Imagem Cenario;
    private Imagem Charmander;
    private Imagem Charmeleon;
    private Imagem Charizard;
    private Imagem Bulbasaur;
    private Imagem Ivysaur;
    private Imagem Venusaur;
    private Imagem Pidgey;
    private Imagem Pidgeotto;
    private Imagem Pidgeot;
    private Imagem Squirtle;
    private Imagem Pikachu;
    private Imagem Wartortle;
    private Imagem Blastoise;
    private Imagem Caterpie;
    private Imagem Metapod;
    private String player1;
    private String inimigo;
    private int xSelecionado;
    private int ySelecionado;
    private int xDraw;
    private int yDraw;

    public CharacterSelect(String p1) {
        this.ySelecionado = 1;
        this.player1 = p1;
        this.xDraw = -25;
        this.yDraw = 75;
    }

    public void load() {

        try {
            this.Cenario = new Imagem("resources/Cenario/493pokemons2 preto.png");

            this.Charmander = new Imagem("resources/personagens/Charmander/Charmander_Down.gif");
            this.Charmeleon = new Imagem("resources/personagens/Charmeleon/Charmeleon_Down.gif");
            this.Charizard = new Imagem("resources/personagens/Charizard/Charizard_Down.gif");
            this.Bulbasaur = new Imagem("resources/personagens/Bulbasaur/Bulbasaur_Down.gif");
            this.Ivysaur = new Imagem("resources/personagens/Ivysaur/Ivysaur_Down.gif");
            this.Venusaur = new Imagem("resources/personagens/Venusaur/Venusaur_Down.gif");
            this.Pidgey = new Imagem("resources/personagens/Pidgey/Pidgey_Down.gif");
            this.Pidgeotto = new Imagem("resources/personagens/Pidgeotto/Pidgeotto_Down.gif");
            this.Pidgeot = new Imagem("resources/personagens/Pidgeot/Pidgeot_Down.gif");
            this.Squirtle = new Imagem("resources/personagens/Squirtle/Squirtle_Down.gif");
            this.Pikachu = new Imagem("resources/personagens/Pikachu/Pikachu_Down.gif");
            this.Wartortle = new Imagem("resources/personagens/Wartortle/Wartortle_Down.gif");
            this.Blastoise = new Imagem("resources/personagens/Blastoise/Blastoise_Down.gif");
            this.Caterpie = new Imagem("resources/personagens/Caterpie/Caterpie_Down.gif");
            this.Metapod = new Imagem("resources/personagens/Metapod/Metapod_Down.gif");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
            System.exit(1);
        }

    }

    public void step(long timeElapsed) {

        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if (teclado.keyDown(Keys.ESQUERDA)) {
            if (this.xSelecionado > 1) {
                this.xSelecionado -= 1;
            } else if (this.xSelecionado <= 1) {
                this.xSelecionado = 7;
            }

            if (this.xDraw > 75) {
                this.xDraw -= 100;
            } else {
                this.xDraw = 675;
            }

            Util.sleep(150);
        }
        if (teclado.keyDown(Keys.DIREITA)) {
            if (this.xSelecionado < 7) {
                this.xSelecionado += 1;
            } else if (this.xSelecionado >= 1) {
                this.xSelecionado = 1;
            }

            if (this.xDraw < 675) {
                this.xDraw += 100;
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
                this.yDraw -= 100;
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
                this.yDraw += 100;
            } else {
                this.yDraw = 75;
            }

            Util.sleep(150);
        }

        if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 1 && this.ySelecionado == 1) {
            this.player1 = "Bulbasaur";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 2 && this.ySelecionado == 1) {
            this.player1 = "Ivysaur";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 3 && this.ySelecionado == 1) {
            this.player1 = "Venusaur";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 4 && this.ySelecionado == 1) {
            this.player1 = "Charmander";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 5 && this.ySelecionado == 1) {
            this.player1 = "Charmeleon";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 6 && this.ySelecionado == 1) {
            this.player1 = "Charizard";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 7 && this.ySelecionado == 1) {
            this.player1 = "Squirtle";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 1 && this.ySelecionado == 2) {
            this.player1 = "Wartortle";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 2 && this.ySelecionado == 2) {
            this.player1 = "Blastoise";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 3 && this.ySelecionado == 2) {
            this.player1 = "Caterpie";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 4 && this.ySelecionado == 2) {
            this.player1 = "Metapod";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 5 && this.ySelecionado == 2) {
            this.player1 = "Pidgey";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 6 && this.ySelecionado == 2) {
            this.player1 = "Pidgeotto";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 7 && this.ySelecionado == 2) {
            this.player1 = "Pidgeot";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else if (teclado.keyDown(Keys.ESPACO) && this.xSelecionado == 1 && this.ySelecionado == 3) {
            this.player1 = "Pikachu";
            Util.sleep(500);
            this.sorteiaInimigo();
            while (this.inimigo == this.getPlayer1()) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        } else {
            //nao faz nada
        }




    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 700);
        this.Cenario.draw(g, 0, 0);

        g.setColor(Color.white);
        g.drawString(" P1, escolha o personagem", 325, 420);

        if (this.xDraw >= 50) {
            g.drawRect(this.xDraw - 8, this.yDraw - 8, 80, 80);
        }

        this.Bulbasaur.draw(g, 75, 75);
        this.Ivysaur.draw(g, 175, 75);
        this.Venusaur.draw(g, 275, 75);
        this.Charmander.draw(g, 375, 75);
        this.Charmeleon.draw(g, 475, 75);
        this.Charizard.draw(g, 575, 75);
        this.Squirtle.draw(g, 675, 75);
        this.Wartortle.draw(g, 75, 175);
        this.Blastoise.draw(g, 175, 175);
        this.Caterpie.draw(g, 275, 175);
        this.Metapod.draw(g, 375, 175);
        this.Pidgey.draw(g, 475, 175);
        this.Pidgeotto.draw(g, 575, 175);
        this.Pidgeot.draw(g, 675, 175);
        this.Pikachu.draw(g, 75, 275);

    }

    public void unload() {
    }

    public void start() {
        this.sorteiaInimigo();

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
        int n = Util.random(11);
        switch (n) {
            case 1:
                this.inimigo = "Charizard";
                break;
            case 2:
                this.inimigo = "Bulbasaur";
                break;
            case 3:
                this.inimigo = "Pidgeotto";
                break;
            case 4:
                this.inimigo = "Squirtle";
                break;
            case 5:
                this.inimigo = "Pikachu";
                break;
            case 6:
                this.inimigo = "Charmander";
                break;
            case 7:
                this.inimigo = "Charmeleon";
                break;
            case 8:
                this.inimigo = "Ivysaur";
                break;
            case 9:
                this.inimigo = "Venusaur";
                break;
            case 10:
                this.inimigo = "Pidgey";
                break;
            case 11:
                this.inimigo = "Pidgeot";
                break;
        }
    }
    
    public void iniciaJogo(){
        GameEngine.getInstance().setNextGameStateController(3);
    }
}
