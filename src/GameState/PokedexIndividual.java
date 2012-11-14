/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import DAO.PokemonDAO;
import java.util.ArrayList;
import model.Pokemon;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import tcc.Animacao;

/**
 *
 * @author maike_p_santos
 */
public class PokedexIndividual extends BasicGameState {

    public static final int ID = 10;
    StateBasedGame game;
    public static Pokemon pokemon;
    Animacao animacao;
    ArrayList<Image> imgsAnimacao;
    int id;
    String nome;
    int hp;
    int atk;
    int def;
    int spd;
    int tipoPrimario;
    int tipoSecundario;
    String ataque;
    int forcaAtaque;
    int elementoAtaqueId;
    String elementoAtaque;
    String tipoPrimarioString;
    String tipoSecundarioString;
    int[] fraquezas;
    Sound somSelect;
    Sound somMove;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.animacao = new Animacao(300);
        this.imgsAnimacao = new ArrayList<Image>();

        this.somSelect = new Sound("resources/sounds/misc/select.wav");
        this.somMove = new Sound("resources/sounds/misc/move.wav");
        System.out.println("PokedexIndividual loaded.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        this.animacao.update();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        this.setAttributes();

        this.desenhaFundo(gc, g);
        this.desenhaElementoPrimario(g);
        this.desenhaElementoSecundario(g);
        this.desenhaStats(gc, g);
        this.desenhaStatsAtaque(g);

        this.imgsAnimacao.clear();
        this.imgsAnimacao.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Down.png"));
        this.imgsAnimacao.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Down2.png"));
        this.animacao.setImages(this.imgsAnimacao);

        this.animacao.render(200, 260, 1, true);
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_BACK) {
            this.somSelect.play();
            this.game.enterState(Pokedex.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        if (key == Input.KEY_LEFT) {
            this.somMove.play();
            this.pokemon = PokemonDAO.getPokemon(this.id - 1);
            if (this.id - 1 == 0) {
                ArrayList<Pokemon> lista = PokemonDAO.getLista();
                this.pokemon = lista.get(lista.size() - 1);
            }
        }
        if (key == Input.KEY_RIGHT) {
            this.somMove.play();
            this.pokemon = PokemonDAO.getPokemon(this.id + 1);
            if (this.pokemon.getNome() == null) {
                this.pokemon = PokemonDAO.getPokemon(1);
            }
        }
    }

    public void desenhaElementoPrimario(Graphics g) {
        switch (tipoPrimario) {
            case 1:// Sem elemento
                break;
            case 2:
                g.setColor(new Color(168, 168, 120));// Normal
                break;
            case 3:
                g.setColor(new Color(240, 128, 48));// Fire
                break;
            case 4:
                g.setColor(new Color(192, 48, 40));// Fighting
                break;
            case 5:
                g.setColor(new Color(104, 144, 204));// Water
                break;
            case 6:
                g.setColor(new Color(168, 144, 240));// Flying
                break;
            case 7:
                g.setColor(new Color(120, 200, 80));// Grass
                break;
            case 8:
                g.setColor(new Color(160, 64, 160));// Poison
                break;
            case 9:
                g.setColor(new Color(248, 208, 48));// Electric
                break;
            case 10:
                g.setColor(new Color(224, 192, 104));// Ground
                break;
            case 11:
                g.setColor(new Color(248, 88, 136));// Psychic
                break;
            case 12:
                g.setColor(new Color(184, 160, 56));// Rock
                break;
            case 13:
                g.setColor(new Color(152, 216, 216));// Ice
                break;
            case 14:
                g.setColor(new Color(168, 184, 32));// Bug
                break;
            case 15:
                g.setColor(new Color(112, 56, 248));// Dragon
                break;
            case 16:
                g.setColor(new Color(112, 88, 152));// Ghost
                break;
            case 17:
                g.setColor(new Color(112, 88, 72));// Dark
                break;
            case 18:
                g.setColor(new Color(184, 184, 208));// Steel
                break;
        }
        if (this.tipoSecundario == 1) {
            g.fillRoundRect(150, 345, 100, 30, 5);
            g.setColor(Color.white);
            g.drawString("" + this.tipoPrimarioString, 200 - g.getFont().getWidth("" + this.tipoPrimarioString) / 2, 350);
        } else {
            g.fillRoundRect(100, 345, 100, 30, 5);
            g.setColor(Color.white);
            g.drawString("" + this.tipoPrimarioString, 150 - g.getFont().getWidth("" + this.tipoPrimarioString) / 2, 350);
        }
    }

    public void desenhaElementoSecundario(Graphics g) {
        switch (tipoSecundario) {
            case 1:// Sem elemento
                break;
            case 2:
                g.setColor(new Color(168, 168, 120));// Normal
                break;
            case 3:
                g.setColor(new Color(240, 128, 48));// Fire
                break;
            case 4:
                g.setColor(new Color(192, 48, 40));// Fighting
                break;
            case 5:
                g.setColor(new Color(104, 144, 204));// Water
                break;
            case 6:
                g.setColor(new Color(168, 144, 240));// Flying
                break;
            case 7:
                g.setColor(new Color(120, 200, 80));// Grass
                break;
            case 8:
                g.setColor(new Color(160, 64, 160));// Poison
                break;
            case 9:
                g.setColor(new Color(248, 208, 48));// Electric
                break;
            case 10:
                g.setColor(new Color(224, 192, 104));// Ground
                break;
            case 11:
                g.setColor(new Color(248, 88, 136));// Psychic
                break;
            case 12:
                g.setColor(new Color(184, 160, 56));// Rock
                break;
            case 13:
                g.setColor(new Color(152, 216, 216));// Ice
                break;
            case 14:
                g.setColor(new Color(168, 184, 32));// Bug
                break;
            case 15:
                g.setColor(new Color(112, 56, 248));// Dragon
                break;
            case 16:
                g.setColor(new Color(112, 88, 152));// Ghost
                break;
            case 17:
                g.setColor(new Color(112, 88, 72));// Dark
                break;
            case 18:
                g.setColor(new Color(184, 184, 208));// Steel
                break;
        }
        if (this.tipoSecundario != 1) {
            g.fillRoundRect(200, 345, 100, 30, 5);
            g.setColor(Color.white);
            g.drawString("" + this.tipoSecundarioString, 250 - g.getFont().getWidth("" + this.tipoSecundarioString) / 2, 350);
        }

    }

    private void setAttributes() {
        this.id = this.pokemon.getId();
        this.nome = this.pokemon.getNome();
        this.hp = this.pokemon.getHpBase();
        this.atk = this.pokemon.getAtkBase();
        this.def = this.pokemon.getDefBase();
        this.spd = this.pokemon.getSpdBase();
        this.tipoPrimario = this.pokemon.getElementoPrimario();
        this.tipoSecundario = this.pokemon.getElementoSecundario();
        this.tipoPrimarioString = this.pokemon.getElementoPrimarioString();
        this.tipoSecundarioString = this.pokemon.getElementoSecundarioString();
        this.ataque = this.pokemon.getNomeAtaque();
        this.forcaAtaque = this.pokemon.getForcaAtaque();
        this.elementoAtaque = this.pokemon.getElementoAtaque();
        this.elementoAtaqueId = this.pokemon.getElementoAtaqueId();
        this.fraquezas = new int[]{}; //fraquezas, nao implementado ainda
    }

    private void desenhaFundo(GameContainer gc, Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(25, 25, gc.getWidth() - 50, gc.getHeight() - 50);
        g.setColor(Color.darkGray);
        g.fillRoundRect(125, 185, 150, 150, 3);
        g.fillRoundRect(375, 185, 250, 150, 3);
        g.fillRoundRect(gc.getWidth() / 2 - 100, 85, 200, 50, 3);
        g.fillRoundRect(100, 400, 200, 150, 3);
        g.fillRoundRect(350, 400, 300, 150, 3);
        g.setColor(Color.white);
        g.drawRoundRect(125, 185, 150, 150, 3);
        g.drawRoundRect(375, 185, 250, 150, 3);
        g.drawRoundRect(gc.getWidth() / 2 - 100, 85, 200, 50, 3);
        g.drawRoundRect(100, 400, 200, 150, 3);
        g.drawRoundRect(350, 400, 300, 150, 3);
    }

    private void desenhaStats(GameContainer gc, Graphics g) {

        g.setColor(Color.white);
        g.drawString("" + this.nome, gc.getWidth() / 2 - g.getFont().getWidth("" + this.nome) / 2, 100);
        g.drawString("NO: " + this.id, 400, 200);
        g.drawString("HP: " + this.hp, 400, 225);
        g.fillRoundRect(500, 225 - 3 + g.getFont().getHeight("HP: " + this.hp) / 2, this.hp, 10, 3);
        g.drawString("ATK: " + this.atk, 400, 250);
        g.fillRoundRect(500, 250 - 3 + g.getFont().getHeight("ATK: " + this.atk) / 2, this.atk, 10, 3);
        g.drawString("DEF: " + this.def, 400, 275);
        g.fillRoundRect(500, 275 - 3 + g.getFont().getHeight("DEF: " + this.def) / 2, this.def, 10, 3);
        g.drawString("SPD: " + this.spd, 400, 300);
        g.fillRoundRect(500, 300 - 3 + g.getFont().getHeight("SPD: " + this.spd) / 2, this.spd, 10, 3);
    }

    private void desenhaStatsAtaque(Graphics g) {
        String[] teste = this.ataque.split("(?=[A-Z])");
        String nomeAtk = "";
        for (int i = 0; i < teste.length; i++) {
            if (!(teste[i].equals(""))) {
                nomeAtk += teste[i] + " ";
            }
        }
        g.drawString("" + nomeAtk, 375, 440);
        g.drawString("Atk: " + this.forcaAtaque, 375, 465);
        g.fillRoundRect(475, 465 - 3 + g.getFont().getHeight("Atk: " + this.forcaAtaque) / 2, this.forcaAtaque, 10, 3);

        switch (elementoAtaqueId) {
            case 1:// Sem elemento
                break;
            case 2:
                g.setColor(new Color(168, 168, 120));// Normal
                break;
            case 3:
                g.setColor(new Color(240, 128, 48));// Fire
                break;
            case 4:
                g.setColor(new Color(192, 48, 40));// Fighting
                break;
            case 5:
                g.setColor(new Color(104, 144, 204));// Water
                break;
            case 6:
                g.setColor(new Color(168, 144, 240));// Flying
                break;
            case 7:
                g.setColor(new Color(120, 200, 80));// Grass
                break;
            case 8:
                g.setColor(new Color(160, 64, 160));// Poison
                break;
            case 9:
                g.setColor(new Color(248, 208, 48));// Electric
                break;
            case 10:
                g.setColor(new Color(224, 192, 104));// Ground
                break;
            case 11:
                g.setColor(new Color(248, 88, 136));// Psychic
                break;
            case 12:
                g.setColor(new Color(184, 160, 56));// Rock
                break;
            case 13:
                g.setColor(new Color(152, 216, 216));// Ice
                break;
            case 14:
                g.setColor(new Color(168, 184, 32));// Bug
                break;
            case 15:
                g.setColor(new Color(112, 56, 248));// Dragon
                break;
            case 16:
                g.setColor(new Color(112, 88, 152));// Ghost
                break;
            case 17:
                g.setColor(new Color(112, 88, 72));// Dark
                break;
            case 18:
                g.setColor(new Color(184, 184, 208));// Steel
                break;
        }
        g.fillRoundRect(375, 490, 100, 30, 5);
        g.setColor(Color.white);
        g.drawString("" + this.elementoAtaque, 425 - g.getFont().getWidth("" + this.elementoAtaque) / 2, 495);
    }

}