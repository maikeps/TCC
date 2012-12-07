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
    ArrayList<String> listaNomesPokemonLiberado;

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
        this.listaNomesPokemonLiberado = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            this.pokemonsNaTela.add(this.listaDePokemon.get(i));
        }
        for (PokemonLiberado pl : this.listaDePokemonLiberado) {
            this.listaNomesPokemonLiberado.add(pl.getNome());
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
        g.setColor(new Color(1f, 1f, 1f, 0.6f));
        g.fillRect(50, (this.linha * 75) - 35, gc.getWidth() - 100, 75);


        for (int i = 0; i < this.pokemonsNaTela.size(); i++) {
            g.setColor(Color.black);
            String status = "Locked";
            if (this.listaNomesPokemonLiberado.contains(this.pokemonsNaTela.get(i).getNome())) {
                status = "Down";
            }
            Image img = new Image("resources/personagens/" + this.pokemonsNaTela.get(i).getId() + " - " + this.pokemonsNaTela.get(i).getNome() + "/" + this.pokemonsNaTela.get(i).getNome() + "_" + status + ".png");
            img.drawCentered(100, 75 + (75 * i));
            g.drawString("" + this.pokemonsNaTela.get(i).getNome(), 200, 75 + (75 * i));
            int vezesDerrotado = this.listaDePokemonDerrotado.get(this.pokemonsNaTela.get(i).getId() - 1).getVezesDerrotado();
            int vezesMinima = this.listaDePokemon.get(this.pokemonsNaTela.get(i).getId() - 1).getRaridade();
            
            if(status.equals("Locked")){
            g.drawString("" + vezesDerrotado + " de " + vezesMinima, 400, 75 + (75 * i));
            }else{
            g.drawString("Liberado!", 400, 75 + (75 * i));
            }
            if (vezesMinima > 0 && status.equals("Locked")) {
                double total = 100;
                double fracao = (100 * vezesDerrotado) / vezesMinima;
                g.setColor(Color.black);
                g.fillRoundRect(499, 74 + (75 * i), (int) total + 2, 22, 2);
                g.setColor(Color.white);
                g.fillRoundRect(500, 75 + (75 * i), (int) fracao, 20, 2);
                g.setColor(Color.black);
                g.drawString("" + (int) fracao + "%", 615, 75 + (75 * i));
            }
        }
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
                if (this.selecionado == this.listaDePokemon.size()) {
                    this.pokemonsNaTela.clear();
                    for (int i = this.listaDePokemon.size() - 7; i < this.listaDePokemon.size(); i++) {
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
                if (this.selecionado == 1) {
                    this.pokemonsNaTela.clear();
                    for (int i = 0; i < 7; i++) {
                        this.pokemonsNaTela.add(this.listaDePokemon.get(i));
                    }
                } else {
                    this.pokemonsNaTela.remove(0);
                    this.pokemonsNaTela.add(this.listaDePokemon.get(this.selecionado - 1));
                }
            }
        }
    }
}
