/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Maike
 */
public class Options extends BasicGameState {

    public static final int ID = 4;
    StateBasedGame game;
    GameContainer gc;
    public static String[] options = {"Audio Options", "Video Options", "Accept"};
    private int selected;
    boolean fullScreen = false;
    public static int idPreviousGameState;
    Sound som;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.gc = gc;
        this.som = new Sound("resources/sounds/misc/select.wav");
        System.out.println("Options loaded.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        
        for (int i = 0; i < this.options.length; i++) {
            g.drawString(options[i], gc.getWidth() / 2 - g.getFont().getWidth(this.options[i]) / 2, 400 + (50 * i));
            String string = "";
            g.drawString(string, gc.getWidth() / 2 + 125, 400 + (50 * i));
            if (i == this.selected) {
                g.drawRect(gc.getWidth() / 2 - 100, 385 + (50 * i), 200, 50);
            }
        }
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_DOWN) {
            this.som.play();
            this.selected++;
            if (this.selected >= this.options.length) {
                this.selected = 0;
            }
        }
        if (key == Input.KEY_UP) {
            this.som.play();
            this.selected--;
            if (this.selected < 0) {
                this.selected = this.options.length - 1;
            }
        }

        if (key == Input.KEY_ENTER) {
            this.som.play();
            if (this.options[this.selected].equals("Audio Options")) {
                AudioOptions.idPreviousGameState = this.getID();
                this.game.enterState(AudioOptions.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if (this.options[this.selected].equals("Video Options")) {
                VideoOptions.idPreviousGameState = this.getID();
                this.game.enterState(VideoOptions.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if (this.options[this.selected].equals("Accept")) {
                this.game.enterState(this.idPreviousGameState, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
    }
}
