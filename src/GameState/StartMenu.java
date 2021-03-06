/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
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
public class StartMenu extends BasicGameState {

    public static final int ID = 1;
    StateBasedGame game;
    private Image imagem;
    int cont = 0;
    Color cor = Color.white;
    int veloc = 1;
    boolean draw = true;
    Music musica;
    Sound somSelect;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        this.imagem = new Image("resources/Title.png");
        this.musica = new Music("resources/sounds/music/Pokemon Opening.wav");
        this.somSelect = new Sound("resources/sounds/misc/select.wav");
        System.out.println("StartMenu loaded.");
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(!this.musica.playing()){
            this.musica.play();
        }
        this.cont += this.veloc;
        if (this.cont >= 30) {
            this.veloc = -1;
            this.draw = false;
        } else if (this.cont <= 0) {
            this.veloc = 1;
            this.draw = true;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        
        this.imagem.draw(195, 50);
        if (this.draw) {
            g.drawString("Pressione ENTER", gc.getWidth() / 2 - g.getFont().getWidth("Pressione ENTER") / 2, 400);
        }
    }

    @Override
    public void keyPressed(int key, char c) {

        if (key == Input.KEY_ENTER) {
            this.somSelect.play();
            game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }

    }
}
