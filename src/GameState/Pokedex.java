/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import DAO.PokemonDAO;
import DAO.PokemonDerrotadoDAO;
import DAO.PokemonLiberadoDAO;
import java.util.ArrayList;
import model.Pokemon;
import model.PokemonDerrotado;
import model.PokemonLiberado;
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

/**
 *
 * @author maike_p_santos
 */
public class Pokedex extends BasicGameState {

    public static final int ID = 9;
    StateBasedGame game;
    ArrayList<Pokemon> listaDePokemon;
    ArrayList<PokemonLiberado> listaDePokemonLiberado;
    ArrayList<PokemonDerrotado> listaDePokemonDerrotado;
    int selecionado = 1;
    int yDraw = 50;
    int linha = 1;// de 1 ate 7, a linha em que o retangulo esta, na tela
    Sound somSelect;
    Sound somMove;
    ArrayList<Pokemon> pokemonsNaTela;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.listaDePokemon = PokemonDAO.getLista();
        this.listaDePokemonLiberado = PokemonLiberadoDAO.getListaPokemon(1);
        this.listaDePokemonDerrotado = PokemonDerrotadoDAO.getLista();
        this.pokemonsNaTela = new ArrayList<Pokemon>();
        for (int i = 0; i < 7; i++) {
            this.pokemonsNaTela.add(this.listaDePokemon.get(i));
        }

        this.somSelect = new Sound("resources/sounds/misc/select.wav");
        this.somMove = new Sound("resources/sounds/misc/move.wav");
        System.out.println("Pokedex loaded.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.lightGray);
        g.fillRect(25, 25, gc.getWidth() - 50, gc.getHeight() - 50);

        int y1 = 0;
        int y2 = 0;
        //ERRO TA AQUI
        if (this.selecionado > 7) {
            y1 -= 75 * (this.selecionado - this.linha);
            y2 -= 75 * (this.selecionado - this.linha);
        }

        g.setColor(new Color(1f, 1f, 1f, 0.6f));
        g.fillRect(50, (this.linha * 75) - 35, gc.getWidth() - 100, 75);


        ////////////////////////////////
        for (int i = 0; i < this.pokemonsNaTela.size(); i++) {
            Image img = new Image("resources/personagens/" + this.pokemonsNaTela.get(i).getId() + " - " + this.pokemonsNaTela.get(i).getNome() + "/" + this.pokemonsNaTela.get(i).getNome() + "_Locked.gif");
            img.drawCentered(450, 75 + (75 * i));
            g.drawString("" + this.pokemonsNaTela.get(i).getNome(), 500, 75 + (75 * i));
        }
        ////////////////////////////////



        for (Pokemon p : this.listaDePokemon) {
            y1 += 75;
            Image img = new Image("resources/personagens/" + p.getId() + " - " + p.getNome() + "/" + p.getNome() + "_Locked.gif");
            // img.drawCentered(75, 75 + (75 * this.listaDePokemon.indexOf(p)));
            img.drawCentered(100, y1);
            g.setColor(Color.black);
            g.drawString("" + p.getNome(), 200, y1);
            int vezesDerrotado = this.listaDePokemonDerrotado.get(p.getId() - 1).getVezesDerrotado();
            g.drawString("" + vezesDerrotado, 400, y1);
        }
        for (PokemonLiberado p : this.listaDePokemonLiberado) {
            //y2 = 75 * p.getIdPokemon();
            Image img = new Image("resources/personagens/" + p.getIdPokemon() + " - " + p.getNome() + "/" + p.getNome() + "_Down.gif");
            img.drawCentered(100, y2 + (75 * p.getIdPokemon()));
        }

        g.drawString(this.selecionado + " - linha: " + this.linha, 200, 200);
        g.drawString("Pressione o botão 'BACKSPACE' para voltar", gc.getWidth() / 2 - g.getFont().getWidth("Pressione o botão 'BACKSPACE' para voltar") / 2, 560);
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ENTER) {
            this.somSelect.play();
            int id = this.listaDePokemon.get(this.selecionado - 1).getId();
            PokedexIndividual.pokemon = PokemonDAO.getPokemon(id);
            this.game.enterState(PokedexIndividual.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        if (key == Input.KEY_BACK) {
            this.somSelect.play();
            this.game.enterState(PauseScreen.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

        }
        if (key == Input.KEY_UP) {
            this.somMove.play();
            this.selecionado--;
            if (this.linha > 1) {
                this.linha--;
            }
            if (this.linha == 1) {
                if (this.selecionado <= 0) {
                    this.selecionado = this.listaDePokemon.size();
                    this.linha = 7;
                }
                ///////////////////////////////////////
                if (this.selecionado == this.listaDePokemon.size()) {
                    this.pokemonsNaTela.clear();
                    for (int i = this.listaDePokemon.size()-7; i < this.listaDePokemon.size(); i++) {
                        this.pokemonsNaTela.add(this.listaDePokemon.get(i));
                    }
                } else {
                    this.pokemonsNaTela.set(0, this.listaDePokemon.get(this.selecionado - 1));
                    this.pokemonsNaTela.set(1, this.listaDePokemon.get(this.selecionado));
                    this.pokemonsNaTela.set(2, this.listaDePokemon.get(this.selecionado + 1));
                    this.pokemonsNaTela.set(3, this.listaDePokemon.get(this.selecionado + 2));
                    this.pokemonsNaTela.set(4, this.listaDePokemon.get(this.selecionado + 3));
                    this.pokemonsNaTela.set(5, this.listaDePokemon.get(this.selecionado + 4));
                    this.pokemonsNaTela.set(6, this.listaDePokemon.get(this.selecionado + 5));
                }
                ///////////////////////////////////////

            }
        }
        if (key == Input.KEY_DOWN) {
            this.somMove.play();
            this.selecionado++;
            if (this.linha < 7) {
                this.linha++;
            }
            if (linha == 7) {
                if (this.selecionado > this.listaDePokemon.size()) {
                    this.selecionado = 1;
                    this.linha = 1;
                }
                ///////////////////////////////////////
                if (this.selecionado == 1) {
                    this.pokemonsNaTela.clear();
                    for (int i = 0; i < 7; i++) {
                        this.pokemonsNaTela.add(this.listaDePokemon.get(i));
                    }
                } else {
                    this.pokemonsNaTela.remove(0);
                    this.pokemonsNaTela.add(this.listaDePokemon.get(this.selecionado - 1));
                }
                ///////////////////////////////////////
            }

        }
    }
}
