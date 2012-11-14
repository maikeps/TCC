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
public class AudioOptions extends BasicGameState {

    public static final int ID = 12;
    StateBasedGame game;
    GameContainer gc;
    String[] options = {"Sound Volume", "Music Volume", "Accept"};
    private int selected;
    public static int idPreviousGameState;
    Sound somSelect;
    Sound somMove;
    
    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.gc = gc;
        
        this.somSelect = new Sound("resources/sounds/misc/select.wav");
        this.somMove = new Sound("resources/sounds/misc/move.wav");
        System.out.println("AudioOptions loaded.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);

        for (int i = 0; i < this.options.length; i++) {
            g.drawString(options[i], gc.getWidth() / 2 - g.getFont().getWidth(this.options[i]) / 2, 400 + (50 * i));
            if (this.options[i].equals("Sound Volume")) {
                g.drawRect(gc.getWidth() / 2 + 150, 395+(50*i), 100, 20);
                g.fillRect(gc.getWidth() / 2 + 150, 395+(50*i), gc.getSoundVolume() * 100, 20);
            } else if (this.options[i].equals("Music Volume")) {
                g.drawRect(gc.getWidth() / 2 + 150, 395+(50*i), 100, 20);
                g.fillRect(gc.getWidth() / 2 + 150, 395+(50*i), gc.getMusicVolume() * 100, 20);
            }
            if (i == this.selected) {
                g.drawRect(gc.getWidth() / 2 - 100, 385 + (50 * i), 200, 50);
            }
        }
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_DOWN) {
            this.somMove.play();
            this.selected++;
            if (this.selected >= this.options.length) {
                this.selected = 0;
            }
        }
        if (key == Input.KEY_UP) {
            this.somMove.play();
            this.selected--;
            if (this.selected < 0) {
                this.selected = this.options.length - 1;
            }
        }

        if (key == Input.KEY_LEFT) {
            this.somMove.play();
            if (this.options[this.selected].equals("Sound Volume")) {
                if (gc.getSoundVolume() > 0) {
                    gc.setSoundVolume(gc.getSoundVolume() - 0.1f);
                }
            }
            if (this.options[this.selected].equals("Music Volume")) {
                if (gc.getSoundVolume() > 0) {
                    gc.setMusicVolume(gc.getMusicVolume() - 0.1f);
                }
            }
        }
        if (key == Input.KEY_RIGHT) {
            this.somMove.play();
            if (this.options[this.selected].equals("Sound Volume")) {
                if (gc.getSoundVolume() < 1) {
                    gc.setSoundVolume(gc.getSoundVolume() + 0.1f);
                }
            }
            if (this.options[this.selected].equals("Music Volume")) {
                if (gc.getSoundVolume() < 1) {
                    gc.setMusicVolume(gc.getMusicVolume() + 0.1f);
                }
            }
        }

        if (key == Input.KEY_ENTER) {
            this.somSelect.play();
            if (this.options[this.selected].equals("Accept")) {
                this.game.enterState(this.idPreviousGameState, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
    }
}
